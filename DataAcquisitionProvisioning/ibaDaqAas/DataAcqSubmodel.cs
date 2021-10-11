/*******************************************************************************
* Author: T. Seitz
* derivated from the HelloAssetAdministrationShell example from the BaSyx repository
* 
*******************************************************************************/

using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Extensions;
using BaSyx.Utils.ResultHandling;
using System;
using System.Collections.Generic;
using PDA_AAS.DataModel;
using BaSyx.Models.Core.AssetAdministrationShell.Identification.BaSyx;
using BaSyx.Models.Core.AssetAdministrationShell;

namespace IbaDAQAssetAdministrationShell
{
    public class SignalSubmodel
    {

        private Dictionary<string, SubmodelElementCollection> sigIdToDataProvisioning;
        private PDAModel pdaModel;

        public SignalSubmodel(PDAModel pdaModel)
        {
            this.pdaModel = pdaModel;
        }

        private Submodel _submodel = null;
        public Submodel Submodel
        {
            get
            {
                if (_submodel == null)
                {
                    _submodel = CreateSubmodel();
                }
                return _submodel;
            }
        }

        private Submodel CreateSubmodel()
        {
            sigIdToDataProvisioning = new Dictionary<string, SubmodelElementCollection>(pdaModel.AvailSignals.Count);
            Submodel submodel = new Submodel("Signals", new UniformResourceName("com.iba-ag", BaSyxUrnConstants.BASYX_SUBMODELS, "Signals", "1.0.0", null, "ibadaq ", "walzgerüst"))
            {
                Description = new LangStringSet() { new LangString("en-US", "This is a submodel to handle the data acquisition and provisioning with ibaPDA") },
                Kind = ModelingKind.Instance,
                SemanticId = new Reference(new GlobalKey(KeyElements.Submodel, KeyType.IRI, new BaSyxSubmodelIdentifier("Signals", "1.0.0").ToUrn())),
                SubmodelElements =
                {
                    //new Property<string>("PdaVersion")
                    //{
                    //    Get = prop => {  return pdaModel.PdaVersion; },
                    //    Kind = ModelingKind.Instance,
                    //    Description = new LangStringSet() { new LangString("en-US", "This is the version of the installed PDA software") },
                    //    SemanticId = new Reference(new GlobalKey(KeyElements.Property, KeyType.IRI, "urn:basys:org.eclipse.basyx:dataElements:PdaVersion:1.0.0"))
                    //},
                    new SubmodelElementCollection("MessageBroker")
                    {
                        Value = {
                            new Property<string>("Type")
                            {
                                Value = "Kafka"
                            },
                            new Property<string>("ConnectionString")
                            {
                                Value = pdaModel.KafkaAddress.server + ":" + pdaModel.KafkaAddress.port
                            }
                        }
                    },
                    //new Property<int>("NumberOfSignals")
                    //{
                    //    Get = prop => { return pdaModel.AvailSignals.Count; },
                        //Value = signal_data.AvailSignals.Count
                    //}
                }
                //new ElementContainer<ISubmodelElement>()
            };

            var collection = new SubmodelElementCollection("Signals");

            foreach (PdaSignal sig in pdaModel.AvailSignals)
            {
                var signal = new SubmodelElementCollection(sig.IDShort)
                {
                    Description = new LangStringSet() {
                        new LangString("DE", sig.description),
                        new LangString("EN", sig.description2)
                    },
                    SemanticId = new Reference(new GlobalKey(KeyElements.Property, KeyType.IRDI, "0173-1#02-AAJ540#002")),
                    Value = {
                        //new Property<string>("Id") {
                        //    Value = sig.IDPda
                        //},
                        //new Property<string>("Name") {
                        //   Value = sig.Name
                        //},
                        new SubmodelElementCollection("DataAcquisition") {
                            Value = {
                                new Property<string>("Unit") {
                                    Value = sig.Unit
                                },
                                new Property<int>("ModuleNumber") {
                                    Value = sig.ModuleNumber
                                },
                                new Property<int>("NumberInModule") {
                                    Value = sig.NumberInModule
                                },
                                new Property<float>("SamplingRate") {
                                    Value = sig.SamplingRate
                                }
                            }
                        },
                        new SubmodelElementCollection("DataProvisionings")
                    }
                };

                SubmodelElementCollection dataProvisioningContainer = signal.Value.Retrieve("DataProvisionings").Entity as SubmodelElementCollection;
                sigIdToDataProvisioning.Add(sig.IDShort, dataProvisioningContainer);

                int i = 0;
                foreach (StorageProfile profile in sig.StorageProfiles)
                {
                    dataProvisioningContainer.Value.Add(createDataProvisioning(profile, ++i));
                }

                collection.Value.Add(signal);
            }

            submodel.SubmodelElements.Add(collection);

            var op = new Operation("Publish")
            {
                Description = new LangStringSet() { new LangString("en-US", "Creates a new data provisioning entry") },
                InputVariables = new OperationVariableSet() {
                    new Property<string>("sigid") {
                        Description =  new LangStringSet() { new LangString("en-US", "IDShort of signal") }
                    },
                    new Property<string>("topic") {
                        Description =  new LangStringSet() { new LangString("en-US", "Write data to this topic") }
                    },
                    new Property<int>("smpl_rate") {
                        Description =  new LangStringSet() { new LangString("en-US", "sample rate in 100ms ticks") }
                    },
                    new Property<int>("agg_mode") {
                        Description =  new LangStringSet() { new LangString("en-US", "none (0), avg (1), min(2), max(3)") }
                    }
                },
                OutputVariables = new OperationVariableSet() {
                    new Property<bool>("Success") {
                        Description =  new LangStringSet() { new LangString("en-US", "operation successful?") }
                    },
                    new Property<String>("Address") {
                        Description =  new LangStringSet() { new LangString("en-US", "Kafka cluster where the topic is added") }
                    },
                    new Property<String>("id_in_message") {
                        Description =  new LangStringSet() { new LangString("en-US", "internal signal id used in Kafka message") }
                    }
                },
                OnMethodCalled = (op, inArgs, inOutArgs, outArgs, cancellationToken) =>
                {
                    inArgs["Expression"]?.GetValue<string>();
                    string sigid = inArgs["sigid"]?.GetValue<string>();
                    string topic = inArgs["topic"]?.GetValue<string>();
                    int? spl_rate = inArgs["smpl_rate"].GetValue<int>();
                    int? agg_mode = inArgs["agg_mode"].GetValue<int>();

                    try
                    {
                        if (pdaModel.AddSignalToKafka(sigid, topic, spl_rate.Value, agg_mode.Value))
                        {

                            StorageProfile profile = new StorageProfile()
                            {
                                active = true,
                                topic = topic,
                                sampling_rate = spl_rate.Value,
                                aggregation_type = (AggType)agg_mode
                            };

                            SubmodelElementCollection dataProvisioningContainer = sigIdToDataProvisioning[sigid];
                            SubmodelElementCollection dataProvisioning = createDataProvisioning(profile, dataProvisioningContainer.Value.Count + 1);
                            dataProvisioningContainer.Value.Add(dataProvisioning);

                            //var sig = signal_data.get_signal_by_IDshort(sigid);
                            string kafka_server = pdaModel.KafkaAddress.server + ":" + pdaModel.KafkaAddress.port;
                            string inner_id = pdaModel.get_signal_by_IDshort(sigid).IDPda;

                            outArgs.Add(new Property<bool>("Success") { Value = true });
                            outArgs.Add(new Property<string>("Address") { Value = kafka_server});
                            outArgs.Add(new Property<string>("id_in_message") { Value = inner_id});
                            return new OperationResult(true);
                        }
                        else
                        {
                            outArgs.Add(new Property<bool>("Success") { Value = false });
                            return new OperationResult(false);
                        }
                
                    }
                    catch (Exception e)
                    {
                        outArgs.Add(new Property<bool>("Success") { Value = false });
                        return new OperationResult(false);
                    }
                }
            };
            submodel.SubmodelElements.Add(op);

            return submodel;
        }

        private SubmodelElementCollection createDataProvisioning(StorageProfile profile, int number)
        {
            return new SubmodelElementCollection("DataProvisioning" + number)
            {
                Value = {
                    new Property<bool>("Active")
                    {
                        Value = profile.active
                    },
                    new Property<string>("Topic")
                    {
                        Value = profile.topic
                    },
                    new Property<float>("SamplingRate")
                    {
                        Value = profile.sampling_rate
                    },
                    new Property<string>("AggregationMode")
                    {
                        Value = profile.aggregation_type.ToString()
                    }
                }
            };
        }


    }
}
