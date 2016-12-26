package com.sutd.zhangzhexian.geolocatorapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getLocation(View view){
        Geocoder my_coder = new Geocoder(this);

        List<Address> mylist = null;

        EditText myText = (EditText)findViewById(R.id.editText);

        String location = myText.getText().toString();

        Intent myIntent = new Intent(Intent.ACTION_VIEW);

        String lat;

        String lon;

        try {

            mylist = my_coder.getFromLocationName(location, 1);


        } catch (IOException ex){
            Toast.makeText(getApplicationContext(), "Not able to find location", Toast.LENGTH_LONG).show();
        }
        try{
            lat = String.valueOf(mylist.get(0).getLatitude());

            lon = String.valueOf(mylist.get(0).getLongitude());

            startActivity(Intent.createChooser(myIntent, "Get location"));
        } catch (Exception ex2){
            Toast.makeText(getApplicationContext(), "A problem occured in retrieving location", Toast.LENGTH_LONG).show();
            return;
        }

        myIntent.setData(Uri.parse("geo:" + lat + "," + lon));

        Intent chooser = Intent.createChooser(myIntent, "Launch Maps");

        startActivity(chooser);
    }
}
