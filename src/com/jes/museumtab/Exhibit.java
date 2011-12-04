package com.museumtab;

public class Exhibit {
	
	private int mId;
	private String mName;
	private String mDescription;
	private String mImagePath;
	
	// how do we pull an image in? from a file?
	public Exhibit(int id, String name, String desc, String imagePath) {
		mId = id;
		mName = name;
		mDescription = desc;
		mImagePath = imagePath;
	}
	
	// TODO: decide how to abstract storage of images, some options
	// option 1). make Exhibit a base class with android app/server having their 
	// 			  own implementations
	// option 2). wrapper around image class, abstracting away app/server 
	//			  storage specifics

}
