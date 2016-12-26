package xbee.udootest;

import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import me.palazzetti.adktoolkit.AdkManager;

public class MainActivity extends Activity{

	private static final String TAG = "UDOO_AndroidADKFULL";

    private AdkManager mAdkManager;

    private TextView light;
    private TextView humidity;
    private TextView temperature;
    private TextView vibration;
    private TextView openorclose;

    private AdkReadTask mAdkReadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdkManager = new AdkManager((UsbManager) getSystemService(Context.USB_SERVICE));

//		register a BroadcastReceiver to catch UsbManager.ACTION_USB_ACCESSORY_DETACHED action
        registerReceiver(mAdkManager.getUsbReceiver(), mAdkManager.getDetachedFilter());
        light  = (TextView) findViewById(R.id.textView_light);
        humidity  = (TextView) findViewById(R.id.textView_humidity);
        temperature  = (TextView) findViewById(R.id.textView_temperature);
        vibration  = (TextView) findViewById(R.id.textView_vibration);
        openorclose  = (TextView) findViewById(R.id.textView_openorclose);
    }

    @Override
    public void onResume() { // activity is in foreground (running state)
        super.onResume();
        mAdkManager.open();

        mAdkReadTask = new AdkReadTask();
        mAdkReadTask.execute();
    }

    @Override
    public void onPause() {  // activity is not in the foreground but still alive
        super.onPause();
        mAdkManager.close();

        mAdkReadTask.pause();
        mAdkReadTask = null;
    }

    @Override
    public void onDestroy() { // activity is about to be destroyed
        super.onDestroy();  // call super class implementation of onDestroy
        unregisterReceiver(mAdkManager.getUsbReceiver());
    }

    /*
     * We put the readSerial() method in an AsyncTask to run the
     * continuous read task out of the UI main thread
     */
    private class AdkReadTask extends AsyncTask<Void, String, Void> {

        private boolean running = true;

        public void pause(){
            running = false;
        }

        protected Void doInBackground(Void... params) {
	    	//Log.i("ADK demo bi", "start adkreadtask");
            while(running) {
                publishProgress(mAdkManager.readSerial()) ;
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {

            int ligh = (int)progress[0].charAt(0);
            int humidi = (int)progress[0].charAt(1);
            int tempera = (int)progress[0].charAt(2);
            int vibra = (int)progress[0].charAt(3);
            int openor = (int)progress[0].charAt(4);

//            DecimalFormat df = new DecimalFormat("#.#");
            light.setText(ligh + " light unit");
            humidity.setText(humidi + " humidity unit");
            temperature.setText(tempera + " temperature unit");
            vibration.setText(vibra + " vibration unit");
            openorclose.setText(openor + "");

        }
    }
}
