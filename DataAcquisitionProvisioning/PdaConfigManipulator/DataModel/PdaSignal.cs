using System;
using System.Collections.Generic;

namespace PDA_AAS.DataModel
{
    public class PdaSignal
    {
        public PdaSignal(String idshort,String sigid, String nm, String unt, String desc = "",String desc2 = "", int Mdnbr = 0, int sgnmbr = 0, float smplr = 0.1f)
        {
            IDShort = idshort;
            IDPda = sigid;
            Name = nm;
            Unit = unt;
            description = desc;
            description2 = desc2;
            ModuleNumber = Mdnbr;
            NumberInModule = sgnmbr;
            SamplingRate = smplr;
            //sig_type = sig_type.analog;         
            StorageProfiles = new List<StorageProfile>();
        }
        public string IDShort { get; } //can e.g. be composed from ModuleNumer, NumberInModule, and Name
        public string IDPda { get; }
        public string Name { get; }
        public string Unit { get; }
        public string description { get;} //derived from comments
        public string description2 { get; }
        public int ModuleNumber { get; }
        public int NumberInModule { get;}
        public float SamplingRate { get; }
        public enum sig_type { analog, boolean, text }

        public List<StorageProfile> StorageProfiles { get; protected set; }

        public void add_StorageProfile(string tpc, float smplrate, int agg_type =1)
        {
            StorageProfiles.Add(new StorageProfile()
            {
                active = true,
                topic = tpc,
                sampling_rate = smplrate,
                aggregation_factor = (int)(smplrate / this.SamplingRate),
                aggregation_type = (AggType) agg_type
            });
        }
    }
}
