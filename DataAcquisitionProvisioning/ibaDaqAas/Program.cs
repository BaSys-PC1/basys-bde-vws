using BaSyx.AAS.Server.Http;
using BaSyx.API.AssetAdministrationShell.Extensions;
using BaSyx.API.Components;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
//using BaSyx.Submodel.Server.Http;
using BaSyx.Utils.Settings.Types;
using System;
using NLog;
using BaSyx.Submodel.Server.Http;
using BaSyx.Registry.Client.Http;
using System.Threading.Tasks;
using System.Configuration;
using PDA_AAS.DataModel;
using BaSyx.Common.UI;
using BaSyx.Common.UI.Swagger;
using BaSyx.Models.Connectivity.Descriptors;
using BaSyx.Models.Connectivity;
using System.Collections.Generic;

namespace IbaDAQAssetAdministrationShell
{
    class Program
    {
        //Enable logging
        private static readonly ILogger logger = LogManager.GetCurrentClassLogger();

        static void Main(string[] args)
        {            
            string registryUrl = ConfigurationManager.AppSettings.Get("registry_server");
            string hostUrl = ConfigurationManager.AppSettings.Get("host_address");
            string out_path = ConfigurationManager.AppSettings.Get("Config_out_path");
            string read_io_path = ConfigurationManager.AppSettings.Get("IO_config_location");
            string read_ds_path = ConfigurationManager.AppSettings.Get("DS_config_location");

            var registrySettings = new RegistryClientSettings();
            registrySettings.RegistryConfig.RegistryUrl = registryUrl;
            RegistryHttpClient registryClient = new RegistryHttpClient(registrySettings);

            PDAModel pdaModel = new PDAModel(read_io_path, read_ds_path, out_path);

            IAssetAdministrationShell aas = IbaDAQAssetAdministrationShell.BuildAssetAdministrationShell();            
            aas.Submodels.Add(new SignalSubmodel(pdaModel).Submodel);
            // temporary disabled
            //aas.Submodels.Add(new CurrentDataSubmodel(pdaModel).Submodel);

            ServerSettings aasServerSettings = ServerSettings.CreateSettings();
            aasServerSettings.ServerConfig.Hosting.ContentPath = "Content";
            aasServerSettings.ServerConfig.Hosting.Environment = "Development";
            aasServerSettings.ServerConfig.Hosting.Urls.Add(hostUrl);

            IAssetAdministrationShellServiceProvider serviceProvider = aas.CreateServiceProvider(true);
            serviceProvider.UseAutoEndpointRegistration(aasServerSettings.ServerConfig);

            AssetAdministrationShellHttpServer aasServer = new AssetAdministrationShellHttpServer(aasServerSettings);
            aasServer.SetServiceProvider(serviceProvider);
            aasServer.ApplicationStarted = () =>
            {
                var aasDescriptor = new AssetAdministrationShellDescriptor(aas, serviceProvider.ServiceDescriptor.Endpoints);
                foreach (var sm in aas.Submodels)
                {
                    var endpoints = new List<IEndpoint>();
                    foreach (var e in aasDescriptor.Endpoints)
                    {
                        endpoints.Add(new HttpEndpoint(e.Address + "/submodels/"+ sm.IdShort + "/submodel"));
                    }
                    var smDescriptor = new SubmodelDescriptor(sm, endpoints);
                    aasDescriptor.SubmodelDescriptors.Add(smDescriptor);
                }
                var result = registryClient.CreateOrUpdateAssetAdministrationShellRegistration(aas.Identification.Id, aasDescriptor);
                if (result.Success)
                {
                    logger.Info("AAS registered");
                }
                else
                {
                    logger.Warn("AAS NOT registered");
                } 
            };
            aasServer.ApplicationStopping = () => {
                var result = registryClient.DeleteAssetAdministrationShellRegistration(aas.Identification.Id);
                if (result.Success)
                {
                    logger.Info("AAS unregistered");
                }
                else
                {
                    logger.Warn("AAS NOT unregistered");
                }
            };
            aasServer.AddBaSyxUI(PageNames.AssetAdministrationShellServer);
            aasServer.AddSwagger(Interface.AssetAdministrationShell);
            aasServer.Run();
        }
    }
}
