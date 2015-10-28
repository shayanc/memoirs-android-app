package com.memoirs.application.functionality;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.memoirs.application.gui.GUI_Camera;


import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;
import android.view.View;


public class CameraFunctionality {
	public static Camera mCamera = null;
	private byte[] PictureHolder = null; // holds picture to be saved.

	public CameraFunctionality() {
		mCamera = getCameraInstance();
	}

	/**
	 * Helper method to access the camera returns null if it cannot get the
	 * camera or does not exist
	 */
	public Camera getCameraInstance() {
		Camera camera = null;
		try {
			camera = Camera.open();
			System.out.println("Camera Opened Successfully");
		} catch (Exception e) {
			System.out.println("cannot get camera or does not exist");
		}

		return camera;
	}

	public void takePhoto(View v) {
		Camera.Parameters p = mCamera.getParameters();
		p.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
		mCamera.setParameters(p);

		mCamera.autoFocus(new Camera.AutoFocusCallback() {
			@Override
			public void onAutoFocus(boolean success, Camera camera) {
				if (success) {
					// captureButton.setEnabled(false);
					mCamera.takePicture(null, null, mPicture);
				} else {
					// captureButton.setEnabled(false);
					mCamera.takePicture(null, null, mPicture);
				}
			}
		});
	}

	public void discardPhoto() {
		if (mCamera != null && PictureHolder != null) {
			mCamera.startPreview();
			PictureHolder = null;
		}
	}

	public void keepPhoto() {
		if (PictureHolder != null) {
			if (mCamera != null) {
				mCamera.release();
				mCamera = null;

			}

			File pictureFile = getOutputMediaFile();
			System.out.println("@@:::::" + pictureFile.getPath());
			try {
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(PictureHolder);
				fos.close();
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
			
			//save picture to memory
			Memory memory = Memory.getInstance();
			memory.setPicture(PictureHolder);
		}
	}

	PictureCallback mPicture = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			PictureHolder = data;
			GUI_Camera.btnDiscard.setVisibility(View.VISIBLE);
			GUI_Camera.btnKeep.setVisibility(View.VISIBLE);
		}
	};

	public static File getOutputMediaFile() {
		File mediaStorageDir = new File(
				Environment.getExternalStorageDirectory(), "Memoirs");
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("Memoirs", "failed to create directory");
				return null;
			}
		}
		// Create a media file name
		File mediaFile;
		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + "ObjectMemory" + ".jpg");
		return mediaFile;
	}

}
