package com.jes.museumtab;

import java.util.UUID;

public class ExhibitData {
	private Long mId;
	
	private String mUuid;
	private String mName;
	private String mDescription;
	
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
}
