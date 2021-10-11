/*******************************************************************************
* Author: T. Seitz
* derivated from the HelloAssetAdministrationShell example from the BaSyx repository
* 
*******************************************************************************/

using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using System;
using System.Collections.Generic;
using PDA_AAS.DataModel;
using Confluent.Kafka;
using System.Threading;
using BaSyx.Models.Core.AssetAdministrationShell;
using BaSyx.Models.Core.AssetAdministrationShell.Identification.BaSyx;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using System.Threading.Tasks;

namespace IbaDAQAssetAdministrationShell
{
    public class CurrentDataSubmodel
    {

        Dictionary<string, KafkaDataProvider> _dataProviders = new Dictionary<string, KafkaDataProvider>();
        PDAModel _pdaModel;

        public CurrentDataSubmodel(PDAModel pdaModel)
        {
            this._pdaModel = pdaModel;
        }

        private Submodel _submodel = null;
        public Submodel Submodel {
            get {
                if (_submodel == null) {
                    _submodel = CreateSubmodel();
                }
                return _submodel;
            }
        }

        public Submodel CreateSubmodel()
        {
            string server = _pdaModel.KafkaAddress.server + ":" + _pdaModel.KafkaAddress.port;

            Submodel submodel = new Submodel("SignalData", new UniformResourceName("com.iba-ag", BaSyxUrnConstants.BASYX_SUBMODELS, "SignalData", "1.0.0", null, "ibadaq ", "walzgerüst"))
            {
                Description = new LangStringSet() { new LangString("en-US", "This is a submodel to retrieve provisioned signal data") },
                Kind = ModelingKind.Instance,
                SemanticId = new Reference(new GlobalKey(KeyElements.Submodel, KeyType.IRI, new BaSyxSubmodelIdentifier("SignalData", "1.0.0").ToUrn()))
            };

            foreach (PdaSignal sig in _pdaModel.AvailSignals)
            {
                foreach (StorageProfile profile in sig.StorageProfiles)
                {
                    KafkaDataProvider provider = new KafkaDataProvider(server, profile.topic, CancellationToken.None);
                    _dataProviders.Add(sig.IDShort, provider);

                    Property<string> signalData = new Property<string>(sig.IDShort)
                    {
                        Description = new LangStringSet()
                        {
                            new LangString("DE", sig.description),
                            new LangString("EN", sig.description2)
                        },
                        MetaData = new Dictionary<string, string>() {
                            { "Name", sig.Name },
                            { "Id", sig.IDPda },
                            { "Topic", profile.topic},
                        },
                        SemanticId = new Reference(new GlobalKey(KeyElements.Property, KeyType.IRDI, "0173-1#02-AAM391#002")),
                        Get = prop => { return provider.Data; }
                    };
                    submodel.SubmodelElements.Add(signalData);
                }    
            }
           
            return submodel;
        }

        public static string getLastData(string server, ISubmodelElement property) {
            Property p = property as Property;

            CancellationTokenSource cts = new CancellationTokenSource();
            Console.CancelKeyPress += (_, e) => {
                e.Cancel = true; // prevent the process from terminating.
                cts.Cancel();
            };

            return RetrieveFromKafka(server, p.MetaData["Topic"], cts.Token);                       
        }

        public static string RetrieveFromKafka(string brokerList, string topic, CancellationToken cancellationToken)
        {
            var config = new ConsumerConfig
            {
                // the group.id property must be specified when creating a consumer, even 
                // if you do not intend to use any consumer group functionality.
                GroupId = Guid.NewGuid().ToString(),
                BootstrapServers = brokerList,
                // partition offsets can be committed to a group even by consumers not
                // subscribed to the group. in this example, auto commit is disabled
                // to prevent this from occurring.
                EnableAutoCommit = true
            };

            using (var consumer =
                new ConsumerBuilder<Ignore, string>(config)
                    .SetErrorHandler((_, e) => Console.WriteLine($"Error: {e.Reason}"))
                    .Build())
            {
                TopicPartition tp = new TopicPartition(topic, new Partition(0));
                consumer.Assign(new TopicPartitionOffset(tp, Offset.Beginning));
                //Offset o = consumer.Position(tp);
                //consumer.Assign(new TopicPartitionOffset(tp, new Offset(o.Value - 1));

                try
                {
                    var consumeResult = consumer.Consume(2000);
                    // Note: End of partition notification has not been enabled, so
                    // it is guaranteed that the ConsumeResult instance corresponds
                    // to a Message, and not a PartitionEOF event.
                    if (consumeResult != null)
                    {
                        Console.WriteLine($"Received message at {consumeResult.TopicPartitionOffset}: ${consumeResult.Message.Value}");
                        return consumeResult.Message.Value;
                    }
                    else 
                    {
                        return string.Empty;
                    }
                }
                catch (ConsumeException e)
                {
                    Console.WriteLine($"Consume error: {e.Error.Reason}");
                    return string.Empty;
                }
                finally {
                    Console.WriteLine("Closing consumer.");
                    consumer.Unassign();                    
                    consumer.Close();
                }                
            }                        
        }
    }

    public class KafkaDataProvider : IDisposable 
    {

        private readonly string _server;
        private readonly string _topic;
        private readonly IConsumer<string, string> _kafkaConsumer;

        public KafkaDataProvider(string server, string topic, CancellationToken token)
        {
            this._server = server;
            this._topic = topic;
            var config = new ConsumerConfig
            {
                // the group.id property must be specified when creating a consumer, even 
                // if you do not intend to use any consumer group functionality.
                GroupId = "current-data-submodel", //Guid.NewGuid().ToString(),
                BootstrapServers = _server,
                // partition offsets can be committed to a group even by consumers not
                // subscribed to the group. in this example, auto commit is disabled
                // to prevent this from occurring.
                EnableAutoCommit = true
            };
            this._kafkaConsumer = new ConsumerBuilder<string, string>(config)
                    .SetErrorHandler((_, e) => Console.WriteLine($"Error: {e.Reason}"))
                    .Build();
            new Thread(() => StartConsumerLoop(token)).Start();
        }
       
        private string _data = "-";
        public string Data
        {
            get { return _data; }
            private set { _data = value; }
        }

        private void StartConsumerLoop(CancellationToken cancellationToken)
        {
            //_kafkaConsumer.Subscribe(this._topic);

            TopicPartition tp = new TopicPartition(this._topic, new Partition(0));
            _kafkaConsumer.Assign(new TopicPartitionOffset(tp, Offset.Beginning));

            while (!cancellationToken.IsCancellationRequested)
            {
                try
                {
                    var cr = _kafkaConsumer.Consume(cancellationToken);

                    // Handle message...
                    Console.WriteLine($"{cr.Message.Key}: {cr.Message.Value}");
                    Data = cr.Message.Value;
                }
                catch (OperationCanceledException)
                {
                    break;
                }
                catch (ConsumeException e)
                {
                    // Consumer errors should generally be ignored (or logged) unless fatal.
                    Console.WriteLine($"Consume error on topic {this._topic}: {e.Error.Reason}");

                    if (e.Error.IsFatal)
                    {
                        // https://github.com/edenhill/librdkafka/blob/master/INTRODUCTION.md#fatal-consumer-errors
                        break;
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine($"Unexpected error: {e}");
                    break;
                }
            }
        }

        public void Dispose()
        {
            this._kafkaConsumer.Close(); // Commit offsets and leave the group cleanly.
            this._kafkaConsumer.Dispose();
        }

    }
}
