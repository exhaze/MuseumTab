package com.jes.museumtab.backend;
import java.util.UUID;

import com.google.gson.Gson;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ExhibitData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mId;
	
	private String mUuid;
	private String mName;
	private String mDescription;
	
	public static ExhibitData fromJson(String jsonData) {
		Gson gson = new Gson();
		return gson.fromJson(jsonData, ExhibitData.class);
	}
	
	public ExhibitData(String name, String desc) {
		mUuid = UUID.randomUUID().toString();
		mName = name;
		mDescription = desc;
	}
	
	public Long getId() {
		return mId;
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
