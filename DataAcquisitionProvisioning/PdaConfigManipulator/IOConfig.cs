using System;
using System.Collections.Generic;
using System.IO;
using System.Xml;
using System.Xml.Serialization;
using PDA_AAS.IOConfig;
using PDA_AAS.DataModel;
using NLog;

namespace PDA_AAS.PdaConfigManipulator
{
    public class IOConfig
    {
        private static readonly ILogger logger = LogManager.GetCurrentClassLogger();

        public static string IDSHORT_PREFIX = "Signal_";

        protected IOConfiguration _mainIOConfig;
        public IOConfiguration mainIOConfig
        {
            get => _mainIOConfig;
        }

        protected List<PdaSignal> _sigList = new List<PdaSignal>();

        public List<PdaSignal> signalList
        {
            get => _sigList;
        }

        public IOConfig(string filename)
        {
            _mainIOConfig = DeSerializeIO(filename);
            get_signals();
        }
   
        private Int32 nbrSignal { get; set; }

        
        private void add_signal_to_list(PDA_AAS.IOConfig.Signal signallink, Int32 modnbr, Int32 signbr, Double tbase)
        {
            var SignalType = signallink.DataType;
            var Active = false;
            if (signallink.Active == "1") { Active = true; }

            char signaltypesign;
            switch (SignalType)
            {
                    case "0":       // digital signals
                        signaltypesign = '.';
                        break;
                    case "2":       // analog signals
                        signaltypesign = ':';
                        break;
                    case "25":      // text channels
                        signaltypesign = ':';
                        break;
                    case "1048":    // techno strings
                        signaltypesign = ':';
                        break;
                    default:
                        signaltypesign = '?';
                        break;
            }
            char sep = 'a';
            if (signaltypesign == '.') { sep = 'd'; }
            var PdaId = "[" + modnbr.ToString() + signaltypesign + signbr.ToString() + "]";
            var SignalShortId = IDSHORT_PREFIX + modnbr.ToString() + sep + signbr.ToString();

            if ((
                (SignalType == "0") ||  // analog signals
                (SignalType == "2") ||    // digital signals
                (SignalType == "25") ||   // text channels
                (SignalType.Contains("1048")) // techno strings
                )
                && (Active == true))
                _sigList.Add(new PdaSignal(SignalShortId, PdaId, signallink.Name, signallink.Unit, signallink.Comment1, signallink.Comment2, modnbr, signbr, (float)tbase));

        }


        private void get_signals()
        {
            //ibaSignalInformationHandler signalAddOn = ibaSignalInformationHandler.Instance;

            foreach (IOConfigurationModulesModule cfg_modules in _mainIOConfig.Modules)
            {
                foreach (Links cfg_links in cfg_modules.Links)
                {
                    foreach (LinksLink cfg_linkslink in cfg_links.Link)
                    {
                        if (cfg_linkslink.Analog != null)
                        {
                            Int32 mod_signalnbr = 0;
                            foreach (Signal signal in cfg_linkslink.Analog)
                            {
                                //if (signal.Name != "")
                                {
                                    add_signal_to_list(signal, Convert.ToInt32(cfg_modules.ModuleNr), mod_signalnbr, Convert.ToDouble(cfg_modules.Timebase));
                                    mod_signalnbr++;
                                }
                                
                            }
                        }
                        if (cfg_linkslink.Digital != null)
                        {
                            Int32 mod_signalnbr = 0;
                            foreach (Signal signal in cfg_linkslink.Digital)
                            {
                                //if (signal.Name != "")
                                {
                                    add_signal_to_list(signal, Convert.ToInt32(cfg_modules.ModuleNr), mod_signalnbr, Convert.ToDouble(cfg_modules.Timebase));
                                    mod_signalnbr++;
                                }
                                
                            }
                        }
                    }
                }
            }

            //nbrSignal = signalAddOn.GetNumberSignals();
            nbrSignal = _sigList.Count;
            logger.Info($"ibaIOManagerFileReader: {nbrSignal} signals added.");

        }

        protected static IOConfiguration DeSerializeIO(string filename)
        {
            IOConfiguration ioConfig = new IOConfiguration();
            if (File.Exists(filename))
            {
                try
                {
                    //using (FileStream originalFileStream = new FileStream(filename, FileMode.Open))
                    using (TextReader tr = new StreamReader(filename))
                    {
                        XmlSerializer serializer = new XmlSerializer(typeof(IOConfiguration));
                        XmlReader reader = XmlReader.Create(tr);
                        ioConfig = (IOConfiguration)serializer.Deserialize(reader);
                        reader.Close();
                    }
                }
                catch (Exception e)
                {
                    // Something went wrong
                    Console.WriteLine($"{e}");
                }
                
            }
            return ioConfig;
        }
    }
}


