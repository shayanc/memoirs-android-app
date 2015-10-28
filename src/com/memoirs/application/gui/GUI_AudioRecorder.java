package com.memoirs.application.gui;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Audio;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.memoirs.application.R;
import com.memoirs.application.functionality.VoiceRecorder;
import com.memoirs.application.interfaces.AudioInterface;

@SuppressLint("NewApi")
public class GUI_AudioRecorder extends Activity {

	Button btnMic, btnRecord, btnDone;

	enum RunningState {
		RECORDING, STOPED;
	}

	RunningState state = RunningState.STOPED;
	AudioInterface audioRecorder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set no title
				requestWindowFeature(Window.FEATURE_NO_TITLE);
		// set fullscreen.
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// set orientation
		setContentView(R.layout.audio_recorder);
		//supporting functionalities classes
		audioRecorder = new VoiceRecorder();
	}

	public void recording(View v) throws IllegalArgumentException, IOException {
		Button btnDone = (Button) findViewById(R.id.btnDone);
		Button btnRecord = (Button) findViewById(R.id.btnRecord);

		if (state == RunningState.RECORDING) {
			state = RunningState.STOPED;
			btnRecord.setText("Record");
			btnDone.setEnabled(true);
			audioRecorder.stopRecording();
			Toast.makeText(GUI_AudioRecorder.this, "Audio file saved.",
					Toast.LENGTH_LONG).show();
		} else if (state == RunningState.STOPED) {
			state = RunningState.RECORDING;
			btnDone.setEnabled(false);
			btnRecord.setText("Stop");
			
//			MediaPlayer player = MediaPlayer.create(this,
//				    Settings.System.DEFAULT_RINGTONE_URI);
//				player.start();
			try {
				audioRecorder.beginRecording();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sendResult(View v) throws IllegalArgumentException, IOException {
		Intent returnIntent = new Intent();
		//send back path of the file.
		returnIntent.putExtra("RESULT", VoiceRecorder.getOutputMediaFile().getPath());
		returnIntent.putExtra("DURATION", audioRecorder.getDuration());
		setResult(RESULT_OK, returnIntent);
		// close this activity
		finish();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		audioRecorder.releaseAll();
		finish();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		audioRecorder.releaseAll();
		finish();
	}
}
