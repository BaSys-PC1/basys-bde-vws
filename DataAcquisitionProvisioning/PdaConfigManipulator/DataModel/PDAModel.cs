using System;
using System.Linq;
using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;

using Confluent.Kafka;
using Confluent.Kafka.Admin;


namespace PDA_AAS.DataModel
{
    public class PDAModel //this class describes the submodel
    {
        private readonly String config_path;

        protected PdaConfigManipulator.IOConfig _ioconf;// = new PdaConfigManipulator.IOConfig(@"current_IO_config.io");
        protected PdaConfigManipulator.DSConfig _dsconf;// = new PdaConfigManipulator.DSConfig(@"current_DS_config.ds");
        private readonly String _idshort = "dummyid";
        private readonly String _pdaversion = "7.2.1";
        private readonly KafkaAddress _kafka;
        private readonly List<PdaSignal> _sigList;

        //accessible variables
        public string IDshort { get => _idshort; } 
        public string PdaVersion { get => _pdaversion; }
        public int NumberOfSignals { get => _ioconf.signalList.Count;  }
        public KafkaAddress KafkaAddress
        {
            get => _kafka;
        }

        public List<PdaSignal> AvailSignals { get => _sigList.Where(sig => sig.Name != "").ToList() ; }

        public PDAModel(String io_path, String ds_path, String out_path)
        {
            config_path = out_path;
            try
            {
                _ioconf = new PdaConfigManipulator.IOConfig(io_path);
                _dsconf = new PdaConfigManipulator.DSConfig(ds_path);
            }
            catch (Exception)
            {
                throw;
            }

            
            _sigList = _ioconf.signalList;
            foreach ( var elem in _sigList)
            {
                var (ntpc,srt) = _dsconf.signal_in_topic(elem.IDPda);
                if (ntpc != "")
                {
                    elem.add_StorageProfile(ntpc,srt);
                }
            }

            var archiver = _dsconf.KafkaStores[0].ClusterAddress;
            String serv = archiver.Split(':')[0];
            int prt = Convert.ToInt32(archiver.Split(':')[1]);
            _kafka = new KafkaAddress()
            {
                server = serv,
                port = prt
            };


        }

        /// <summary>
        /// Retrieve a specific signal by its ID from the signal list.
        /// </summary>
        /// <param name="idsh">Signal ID e.g. [0:0]</param>
        /// <returns>Signal from the signal list. If Found, otherwise it throw an exception.</returns>
        public PdaSignal get_signal_by_IDshort(String idsh)
        {
            try
            {
                //return _sigList.Where(i => i.IDShort.Contains(idsh)).Single(); // Does the same as before without the List - Throws an Exeption if the signal is not in the list
                PdaSignal signal = _sigList.Where(i => i.IDShort.Equals(idsh)).FirstOrDefault(); // Returns null if the signal is not in the list
                if (signal != null)
                {
                    return signal;
                }
                else
                {
                    //no valid signal with this ID short
                    throw new Exception(string.Format("Signal with ID {0} not found!", idsh));
                }
            }
            catch (Exception)
            {
                // If something else happened during the above code.
                throw;
            }
        }

        /// <summary>
        /// Add a signal to a Kafka topic with a given samplerate.
        /// </summary>
        /// <param name="idsh">Signal ID e.g. [0:0]</param>
        /// <param name="tpc">Topic name</param>
        /// <param name="smplrate">Samplerate in seconds</param>
        public bool AddSignalToKafka(String idsh, String tpc, int smplrate, int agg_mode = 1, int store_index = 0)
        {
            string kafka_server = _kafka.server + ":" + _kafka.port;
            using (var adminClient = new AdminClientBuilder(new AdminClientConfig { BootstrapServers = kafka_server }).Build())
            {
                try
                {
                    adminClient.CreateTopicsAsync(new TopicSpecification[] {
                                new TopicSpecification {
                                    Name = tpc,
                                    ReplicationFactor = 1,
                                    NumPartitions = 1 ,
                                    Configs = new Dictionary<string, string>() {
                                        //["retention.ms"] = "-1" // never delete the topic contents
                                    }
                                }
                            });
                }
                catch (CreateTopicsException e)
                {
                    Console.WriteLine($"An error occured creating topic {e.Results[0].Topic}: {e.Results[0].Error.Reason}");
                    return false;
                }
            }

            try
            {
                PdaSignal sign = get_signal_by_IDshort(idsh);

                var (ntpc, tst) = _dsconf.signal_in_topic(sign.IDPda);
                if (ntpc != tpc)
                {
                    //TODO remove from other topic ?
                    //1 -- check if Kafka profile with sampling rate already exists and add profile if not the case
                    var profile_index = _dsconf.get_profile_index_from_resolution(smplrate, agg_mode);
                    int index;
                    if (profile_index == -1)
                    {
                        index = _dsconf.nb_profiles;
                        _dsconf.add_Profile(smplrate, agg_mode);
                    }
                    else
                    {
                        index = profile_index;
                    }

                    //2 -- check if topic exists
                    int tpc_index;
                    var y = _dsconf.get_topic_index_from_name(0, tpc);
                    if (y == -1)
                    {
                        tpc_index = _dsconf.KafkaStores[0].Topics[0].Topic.Length;
                        _dsconf.add_Topic(0, tpc);
                    }
                    else
                    {
                        tpc_index = y;
                    }

                    //3 -- add signal to topic ; TODO check if store index exists
                    _dsconf.add_signal_to_topic(sign.IDPda, tpc_index, store_index, index);

                    //4 -- update signal in datamodel
                    sign.add_StorageProfile(tpc, smplrate,agg_mode);

                    //5 -- save the configuration
                    // Expands the environment variable %userprofile% to its full path and combines with Desktop and the *.ds file
                    // Result: "C:\Users\<current user>\Desktop\new_DS_config.ds"
                    //string combined_path = Path.Combine(Environment.ExpandEnvironmentVariables("%userprofile%"), @"Desktop\new_DS_config.ds");
                    _dsconf.writeDSConfig(config_path + @"new_DS_config.ds" );
                    return true;
                }
                else if (smplrate != sign.StorageProfiles?[0].sampling_rate) //todo empty list?
                {
                    // this means the signal is already used in another profile on this topic
                    //TODO : Create new topic and add signal there instead
                    Console.WriteLine("Signal already written to this topic but with other profile");
                    return false;
                    //AddSignalToKafka(idsh, tpc+"_#", smplrate, agg_mode, store_index);
                }
                //else if (tst != (sign.StorageProfiles[0].sampling_rate * 10000000).ToString()) { }

                else
                {
                    //this means that the signal is already written to kafka in this configuration
                    Console.WriteLine("signal already written in this config");
                    return false;
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }


    }
}
