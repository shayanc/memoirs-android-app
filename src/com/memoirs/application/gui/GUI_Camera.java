package com.memoirs.application.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.memoirs.application.R;
import com.memoirs.application.functionality.CameraFunctionality;
import com.memoirs.application.functionality.CameraPreview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

public class GUI_Camera extends Activity {

	CameraFunctionality Camera = new CameraFunctionality();
	CameraPreview mPreview = null;
	Camera mCamera;

	public static Button btnKeep;
	public static Button btnDiscard;
	ImageButton captureButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set no title
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// set fullscreen.
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// set orientation
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// set layout defiend in camera_layout.xml
		setContentView(R.layout.camera_layout);

		// assign camera if available
		mCamera = Camera.mCamera;
		// Creates a preview to show live camera feed in, see Preview class
		// below
		mPreview = new CameraPreview(this.getApplicationContext(), mCamera);

		// creates preview frame , see FrameLayout in camera_layout.xml
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		// add to Framelayout mPreview which has SurfaceView showing Camera feed
		preview.addView(mPreview);

		// Adding listener
		btnKeep = (Button) findViewById(R.id.btnKeep);
		btnKeep.setVisibility(View.GONE);
		btnDiscard = (Button) findViewById(R.id.btnDiscard);
		btnDiscard.setVisibility(View.GONE);

		captureButton = (ImageButton) findViewById(R.id.button_capture);
		captureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Camera.takePhoto(v);
				captureButton.setEnabled(false);
//				btnDiscard.setVisibility(View.VISIBLE);
//				btnKeep.setVisibility(View.VISIBLE);

			}
		});

		btnDiscard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Camera.discardPhoto();
				btnDiscard.setVisibility(View.GONE);
				btnKeep.setVisibility(View.GONE);
				captureButton.setEnabled(true);
				captureButton.setVisibility(View.VISIBLE);
				
			}
		});

		btnKeep.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Camera.keepPhoto();
				
				 Toast.makeText(GUI_Camera.this, "Photo Saved!",
				 Toast.LENGTH_SHORT).show();
					Intent returnIntent = new Intent();
					// String test= pictureFile.toString();
					returnIntent.putExtra("RESULT", Camera.getOutputMediaFile().toString());
					setResult(RESULT_OK, returnIntent);
					// close this activity
					finish();	
			}
		});

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
		this.finish();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		this.finish();
	}

}
