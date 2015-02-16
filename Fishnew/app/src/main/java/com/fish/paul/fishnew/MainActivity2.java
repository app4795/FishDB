package com.fish.paul.fishnew;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


public class MainActivity2 extends ActionBarActivity {
    Button newBackButton;
    ListView listView;
    private ArrayAdapter<String> listAdapter;
    String [] scientificName=new String [60];
    int count=0;
    String toPasstoNextpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        setTitle("Serch Result");
        newBackButton=(Button)findViewById(R.id.backbutton);
        listView = (ListView)findViewById(R.id.listView);
        //=======Code For copying Existing Database file to system folder for use====//
        // Copying Existing Database into system folder
        try {

            String destPath = "/data/data/" + getPackageName()
                    + "/databases/TruelyGood.db";

            File f = new File(destPath);
            if(!f.exists()){
                Log.v("TAG","File Not Existed");
                InputStream in = getAssets().open("test.db");
                OutputStream out = new FileOutputStream(destPath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                    Log.v("TAG","FileGOOGOOOOOOOOO999");
                }
                in.close();
                out.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.v("TAG","ioexeption");
            e.printStackTrace();
        }
        Log.v("TAG","I am GOOD");
        DBManager dbManager =  new DBManager(this,"TruelyGood.db");
        Log.v("TAG","Database is there with version: "+dbManager.getReadableDatabase().getVersion());
        //String sql = "select * from db";
        Intent it =getIntent();
        String toSerchKeyWord=it.getStringExtra("ToSerchKeyWord");
        String sql = "select * from db WHERE scientificName like '%"+toSerchKeyWord+"%' or commonName like '%"+toSerchKeyWord+"%' or commonName2 like '%"+toSerchKeyWord+"%'";

        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        Log.v("TAG","Query Result:"+cursor);
        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        count=0;
        if (cursor.moveToFirst()) {
            do {
                scientificName[count++]=cursor.getString(1);
                listAdapter.add(cursor.getString(0)+" " + scientificName[count-1]  + "\n"+cursor.getString(2)+"\n");
            } while (cursor.moveToNext());
        }
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //Toast.makeText(getApplicationContext(),"ID：" + id +" 選單文字："+ listView.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),scientificName[(int)id],Toast.LENGTH_LONG).show();
                toPasstoNextpage= scientificName[(int)id];
                getNext(view);
            }
        });
        cursor.close();
        db.close();
        dbManager.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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
    public void backClick(View v){
        Intent it =getIntent();
        String toSerchKeyWord=it.getStringExtra("ToSerchKeyWord");
        Toast.makeText(MainActivity2.this, toSerchKeyWord, Toast.LENGTH_SHORT).show();
        finish();
    }
    public void getNext (View v){
        Log.d("debug", "button click");
        Intent it =new Intent(this,MainActivity3.class);
        it.putExtra("scientificName",toPasstoNextpage);
        startActivity(it);
    }
}
