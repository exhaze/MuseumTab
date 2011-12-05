package com.jes.museumtab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.*;

public class MuseumTabActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibit);
        
        // example code showing how to open file in private subdir
//        try {
//        	this.getContentResolver();
//        	File imagesDir = getDir("images", MODE_PRIVATE);
//        	
//	        FileOutputStream fos = new FileOutputStream(
//	        		imagesDir.getAbsolutePath() + File.separatorChar + "blah.jpg");
//	        
//	        byte[] buffer = new byte[100];
//	        buffer[0] = 'h';
//	        
//	        fos.write(buffer);
//	        fos.close();
//	        
//	        FileInputStream fis = new FileInputStream(
//	        		imagesDir.getAbsolutePath() + File.separatorChar + "blah.jpg");
//	        
//	        byte[] buffer2 = new byte[200];
//	        
//	        fis.read(buffer2);
//	        
//	        System.out.println(buffer2);
//	        
//        } catch (FileNotFoundException e) {
//        	e.printStackTrace();
//        } catch (IOException e) {
//        	e.printStackTrace();
//        }
    }
}