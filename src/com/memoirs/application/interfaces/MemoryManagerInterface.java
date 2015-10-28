package com.memoirs.application.interfaces;

import java.io.FileDescriptor;

import com.memoirs.application.gui.WaitingWS;

import android.content.Context;
import android.graphics.Bitmap;

public interface MemoryManagerInterface {

	public Bitmap getBitmapPicture();

	public byte[] getAudioFile();

	public String getTitle(); // may be unecessary now, but may be useful if
								// singleton is decided not to used anymore

	public String getAddInfoToBeDisplayed();

	// public getQRcode(); //TODO add the type that should be returned for
	// displaying QR code
	public String getPhoneNo();

	Bitmap getBitmapPicture(byte[] picture);
	
	public void saveMemory(WaitingWS listener);
	
	public void retrieveMemory( String phoneNo, WaitingWS listener);
}
