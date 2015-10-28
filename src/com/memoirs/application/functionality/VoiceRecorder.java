package com.memoirs.application.functionality;

import java.io.File;
import java.io.IOException;

import com.memoirs.application.interfaces.AudioInterface;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class VoiceRecorder implements AudioInterface {
	private String OUTPUT_FILE;
	private MediaPlayer mediaPlayer;
	private MediaRecorder mediaRecorder;

	public VoiceRecorder(){
		OUTPUT_FILE = Environment.getExternalStorageDirectory()
				+ "/audiorecoder.3gpp";
		mediaPlayer = new MediaPlayer();
	}
	
	public void stopPlayback() {
		if (mediaPlayer != null)
			mediaPlayer.stop();
	}

	public void playRecording() throws IllegalStateException, IOException {
		destroyMediaPlayer();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(getOutputMediaFile().getPath());
		mediaPlayer.prepare();
		mediaPlayer.start();

	}

	public void stopRecording() {
		if (mediaRecorder != null)
			mediaRecorder.stop();
			destroyRecorder();
	}

	public void beginRecording() throws IllegalStateException, IOException {
		destroyRecorder();
		File audioFile = getOutputMediaFile();
		System.out.println("AudioPath@@@@@@@@");
		System.out.println(audioFile.getPath());

		if (audioFile.exists())
			audioFile.delete();
		mediaRecorder = new MediaRecorder();
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mediaRecorder.setOutputFile(audioFile.getPath());
		mediaRecorder.prepare();
		mediaRecorder.start();

		System.out.println("file printed to :::::::::" + OUTPUT_FILE);

	}
	
	public void releaseAll() {
		destroyRecorder();
		destroyMediaPlayer();	
	}
	
	private void destroyRecorder() {
		if (mediaRecorder != null)
			mediaRecorder.release();
	}

	private void destroyMediaPlayer() {
		if (mediaPlayer != null) {
			try {
				mediaPlayer.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static File getOutputMediaFile() {
		File mediaStorageDir = new File(
				Environment.getExternalStorageDirectory(), "Memoirs");
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("Memoirs", "unable to create new directory");
				return null;
			}
		}
		File mediaFile;
		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "AudioMemory.3gpp");

		return mediaFile;
	}

	public double getDuration(){ 
		destroyMediaPlayer();
		mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(getOutputMediaFile().getPath());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		double duration = mediaPlayer.getDuration();			
		destroyMediaPlayer();
		releaseAll();
		
		return duration;
		
	}
}
