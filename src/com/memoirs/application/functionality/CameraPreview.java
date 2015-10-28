package com.memoirs.application.functionality;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback {
	SurfaceHolder sHolder;
	Camera mCamera;

	public CameraPreview(Context context, Camera camera) {
		super(context);
		this.mCamera = camera;
		sHolder = getHolder();
		sHolder.addCallback(this);
		this.setPadding(100, 100, 100, 100);
		// not used after 3.0 but needed before
		sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// surfaceview is created, get camera and start feed/preview

		try {
			// set camera orientation to portrait.
			mCamera.setDisplayOrientation(90);
			// add to preview and start feed
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera == null) {
			mCamera.stopPreview();
			mCamera.setPreviewCallback(null);
			mCamera.release();
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// camera parameters
		Camera.Parameters parameters = mCamera.getParameters();
		List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();

		// You need to choose the most appropriate previewSize for your app
		Camera.Size previewSize = previewSizes.get(0);
		parameters.setPreviewSize(previewSize.width, previewSize.height);

		// mCamera.startPreview();
		mCamera.autoFocus(null);
		if (mCamera == null) {
			mCamera.stopPreview();
			mCamera.release();
		}

	}
}
