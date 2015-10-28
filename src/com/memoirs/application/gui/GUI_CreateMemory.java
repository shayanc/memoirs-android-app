package com.memoirs.application.gui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.memoirs.application.R;
import com.memoirs.application.functionality.Memory;
import com.memoirs.application.functionality.MemoryManager;
import com.memoirs.network.async.interfaces.RequestErrorHolderInterface;
import com.memoirs.network.exceptions.NetworkModuleException;

public class GUI_CreateMemory extends Activity {

	static final int CAMERA_REQUEST = 1;
	static final int AUDIO_REQUEST = 2;
	static final int TEXT_INFO_REQUEST = 3;

	Button preview;
	MemoryManager mManager = new MemoryManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set no title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// set fullscreen.
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.create_memory);
		Memory.resetMemoryInstance();
	}

	// START MEMORY PREVIEW ACTIVITY
	public void preview(View v) throws IllegalArgumentException, IOException {
		System.out.println("test~@@@----STARTING DISPLAY MEMORY----@@@");
		Intent intent = new Intent(v.getContext(), GUI_PreviewMemory.class);
		startActivity(intent);
	}

	// ON CLICK START CAMERA ACTIVITY
	public void startCamera(View v) throws IllegalArgumentException,
	IOException {
		Intent cameraIntent = new Intent(GUI_CreateMemory.this,
				GUI_Camera.class);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);

	}

	public void startAudio(View v) throws IllegalArgumentException, IOException {
		Intent intent = new Intent(GUI_CreateMemory.this, GUI_AudioRecorder.class);
		startActivityForResult(intent, AUDIO_REQUEST);
	}

	public void inputTextInfo(View v) throws IllegalArgumentException,
	IOException {
		Intent intent = new Intent(GUI_CreateMemory.this, GUI_TextInfo.class);
		startActivityForResult(intent, TEXT_INFO_REQUEST);
	}

	//ON ClICK button finish
	public void finishCreatingMemory( View v){
		//check if the memory audio has been recorded
		if ( Memory.getInstance().getAudio() == null){
			Toast.makeText(getApplicationContext(), 
					"You haven't told me the story yet. Please click on the microphone and record the story.",
					Toast.LENGTH_LONG).show();
			return;
		}
		saveMemory();
		finish();
	}

	private void saveMemory() {
		Intent intent = new Intent(getApplicationContext(), WaitingWS.class);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CAMERA_REQUEST:
			if (resultCode == RESULT_OK) {
				////receives picture from intent by path file
				byte[] picture = mManager.toByteArray(data); 
				if (picture != null){
					Memory.getInstance().setPicture(picture);
	
					// display the taken photo on the camera icon button
					Bitmap bitmapImage = BitmapFactory.decodeByteArray(picture, 0,
							picture.length);
					ImageView cameraButton = (ImageView) findViewById(R.id.btnCamera);
					cameraButton.setImageBitmap(bitmapImage);
				}
			}
			break;
		case AUDIO_REQUEST:
			if (resultCode == RESULT_OK) {
				byte[] audioRecord = mManager.toByteArray(data);
				if (audioRecord != null) {
					Memory.getInstance().setAudio(audioRecord);
					TextView auLength = (TextView) findViewById(R.id.audioLength);
					//TODO display lenght of the audio rather than the byte array's
					double audio_duration = data.getDoubleExtra("DURATION", 0);
					auLength.setText("Audio length: \n"+(String.format(
							"%d min, %d sec",
							TimeUnit.MILLISECONDS.toMinutes((long) audio_duration),
							TimeUnit.MILLISECONDS.toSeconds((long) audio_duration)
									- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
											.toMinutes((long) audio_duration)))));
				}
			}
			break;
		case TEXT_INFO_REQUEST:
			if (resultCode == RESULT_OK) {
				// update text views to display user input
				TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
				TextView txtLocation = (TextView) findViewById(R.id.txtLocation);
				TextView txtYear = (TextView) findViewById(R.id.txtYear);
				Memory memory = Memory.getInstance();
				Bundle addInfo = memory.getAddInfo();
				String memoryInfo;
				memoryInfo = memory.getName();
				if (memoryInfo != null)
					txtTitle.setText(memoryInfo);
				memoryInfo = addInfo.getString("location");
				if (memoryInfo != null)
					txtLocation.setText(memoryInfo);
				memoryInfo = addInfo.getString("year");
				if (memoryInfo != null)
					txtYear.setText(memoryInfo);
			}
			break;
		}
	}
}