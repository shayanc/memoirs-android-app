package com.memoirs.application.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.memoirs.application.R;
import com.memoirs.application.functionality.Memory;
import com.memoirs.application.functionality.MemoryManager;
import com.memoirs.application.interfaces.MemoryManagerInterface;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class GUI_PreviewMemory extends Activity {

	// -----Audio Playback variables
	private MediaPlayer mediaPlayer;
	private ImageButton playButton, pauseButton;
	private SeekBar seekBar;

	private double startTime = 0;
	private double endTime = 0;

	private TextView startTimeField, endTimeField;

	private Handler myHandler = new Handler();;
	private int forwardTime = 5000;
	private int backwardTime = 5000;
	public  int oneTimeOnly = 0;

	private enum PLAYBACK_STATUS{STOPPED,PLAYING,PAUSED};
	private PLAYBACK_STATUS playbackStatus;
	File audioFile;
	// -------------------------------------
	
	MemoryManagerInterface mManager = new MemoryManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set no title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// set fullscreen.
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// assign layout xml file to this activity
		setContentView(R.layout.preview_memory);
		
		System.out.println("displaying memory actv started");
		//set Display Image from memory
		ImageView imagePreview = (ImageView) findViewById(R.id.imagePreview);
		if (mManager.getBitmapPicture() != null)
				imagePreview.setImageBitmap(mManager.getBitmapPicture());
		// display title of the memory		
		TextView displayTitle = (TextView) findViewById(R.id.displayTitle);
		displayTitle.setText(mManager.getTitle());

		// display additional text information
		TextView displayAddInfo = (TextView) findViewById(R.id.displayAddInfo);
		String addInfo = mManager.getAddInfoToBeDisplayed();
		if (addInfo != null) displayAddInfo.setText(addInfo);
		
		if (Memory.getInstance().getAudio() == null){
			System.out.println("no audio");
		}
		// set up audio to play from memory object
		playButton = (ImageButton) findViewById(R.id.btn_play);
		pauseButton = (ImageButton) findViewById(R.id.btn_pause);
		seekBar = (SeekBar) findViewById(R.id.seekbar);

		mediaPlayer = new MediaPlayer();
		createTempAudio(mManager.getAudioFile());

		startTimeField = (TextView) findViewById(R.id.startTime);
		endTimeField = (TextView) findViewById(R.id.endTime);

		
		/**
		 * Setting up a listener object to capture the playback completion event.
		 */
		mediaPlayer.setOnCompletionListener( new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				//When this is called, it means that the audio playback has finished.
				stop ();
			}
		});
		
		
		//Initialize the playback commands and status
		resetPlayback();

	}

	
	public void resetPlayback (){
		seekBar.setClickable(false);
		seekBar.setProgress(0);
		pauseButton.setEnabled(false);
		playButton.setEnabled(true);
		
		playbackStatus = PLAYBACK_STATUS.STOPPED;
		try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createTempAudio(byte[] sound) {
		if (sound != null) {
			try {
				audioFile = File.createTempFile("tempAudio", "3gpp",
						getCacheDir());
				audioFile.deleteOnExit();
				FileOutputStream fos = new FileOutputStream(audioFile);
				fos.write(sound);
				fos.close();
				FileInputStream fis;
				fis = new FileInputStream(audioFile);
				mediaPlayer.setDataSource(fis.getFD());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			playButton.setEnabled(false);
			pauseButton.setEnabled(false);
		}
	}

	public void play(View v) throws IllegalArgumentException, IOException {
		
		if (audioFile == null){
			Toast.makeText(getApplicationContext(), "No memory has been recorded yet", Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(getApplicationContext(), "Playing Memory",
				Toast.LENGTH_SHORT).show();

		
		playButton.setEnabled(false);
		pauseButton.setEnabled(true);

		// handle time and seekbar
		endTime = mediaPlayer.getDuration();
		if (playbackStatus == PLAYBACK_STATUS.PAUSED)
			startTime = mediaPlayer.getCurrentPosition();
		else
			startTime = 0;
		if (oneTimeOnly == 0) {
			seekBar.setMax((int) endTime);
			oneTimeOnly = 1;
		}
		endTimeField.setText(String.format(
				"%d min, %d sec",
				TimeUnit.MILLISECONDS.toMinutes((long) endTime),
				TimeUnit.MILLISECONDS.toSeconds((long) endTime)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes((long) endTime))));
		startTimeField.setText(String.format(
				"%d min, %d sec",
				TimeUnit.MILLISECONDS.toMinutes((long) startTime),
				TimeUnit.MILLISECONDS.toSeconds((long) startTime)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes((long) startTime))));
		seekBar.setProgress((int) startTime);
		myHandler.postDelayed(UpdateSongTime, 100);
		
		//change the status of playback to playing
		playbackStatus = PLAYBACK_STATUS.PLAYING;
		
		mediaPlayer.start();

	}

	private Runnable UpdateSongTime = new Runnable() {
		public void run() {
			if (playbackStatus == PLAYBACK_STATUS.PLAYING){
				startTime = mediaPlayer.getCurrentPosition();
				startTimeField.setText(String.format(
						"%d min, %d sec",
						TimeUnit.MILLISECONDS.toMinutes((long) startTime),
						TimeUnit.MILLISECONDS.toSeconds((long) startTime)
								- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
										.toMinutes((long) startTime))));
				seekBar.setProgress((int) startTime);
				myHandler.postDelayed(this, 100);
			}
		}
	};

	public void pause(View v) {
		Toast.makeText(getApplicationContext(), "Pausing sound",
				Toast.LENGTH_SHORT).show();

		mediaPlayer.pause();
		pauseButton.setEnabled(false);
		playButton.setEnabled(true);
		
		//set playback status to paused
		playbackStatus = PLAYBACK_STATUS.PAUSED;
	}

	public void rewind(View view) {
		int temp = (int) startTime;
		if ((temp - 5000) > 0) {
			startTime = startTime - backwardTime;
			mediaPlayer.seekTo((int) startTime);
		} else {
			Toast.makeText(getApplicationContext(),
					"Cannot jump backward 5 seconds", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public void stop (){
		mediaPlayer.stop();
		
		//resets the playback commands and status
		resetPlayback();
	}
	
	public void forward(View view) {
		int temp = (int) startTime;
		if ((temp + 5000) <= endTime) {
			startTime = startTime + forwardTime;
			mediaPlayer.seekTo((int) startTime);
		} else {
			Toast.makeText(getApplicationContext(),
					"Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	protected void onPause() {
		//Check if we are playing the audio
		if (playbackStatus == PLAYBACK_STATUS.PLAYING)
			pause (null);
		super.onPause();
		//finish();
	}
	
	@Override
	protected void onStop (){
		//Check if we are playing the audio
		if (playbackStatus == PLAYBACK_STATUS.PLAYING)
			pause (null);
		super.onPause();
		//finish();
	}
	
	@Override
	protected void  onDestroy (){
		if (playbackStatus != PLAYBACK_STATUS.STOPPED)
			stop ();
		super.onDestroy();
	}
	
	

}
