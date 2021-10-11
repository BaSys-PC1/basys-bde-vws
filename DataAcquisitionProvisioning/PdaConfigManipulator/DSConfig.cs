using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Xml;
using System.Xml.Serialization;
using PDA_AAS.DSConfig;

namespace PDA_AAS.PdaConfigManipulator
{
    public class DSConfig
    {
        //-- constructor and public variables

        public DSConfig(string filename)
        {            
            _mainConfig = DeSerializeDSCFG(filename);
        }

        //-- Configuration Elements: complete, profiles, kafkastores
        protected ArchiverConfiguration _mainConfig;
        protected ArchiverConfigurationProfilesProfile[] _profiles
        {
            get =>_mainConfig.Profiles[0].Profile;
            set
            {
                _mainConfig.Profiles[0].Profile = value;
                nb_profiles = value.Length;
            }
        }

        public int nb_profiles
        {
            get
            {
                int medval;
                Int32.TryParse(_mainConfig.Profiles[0].Count, out medval);
                return medval;
            }
            set
            {
                _mainConfig.Profiles[0].Count = value.ToString();
            }
        }

        public ArchiverConfigurationKafkaArchiverLevelsKafkaArchiverLevel[] KafkaStores
        {
            get => _mainConfig.KafkaArchiverLevels[0].KafkaArchiverLevel;          
            set
            {
                _mainConfig.KafkaArchiverLevels[0].KafkaArchiverLevel = value;
                nb_stores = value.Length;
            }
        }

        protected int nb_stores
        {
            get
            {
                Int32.TryParse(_mainConfig.KafkaArchiverLevels[0].Count, out int medval);
                return medval; 
            }
            set
            {
                _mainConfig.KafkaArchiverLevels[0].Count = value.ToString();
            }
        }

        public ArchiverConfiguration mainConfig
        {
            get => _mainConfig;
        }

        #region profile_modifications

        /// <summary>
        /// Retrieves all available profile names from the configuration
        /// </summary>
        /// <returns>Stringarray that contains all available profile names.</returns>
        public String[] get_avail_profiles()
        {
            var name_list = new List<String>();
            foreach (var elem in _profiles.ToList())
            {
                name_list.Add(elem.Name);
            }
            return name_list.ToArray();
        }

        /// <summary>
        /// Checks if a profile with a given name exists in the configuration
        /// </summary>
        /// <param name="name">Name of the profile to check</param>
        /// <returns>True if the profile exists, otherwise false.</returns>
        public bool has_profile_with_name(string name)
        {
            List<String> name_list = get_avail_profiles().ToList<String>();
            return name_list.Contains(name);
        }

        /// <summary>
        /// Tries to retrieve the profile index by the given resolution.
        /// </summary>
        /// <param name="res">Resolution of the profile in seconds</param>
        /// <returns>Index of the first profile with a matching resolution. Otherwise -1</returns>
        /// <param name="agg_mode">aggregation mode none(0), avg(1), min(2), max(3)</param>
        public int get_profile_index_from_resolution(int res, int agg_mode = 1)
        {
            var tbasestring = ((int)(res * 1000000)).ToString();
            int idx = _profiles.ToList().FindIndex(i => i.Timebase.Equals(tbasestring) && i.Name.Contains(aggr_dict[agg_mode]));

            return idx;
        }

        protected Dictionary<int, string> aggr_dict = new Dictionary<int, string>()
            {
                {0, "none" },
                {1, "avg" },
                {2, "min" },
                {3, "max" },
            };

        /// <summary>
        /// Add a new profile with a given timebase
        /// </summary>
        /// <param name="profilename">Profile name</param>
        /// <param name="timebase">Timebase in 100 ms ticks</param>
        /// <param name="agg_mode">aggregation mode none(0), avg(1), min(2), max(3)</param>
        public void add_Profile(int timebase, int agg_mode = 1)
        {
            string profile_name = (timebase / 10).ToString() + "sec_" + aggr_dict[agg_mode];
            var profile_list = new List<ArchiverConfigurationProfilesProfile>();
            profile_list.AddRange(_profiles.ToList());
            string time_ns = (timebase * 1000000).ToString();
            var newprof = new ArchiverConfigurationProfilesProfile()
            {
                Name = profile_name,
                ProfileType = "0", //0=timebased
                FilteringMode = agg_mode.ToString(), //0=none, 1=Avg, 2=Min, 3=Max      
                Precision = "1",

                TimeBased = "1",
                TimeMode = "1", //0=original timebase, 1=absolute timebase, 2=relative timebase
                Timebase = time_ns, // in 100 ns
                AbsoluteTimebase = "1",
                RelativeTimeFactor = "10",

                LengthBased = "0",
                LengthFilterMode = "0",
                Lengthbase = "55",

                Compression = "0", //not supported with Kafka
                ReadOnly = "0",
                Version = "7"
            };

            profile_list.Add(newprof);

            _profiles = profile_list.ToArray();
        }

        public void clean_unused_profiles()
        {
            // TODO: Implement if needed
        }
        #endregion

        #region store_modifications
        public void add_Store (string storename)
        {
            var store_list = new List<ArchiverConfigurationKafkaArchiverLevelsKafkaArchiverLevel>();
            store_list.AddRange(KafkaStores.ToList());
            //this needs to be extended to create valid stores
            var newstore = new ArchiverConfigurationKafkaArchiverLevelsKafkaArchiverLevel()
            {
                AckMode = "0",
                Name = storename
            };

            store_list.Add(newstore);
            KafkaStores = store_list.ToArray();           
        }
        #endregion

        #region topic_modifications
        /// <summary>
        /// Retrieves the topic name as well as the used timebase based on the given signal ID.
        /// </summary>
        /// <param name="sig_id">Signal ID e.g. [0:0]</param>
        /// <returns>Topic name as string and used timebase as float.</returns>
        public (String, float) signal_in_topic(string sig_id)
        {
            float used_profile = -1f; // Default value
            foreach (var kst in KafkaStores)
            {
                foreach (var tpc in kst.Topics[0].Topic)
                {
                    if (tpc.Signals[0].Signal != null)
                    {
                        // Get the signal if it matches the ID, otherwise return null
                        Signal signal = tpc.Signals[0].Signal.Where(i => i.Id.Equals(sig_id)).SingleOrDefault();

                        
                        if (signal != null)
                        {
                            // Get the profile where the signal is included or return null
                            var profile = _mainConfig.Profiles[0].Profile.Where(i => i.Name.Equals(signal.Profile)).SingleOrDefault();
                            if (profile != null)
                            {
                                float.TryParse(profile.Timebase, out used_profile);
                            }

                            return (tpc.Name, used_profile);
                        }
                    }
                }
            }
            return (string.Empty, used_profile);
        }

        /// <summary>
        /// Retrieves all available topics from a given Kafka store index.
        /// </summary>
        /// <param name="store_index">Kafka store index. zero-based</param>
        /// <returns>All available topics as stringarray.</returns>
        public String[] get_avail_topics(int store_index)
        {
            var topic_list = new List<ArchiverConfigurationKafkaArchiverLevelsKafkaArchiverLevelTopicsTopic>();
            topic_list.AddRange(_mainConfig.KafkaArchiverLevels[0].KafkaArchiverLevel[store_index].Topics[0].Topic);
            var name_list = new List<String>();
            foreach (ArchiverConfigurationKafkaArchiverLevelsKafkaArchiverLevelTopicsTopic elem in topic_list)
            {
                name_list.Add(elem.Name);
            }
            return name_list.ToArray();
        }

        /// <summary>
        /// Checks if a given topic name exists in a Kafka store.
        /// </summary>
        /// <param name="store_index">Kafka store index</param>
        /// <param name="name">Topic name</param>
        /// <returns>True if the topic exists otherwise false.</returns>
        public bool has_topic(int store_index, string name)
        {
            List<String> name_list = get_avail_topics(store_index).ToList<String>();
            return name_list.Contains(name);
        }

        /// <summary>
        /// Retrieves the index of the given topic name from the topic list.
        /// </summary>
        /// <param name="store_index">Kafka store index</param>
        /// <param name="name">Topic name</param>
        /// <returns>Index of the given topic name in the topic list. If found, otherwise -1.</returns>
        public int get_topic_index_from_name(int store_index, string name)
        {
            List<String> name_list = get_avail_topics(store_index).ToList<String>();
            var ind = name_list.FindIndex(t => t.Equals(name));
            return ind;
        }

        /// <summary>
        /// Add a new topic to a given Kafka store
        /// </summary>
        /// <param name="store_index">Kafka store index</param>
        /// <param name="topicname">New topic name</param>
        public void add_Topic(int store_index, string topicname)
        {
            if (has_topic(store_index, topicname))
            {
                throw new System.Exception("Topic already exists.");
            }

            var topic_list = new List<ArchiverConfigurationKafkaArchiverLevelsKafkaArchiverLevelTopicsTopic>();
            topic_list.AddRange(_mainConfig.KafkaArchiverLevels[0].KafkaArchiverLevel[store_index].Topics[0].Topic);

            var signal = new List<Signals>();
            //signal.Add(new Signals());

            var newtopic = new ArchiverConfigurationKafkaArchiverLevelsKafkaArchiverLevelTopicsTopic()
            {
                Active = "1",
                Encoding = "2", //1 = json(grouped), 2 = json(per signal)
                IDFormat = "$signalid ",
                KeyFormat = "$identifier",
                MetaData = "8", // = 0001000 BITS ->  unit comment1 comment2 timestamp signalname singalid  identifier
                Name = topicname,
                Signals = signal.ToArray()
            };

            //extend list and add to config
            topic_list.Add(newtopic);
            _mainConfig.KafkaArchiverLevels[0].KafkaArchiverLevel[store_index].Topics[0].Topic = topic_list.ToArray();
            //update topic counter
            Int32.TryParse(_mainConfig.KafkaArchiverLevels[0].KafkaArchiverLevel[store_index].Topics[0].Count, out int medval);
            _mainConfig.KafkaArchiverLevels[0].KafkaArchiverLevel[store_index].Topics[0].Count = (medval + 1).ToString();
        }

        /// <summary>
        /// Add a signal to a topic of a given Kafka store with a specific profile.
        /// </summary>
        /// <param name="sig_id">Signal ID e.g. [0:0]</param>
        /// <param name="tpc_indx">Zero based topic index</param>
        /// <param name="store_index">Zero based Kafka strore index</param>
        /// <param name="profile_index">Zero based profile index</param>
        public void add_signal_to_topic(string sig_id, int tpc_indx, int store_index, int profile_index)
        {
            var prof_name = _profiles[profile_index].Name;
            var signal_list = new List<PDA_AAS.DSConfig.Signal>();
            var kafkastorescopy = KafkaStores;
            if (kafkastorescopy[store_index].Topics[0].Topic[tpc_indx].Signals.Length >= 1)
            {
                signal_list.AddRange(kafkastorescopy[store_index].Topics[0].Topic[tpc_indx].Signals[0].Signal);
                signal_list.Add(new PDA_AAS.DSConfig.Signal()
                {
                    Id = sig_id,
                    Profile = prof_name
                });
            }
            else
            {
                signal_list.Add(new PDA_AAS.DSConfig.Signal()
                {
                    Id = sig_id,
                    Profile = prof_name
                });
                var tt = new List<Signals>
                {
                    new Signals()
                };
                kafkastorescopy[store_index].Topics[0].Topic[tpc_indx].Signals = tt.ToArray();
                KafkaStores = kafkastorescopy;
            }


            _mainConfig.KafkaArchiverLevels[0].KafkaArchiverLevel[store_index].Topics[0].Topic[tpc_indx].Signals[0].Signal = signal_list.ToArray();
            //update signal counter
            Int32.TryParse(_mainConfig.KafkaArchiverLevels[0].KafkaArchiverLevel[store_index].Topics[0].Topic[tpc_indx].Signals[0].Count, out int medval);
            _mainConfig.KafkaArchiverLevels[0].KafkaArchiverLevel[store_index].Topics[0].Topic[tpc_indx].Signals[0].Count = (medval + 1).ToString();
        }
        #endregion

        protected static ArchiverConfiguration DeSerializeDSCFG(string filename)
        {
            /*
             https://stackoverflow.com/questions/4203540/generate-c-sharp-class-from-xml
             1. Open Developer Prompt to have the XSD utility
             2. Goto the folder where you extracted a PDA-Project
             3. Copy the *.io file to *.xml
             4. Copy the *.ds file to *.xml
             5. Execute: xsd CurrentDataStoreConfig.xml
             6. Execute: xsd CurrentIoConfig.xml
             7. Execute: xsd CurrentDataStoreConfig.xsd /classes
             8. Execute: xsd CurrentIoConfig.xsd /classes
             9. Copy the written *.cs files to your C#-Project
            10. Add the *.cs files to you C#-Project
            11. Try to deserialize the IO/DS config and fix the new *.cs files by removing the second [] of each element that throws an exception
            */

            
            ArchiverConfiguration dsConfig = new ArchiverConfiguration();

            if (File.Exists(filename))
            {
                try
                {
                    //using (FileStream originalFileStream = new FileStream(filename, FileMode.Open))
                    using (TextReader tr = new StreamReader(filename))
                    {
                        XmlSerializer serializer = new XmlSerializer(typeof(ArchiverConfiguration));
                        XmlReader reader = XmlReader.Create(tr);
                        dsConfig = (ArchiverConfiguration)serializer.Deserialize(reader);
                        reader.Close();
                        return dsConfig;
                    }
                }
                catch (Exception)
                {
                    // Something went wrong
                    throw;
                }

                //SerializeObject(dsConfig, @"D:\01_Development\C#2019\PDA-Project\PDA-Project\project\SerializedDataStoreConfig.ds");
            }
            return dsConfig; 
        }

        protected static void SerializeObject(object objToSerialize, string filename)
        {
            XmlSerializer x = new XmlSerializer(objToSerialize.GetType());
            XmlWriterSettings settings = new XmlWriterSettings
            {
                Indent = true,
                CloseOutput = true
            };

            using (FileStream fs = File.Create(filename))
            {
                XmlWriter xmlWriter = XmlWriter.Create(fs, settings);
                x.Serialize(xmlWriter, objToSerialize);
                xmlWriter.Close();
            }
        }
        
        public void writeDSConfig(string filename)
        {
            SerializeObject(_mainConfig, filename);
        }

    }
}


