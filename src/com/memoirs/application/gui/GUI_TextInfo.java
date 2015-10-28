package com.memoirs.application.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.memoirs.application.R;
import com.memoirs.application.functionality.Memory;
import com.memoirs.application.functionality.MemoryManager;

public class GUI_TextInfo extends Activity {

	EditText editLocation;
	EditText editYear;
	EditText editTitle;
	MemoryManager mManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.add_text_info);
		
		mManager = new MemoryManager();
		
		//display previous inputed text on the edit fields
		editLocation = (EditText) findViewById(R.id.inputLocation);
		editYear = (EditText) findViewById(R.id.inputYear);
		editTitle = (EditText) findViewById(R.id.inputTitle);
		String memTitle = Memory.getInstance().getName();
		if (memTitle != null) editTitle.setText(memTitle);
		Bundle textInfo = Memory.getInstance().getAddInfo();
		if (textInfo != null){
			String memLocation = textInfo.getString("location");
			if (memLocation != null) editLocation.setText(memLocation);
			editLocation.setText(memLocation);
			String memYear = textInfo.getString("year");
			if (memYear != null) editYear.setText(memYear);
			//else System.out.println("no descript on addInfo");
		}
		//else System.out.println("addInfo is null");
	}
	
	public void handleTextInput(View view){
		//addInputtedTitleToMemoryObject(findViewById(R.id.inputTitle));
		setInputtedTitleToMemory( (EditText)findViewById(R.id.inputTitle));
		addInputtedAdditionalInfoToMemory();
		setResult(RESULT_OK);
		finish();
	}
	
	private String getTextFrom( EditText editText){
		return editText.getText().toString();
	}
	private void setInputtedTitleToMemory (EditText inputTitle) {
		String title = getTextFrom((inputTitle));
		inputTitle.setText(title);
		Memory.getInstance().setName(title);
	}
	private void addInputtedAdditionalInfoToMemory(){
		EditText editLocation = (EditText) findViewById(R.id.inputLocation);
		EditText editYear = (EditText) findViewById(R.id.inputYear);
		
		String inputLocation =  getTextFrom( editLocation);
		String inputYear = getTextFrom( editYear);
		Bundle addInfo = new Bundle();
		addInfo.putString("location", inputLocation);
		addInfo.putString("year", inputYear);
		Memory.getInstance().setAddInfo(addInfo);
	}
	/*
	public void dismissKeyboard(){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editTitle.getWindowToken(), 0);
	}*/
}