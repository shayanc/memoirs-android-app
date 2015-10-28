package com.memoirs.application.functionality;

import com.memoirs.application.interfaces.MemoryInterface;
import com.memoirs.application.interfaces.MemoryManagerInterface;

import android.os.Bundle;

public class Memory implements MemoryInterface{
	private String name = null;
	private Bundle addInfo = null;
	private byte[] picture = null;
	private byte[] audioFile = null;
	public static Memory memoryInstance = null;
	
	private Memory (String name, Bundle addInfo, byte[] picture, byte[] audioFile){
		this.name = name;
		this.addInfo = addInfo;
		this.picture = picture;
		this.audioFile = audioFile;
	}
	
	public static Memory getInstance(){
		if (memoryInstance == null)
			memoryInstance = new Memory(null, null, null, null);
		return memoryInstance;
	}
	
	//to be called when finished creating a memory
	public static void resetMemoryInstance(){
		memoryInstance = new Memory(null, null, null, null);
	}
	
	public String getName() {
		return name;
	}

	public void setName( String name) {
		this.name = name;
	}

	public Bundle getAddInfo() {
		return addInfo;
	}

	public void setAddInfo( Bundle addInfo) {
		this.addInfo = addInfo;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture( byte[] picture) {
		this.picture = picture;
	}

	public byte[] getAudio() {
		return audioFile;
	}

	public void setAudio( byte[] audioFile) {
		this.audioFile = audioFile;
	}




}