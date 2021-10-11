namespace PDA_AAS.DataModel
{
    public class StorageProfile
    {
        public bool active; //Kafka writing activated?
        public float sampling_rate { get; set; } //aggregation factor * PdaSignal.sampling_rate
        public int aggregation_factor { get; set; }
        public AggType aggregation_type;
        public string topic { get; set; }
    }
}
