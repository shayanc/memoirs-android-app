package com.memoirs.application.interfaces;

import android.os.Bundle;

public interface MemoryInterface {

	public String getName();
	public void setName(String name);
	
	public Bundle getAddInfo();
	public void setAddInfo(Bundle addInfo);
	
	public byte[] getPicture();
	public void setPicture(byte[] picture);

	public byte[] getAudio();
	public void setAudio(byte[] audioFile);
}
