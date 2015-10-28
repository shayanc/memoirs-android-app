package com.memoirs.application.gui;

import java.io.IOException;

import com.memoirs.application.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GUI_Menu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set no title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// set fullscreen.
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//set layout/xml file
		setContentView(R.layout.main_menu);
	}
	
	 public void createMemory(View v) throws IllegalArgumentException, IOException {
		 Intent intent = new Intent(v.getContext(), GUI_CreateMemory.class);
		 startActivity(intent);
	 }
	 public void seeMemory(View v) throws IllegalArgumentException, IOException {
		 Intent intent = new Intent(v.getContext(), GUI_RecallOptions.class);
		 startActivity(intent);
	 }

}
