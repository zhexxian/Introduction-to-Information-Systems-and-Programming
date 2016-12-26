package com.sutd.zhangzhexian.readandwritefileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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

    public void saveText(View view){

        EditText editText = (EditText)findViewById(R.id.editText);

        String text_msg = editText.toString();

        try{
            FileOutputStream fout = openFileOutput ("mytext.txt", MODE_PRIVATE);

            OutputStreamWriter streamWriter = new OutputStreamWriter(fout);

            streamWriter.write(text_msg);
            streamWriter.close();
        } catch (IOException ex){
            System.out.println(ex);
        }

        Toast.makeText(getApplicationContext(), "File saved successfully!", Toast.LENGTH_LONG).show();

    }

    public void readText(View view){

        try {

            FileInputStream f_in = openFileInput("mytext.txt");

            InputStreamReader inputStream = new InputStreamReader(f_in);

            int charRead;

            char[] buffer = new char[100];

            String line="";

            while ((charRead=inputStream.read(buffer))>0) {

                String l = String.copyValueOf(buffer, 0, charRead);

                line+=l;
            }

                inputStream.close();

                Toast.makeText(getApplicationContext(), line, Toast.LENGTH_LONG).show();

                Toast.makeText(getApplicationContext(), "File read successfully!", Toast.LENGTH_LONG).show();


        } catch (IOException ex){
            System.out.println(ex);
        }
    }
}
