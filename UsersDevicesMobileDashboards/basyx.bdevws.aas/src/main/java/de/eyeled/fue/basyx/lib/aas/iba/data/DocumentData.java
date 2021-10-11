package de.eyeled.fue.basyx.lib.aas.iba.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class DocumentData {
	
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String URL = "url";


    @SerializedName(ID)
	private String mId;
    
    @SerializedName(NAME)
	private String mName;
    
    @SerializedName(URL)
	private String mUrl;
    
	public DocumentData(String mName) {
		super();
		this.mName = mName;
	}
	public String getId() {
		return mId;
	}
	
	public void setId(String mId) {
		this.mId = mId;
	}
	
	public String getName() {
		return mName;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	
	public String getUrl() {
		return mUrl;
	}
	public void setUrl(String mUrl) {
		this.mUrl = mUrl;
	}
	public static DocumentData create(String json) {
		DocumentData data = null;
		if(json != null){
			try {
				Gson gson = new GsonBuilder().create();
				data = gson.fromJson(json, DocumentData.class);
			}
			catch(Exception e) {
				e.printStackTrace();
				System.err.println("error creating doc data: "+e.getMessage());
			}
		}
		return data;
	}
	
	
	
	
}