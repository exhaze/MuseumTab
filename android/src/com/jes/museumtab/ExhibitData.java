package com.jes.museumtab;

import com.google.gson.Gson;

public class ExhibitData {
	private String mUuid;
	private String mName;
	private String mDescription;
	
	public static ExhibitData fromJson(String jsonData) {
		Gson gson = new Gson();
		return gson.fromJson(jsonData, ExhibitData.class);
	}
	
	public ExhibitData(String uuid, String name, String desc) {
		mUuid = uuid;
		mName = name;
		mDescription = desc;
	}
	
	public String getUuid() {
		return mUuid;
	}

	public String getName() {
		return mName;
	}

	public String getDescription() {
		return mDescription;
	}

	String toJson()
	{
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
