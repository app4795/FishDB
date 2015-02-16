package com.fish.paul.fishnew;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends ActionBarActivity {
    TextView newlable;
    EditText newEnterText;
    Button newEnterButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Taiwan Common Cichlidae Database");
        newlable = (TextView) findViewById(R.id.textViewFish);
        newEnterText = (EditText) findViewById(R.id.editTextFish);
        newEnterButton=(Button)findViewById(R.id.enterbutton);
        //=======Code For copying Existing Database file to system folder for use====//
        // Copying Existing Database into system folder
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
    public void getEnter (View v){
        DBManager dbManager =  new DBManager(this,"ThisTimeWillFail.db");
        dbManager.close();
        Log.v("TAG","Database is there with version: "+dbManager.getReadableDatabase().getVersion());
        String content = newEnterText.getText().toString();
        newEnterText.setText("");
        Log.d("debug", "button click");
        Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
        Intent it =new Intent(this,MainActivity2.class);
        it.putExtra("ToSerchKeyWord",content);
        startActivity(it);
    }
}
