package de.eyeled.fue.basyx.lib.aas.iba.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class SensorData {
	
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String MIN = "min";
	public static final String MAX = "max";
	public static final String UNIT = "unit";
	public static final String DATATYPE = "datatype";
	public static final String RESSOURCE = "ressource";


    @SerializedName(ID)
	private String mId;
    
    @SerializedName(NAME)
	private String mName;
    
    @SerializedName(TYPE)
	private String mTyp;
    
    @SerializedName(MIN)
	private String mMin;
    
    @SerializedName(MAX)
	private String mMax;
    
    @SerializedName(UNIT)
	private String mUnit;
    
    @SerializedName(DATATYPE)
	private String mDataType;
    
    @SerializedName(RESSOURCE)
	private String mRessourceId;
	
	public SensorData(String mName) {
		super();
		this.mName = mName;
	}
	public String getId() {
		return mId;
	}
	
	public void setId(String mId) {
		this.mId = mId;
	}
	
	public String getRessourceId() {
		return mRessourceId;
	}
	
	public void setRessourceId(String mRessourceId) {
		this.mRessourceId = mRessourceId;
	}
	
	public String getName() {
		return mName;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	public String getTyp() {
		return mTyp;
	}
	public void setTyp(String mTyp) {
		this.mTyp = mTyp;
	}
	public String getMin() {
		return mMin;
	}
	public void setMin(String mMin) {
		this.mMin = mMin;
	}
	public String getMax() {
		return mMax;
	}
	public void setMax(String mMax) {
		this.mMax = mMax;
	}
	public String getUnit() {
		return mUnit;
	}
	public void setUnit(String mUnit) {
		this.mUnit = mUnit;
	}
	public String getDataType() {
		return mDataType;
	}
	public void setDataType(String mDataType) {
		this.mDataType = mDataType;
	}
	
	public static SensorData create(String json) {
		SensorData sensorData = null;
		if(json != null){
			try {
				Gson gson = new GsonBuilder().create();
				sensorData = gson.fromJson(json, SensorData.class);
			}
			catch(Exception e) {
				e.printStackTrace();
				System.err.println("error creating sensor data: "+e.getMessage());
			}
		}
		return sensorData;
	}
	
	
	
	
}
