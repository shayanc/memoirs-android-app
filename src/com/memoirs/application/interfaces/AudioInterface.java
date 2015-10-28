package com.memoirs.application.interfaces;

import java.io.IOException;

public interface AudioInterface {

	public abstract void stopPlayback();

	public abstract void playRecording() throws IllegalStateException,
			IOException;

	public abstract void stopRecording();

	public abstract void beginRecording() throws IllegalStateException,
			IOException;

	public abstract void releaseAll();
	
	public double getDuration();

}