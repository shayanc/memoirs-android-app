package com.memoirs.application.gui;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.memoirs.application.R;
import com.memoirs.application.functionality.Memory;
import com.memoirs.application.functionality.MemoryManager;
import com.memoirs.application.interfaces.MemoryInterface;
import com.memoirs.network.async.interfaces.RequestErrorHolderInterface;
import com.memoirs.network.exceptions.NetworkModuleException;

public class GUI_RecallOptions extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.recall_memory_options);
		Memory.resetMemoryInstance();
	}

	// ON CLICK scan QR code btn
	public void QRcodeScan(View view) {
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();
	}

	// displays dialog to input phone number
	public void insertPhoneNumber(View view) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Insert phone Number");
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String phoneNo = input.getText().toString();
				if (phoneNo != null) {
					callWSRequestActivity(phoneNo);
					finish();
				}
			}
		});
		alert.show();
	}

	// ******************Retrieve Scanning Results******************************
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// retrieve scan result
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			// String scanFormat = scanningResult.getFormatName();
			// formatTxt.setText("FORMAT: " + scanFormat);
			// contentTxt.setText("CONTENT: " + scanContent);
			callWSRequestActivity(scanContent);

		} else {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Error getting data!", Toast.LENGTH_SHORT);
			toast.show();
		}
		finish();
	}

	private void callWSRequestActivity(String memoryId) {
		if (memoryId != null) {
			Intent retrieveMem = new Intent(getApplicationContext(), WaitingWS.class);
			retrieveMem.putExtra("phoneNo", memoryId);
			startActivity(retrieveMem);
		}
	}
	
	

	// to enocde
	// http://chart.googleapis.com/chart?cht=qr&chs=300x300&chl=http://www.cognation.net
	// API is found here,
	// https://developers.google.com/chart/infographics/docs/qr_codes?csw=1#overview
}