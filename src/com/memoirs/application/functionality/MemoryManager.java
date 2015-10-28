package com.memoirs.application.functionality;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;


import com.memoirs.application.gui.WaitingWS;
import com.memoirs.application.interfaces.MemoryManagerInterface;
import com.memoirs.network.NetworkModule;
import com.memoirs.network.async.AsyncNetworkModuleWrapper;

public class MemoryManager implements MemoryManagerInterface {

	Memory memory;

	public MemoryManager() {
		memory = Memory.getInstance();
	}

	@Override
	public Bitmap getBitmapPicture(byte[] picture) {
		return BitmapFactory.decodeByteArray(picture, 0, picture.length);
	}

	@Override
	public byte[] getAudioFile() {
		return memory.getAudio();
	}

	@Override
	public String getTitle() {
		String title = "";
		if (memory.getName() != null)
			title = memory.getName();
		return title;
	}

	//returns additional information inside the bundle
	@Override
	public String getAddInfoToBeDisplayed() {
		Bundle addInfo;		
		String txtAddInfo = "";
		if (memory.getAddInfo() != null){
			addInfo = memory.getAddInfo();
			String location = addInfo.getString("location");
			if (location != null && location != "")
				txtAddInfo += addInfo.getString("location") + "\n";
			String year = addInfo.getString("year");
			if (year != null && year != "") 
				txtAddInfo += addInfo.getString("year");
			return txtAddInfo;
		}
		return null;
	}

	@Override
	public String getPhoneNo() {
		// TODO Auto-generated method stub
		return null;
	}

	// get the byte array representation of the data contented on the intent
	public byte[] toByteArray(Intent data) {
		String path = data.getStringExtra("RESULT");
		// System.out.println(path);
		File file = new File(path);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedInputStream bis = new BufferedInputStream(fis);
		byte[] buffer = new byte[(int) file.length()];
		try {
			bis.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer;
	}

	@Override
	public Bitmap getBitmapPicture() {
		Bitmap bitmapImage = null;
		if (memory.getPicture() != null) {
			bitmapImage = BitmapFactory.decodeByteArray(memory.getPicture(), 0,
					memory.getPicture().length);
		}
		return bitmapImage;
	}

	public void saveMemory(WaitingWS listener){
		NetworkModule netMod = new NetworkModule();
		
		//WaitingWS listener = new WaitingWS();
		AsyncNetworkModuleWrapper async = new AsyncNetworkModuleWrapper(netMod, listener);
		async.createMemory(Memory.getInstance());
//		Toast memSavedMessage = Toast.makeText(c, "Memory saved", Toast.LENGTH_SHORT);
//		memSavedMessage.show();
	}

	public void retrieveMemory( String phoneNo, WaitingWS listener) {
		NetworkModule netMod = new NetworkModule();
		//WaitingWS listener = new WaitingWS();
		AsyncNetworkModuleWrapper async = new AsyncNetworkModuleWrapper(netMod, listener);
		async.retrieveMemory(phoneNo, Memory.getInstance());
	}
}
