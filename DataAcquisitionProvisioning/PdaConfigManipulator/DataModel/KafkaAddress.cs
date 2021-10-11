namespace PDA_AAS.DataModel
{
    public class KafkaAddress
    {
        public string server { get; set; }
        public int port { get; set; }
        public string registry { get; set; }
        public int registry_port { get; set; }
    }
}
