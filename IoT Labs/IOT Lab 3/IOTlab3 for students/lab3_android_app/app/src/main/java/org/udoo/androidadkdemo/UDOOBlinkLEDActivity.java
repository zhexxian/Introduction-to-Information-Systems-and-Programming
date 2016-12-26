package org.udoo.androidadkdemo;

import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import me.palazzetti.adktoolkit.AdkManager;

public class UDOOBlinkLEDActivity extends Activity{
	
//	private static final String TAG = "UDOO_AndroidADK";

	private AdkManager mAdkManager;
	
	private ToggleButton buttonLED;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// initialize a ADK manager to create a connection object between
		// your device and the accessory
		mAdkManager = new AdkManager((UsbManager) getSystemService(Context.USB_SERVICE));
		

		buttonLED = (ToggleButton) findViewById(R.id.toggleButtonLED);		
		
		Log.i("ADK manager", "available: " + mAdkManager.serialAvailable());
	}
	@Override
	public void onResume() {
		super.onResume();  // call super class implementation of onResume
		mAdkManager.open(); // to start AOA protocol communication
	}
 
	@Override
	public void onPause() { // activity is not in the foreground but still alive
		super.onPause();
		mAdkManager.close();
	}

	@Override
    protected void onDestroy() { // activity is about to be destroyed
        super.onDestroy();

    }
 
	// ToggleButton method - send message to the SAM3X8E with the AdkManager
	public void blinkLED(View v) {
		if (buttonLED.isChecked()) { 
			// writeSerial() allows you to write a single char or a String object.
			mAdkManager.writeSerial("1");
		} else {
			mAdkManager.writeSerial("0"); 
		}	
	}
 
}
