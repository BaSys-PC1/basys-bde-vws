using System;
using System.Collections.Generic;

using BaSyx.Models.Core.AssetAdministrationShell;
using BaSyx.Models.Core.AssetAdministrationShell.Generics;
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using BaSyx.Models.Core.AssetAdministrationShell.Identification.BaSyx;
using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
//using BaSyx.Models.Core.AssetAdministrationShell.References;
using BaSyx.Models.Core.Common;


namespace IbaDAQAssetAdministrationShell
{
    public static class IbaDAQAssetAdministrationShell
    {        
      
        public static AssetAdministrationShell BuildAssetAdministrationShell()
        {
            AssetAdministrationShell aas = new AssetAdministrationShell("ibaDAQ-AAS", new UniformResourceName("com.iba-ag", BaSyxUrnConstants.BASYX_SHELLS, null, "1.0.0", null, "ibadaq ", "walzgerüst")) // Leerzeichen nötig wegen Bug in UniformResource.cs
            {
                Description = new LangStringSet() { new LangString("en-US", "This is the asset administration shell for managing an ibaDAQ device") },
                Administration = new AdministrativeInformation()
                {
                    Version = "0.2",
                    Revision = "010"
                },
                Asset = new Asset("ibaDAQ", new UniformResourceName("com.iba-ag", BaSyxUrnConstants.BASYX_ASSETS, null, "1.0.0", null, "ibadaq ", "walzgerüst"))
                {
                    Description = new LangStringSet() {  new LangString("en-US", "This is an ibaDAQ device") },
                    Kind = AssetKind.Instance
                },
                // the container containing all submodels
                Submodels = new ElementContainer<ISubmodel>()
            };

            Submodel assetIdentificationSubmodel = new Submodel("AssetIdentification", new UniformResourceName("com.iba-ag", BaSyxUrnConstants.BASYX_SUBMODELS, "AssetIdentification", "1.0.0", null, "ibadaq ", "walzgerüst"))
            {
                Description = new LangStringSet() { new LangString("en-US", "This submodel identifies the asset.") },
                Kind = ModelingKind.Instance,
                SemanticId = new Reference(new GlobalKey(KeyElements.Submodel, KeyType.IRI, new BaSyxSubmodelIdentifier("AssetIdentification", "1.0.0").ToUrn())),
                SubmodelElements =
                {
                    new Property<string>("ProductType")
                    {
                        Kind = ModelingKind.Instance,
                        SemanticId = new Reference(new GlobalKey(KeyElements.Property, KeyType.IRDI, "0173-1#02-AAO057#002")),
                        Value = "ibaDAQ",
                    },
                    new Property<string>("SerialNumber")
                    {
                        Kind = ModelingKind.Instance,
                        Value = "DAQ_SerialNumber"
                    }
                }
            };

            (aas.Asset as Asset).AssetIdentificationModel = new Reference<ISubmodel>(assetIdentificationSubmodel);

            aas.Submodels.Add(assetIdentificationSubmodel);

            return aas;
        }

    }
}
