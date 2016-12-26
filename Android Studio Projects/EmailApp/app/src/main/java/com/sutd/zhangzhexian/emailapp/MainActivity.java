package com.sutd.zhangzhexian.emailapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

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

    public void Hi(View view){
        Button test3 = (Button) findViewById(R.id.button);
        test3.setText("Hi");
        Intent obj_i = new Intent(this,SecondActivity.class);
        startActivity(obj_i);
    }

    public void radiobuttonClicked(View view){
        RadioButton radioButton = (RadioButton)findViewById(R.id.radioButton);
        Toast toast = Toast.makeText(getApplicationContext(), "Radio button selected", Toast.LENGTH_LONG);
        toast.show();

    }

    public void EmailFunction(View view){
        String[] TO = {"zhangzhexian@outlook.com"};
        String[] CC = {"zhangzhexian@outlook.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT,"Email message goes here");

        try{
            startActivity(Intent.createChooser(emailIntent,"Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(MainActivity.this,"There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
