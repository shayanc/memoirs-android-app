package com.memoirs.application.gui;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.memoirs.application.R;
import com.memoirs.application.functionality.Memory;
import com.memoirs.application.functionality.MemoryManager;
import com.memoirs.application.interfaces.MemoryInterface;
import com.memoirs.network.async.interfaces.RequestErrorHolderInterface;
import com.memoirs.network.async.interfaces.WSRequestListener;
import com.memoirs.network.exceptions.CommunicationException;
import com.memoirs.network.exceptions.ConnectionException;
import com.memoirs.network.exceptions.MalformedJSONException;
import com.memoirs.network.exceptions.NetworkModuleException;
import com.memoirs.network.exceptions.WebServiceException;

public class WaitingWS extends Activity implements WSRequestListener {
	
	private String phoneNo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting_ws);
		Intent intent = getIntent();
		if (intent.hasExtra("phoneNo")){ //recall memory
			phoneNo = intent.getStringExtra("phoneNo");
			retrieveMemoryBy(phoneNo);
			TextView message = (TextView) findViewById(R.id.lblUploading);
			message.setText("Retrieving memory...");
		}
		else { //create memory
			phoneNo = null;
			MemoryManager mManager = new MemoryManager();
			mManager.saveMemory(this);
		}
		//finish();
	}

	private void showMemory(String phoneNo) {
		if (Memory.getInstance().getAudio() != null) {
			Intent display = new Intent(getApplicationContext(),
					DisplayCreatedMemory.class);
			display.putExtra("phoneNo", phoneNo);
			startActivity(display);
		} else {
			System.out.println("Couldn't display memory");
			Toast.makeText(getApplicationContext()
					,"wrong memory id", Toast.LENGTH_LONG).show();
		}
	}

	private void retrieveMemoryBy(String phoneNo) {
		MemoryManager mManager = new MemoryManager();
		mManager.retrieveMemory(phoneNo, this);
	}

	@Override
	public void onCreateMemoryDone(String phone_number,
			RequestErrorHolderInterface errorHolder) {
		try {
			if(!errorHolder.hasErrorOccurred()){
				System.out.println("WS listener: "+phone_number);
				Toast.makeText(getApplicationContext(),
					"memory successfully saved", Toast.LENGTH_LONG).show();
				showMemory(phone_number);
			}
		} catch (UnsupportedEncodingException e) {
		
		} catch (NetworkModuleException e) {
			Toast.makeText(getApplicationContext(),
					e.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
		}
		finish();
	}

	@Override
	public void onRetrieveMemoryDone(MemoryInterface memory,
			RequestErrorHolderInterface errorHolder) {
		try {
			if (!errorHolder.hasErrorOccurred()){
				Toast.makeText(getApplicationContext(),
						"memory successfully retrieved", Toast.LENGTH_LONG).show();
				showMemory(phoneNo);
			}
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(getApplicationContext(),
					e.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			e.printStackTrace();
		} catch (NetworkModuleException e) {
			Toast.makeText(getApplicationContext(),
					e.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			e.printStackTrace();
		}
		finish();
	}

	@Override
	public void onDeleteMemoryDone(RequestErrorHolderInterface errorHolder) {
		try {
			if (!errorHolder.hasErrorOccurred()){
				Toast.makeText(getApplicationContext(),
						"memory successfully deleted", Toast.LENGTH_LONG).show();
			}
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(getApplicationContext(),
						e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (NetworkModuleException e) {
			Toast.makeText(getApplicationContext(),
						e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		finish();
	} 


}