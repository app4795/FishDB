package com.fish.paul.fishnew;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3 extends ActionBarActivity {
    Button newBack2Button;
    TextView fishdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3);
        newBack2Button=(Button)findViewById(R.id.backto2);
        fishdata=(TextView)findViewById(R.id.dataview);
        fishdata .setMovementMethod(ScrollingMovementMethod.getInstance());
        Intent it =getIntent();
        String toSerchKeyWordend=it.getStringExtra("scientificName");
        Toast.makeText(getApplicationContext(), toSerchKeyWordend, Toast.LENGTH_LONG).show();
        DBManager dbManager =  new DBManager(this,"TruelyGood.db");
        String sql = "select * from db WHERE scientificName like '%"+toSerchKeyWordend+"%'";
        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        setTitle("");
        if (cursor.moveToFirst()) {
            //String.format("num%d",cursor.getString(0))
            int id= getResources().getIdentifier(String.format("num%d",cursor.getInt(0)),"drawable","com.fish.paul.fishnew");
            Drawable drawable = this.getResources().getDrawable(id);
            drawable.setBounds(0, 0, 600, 600);//設置圖片大小
            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            setTitle(cursor.getString(1));
            SpannableString spanStr = new SpannableString("學名："+cursor.getString(1)+"\n"+"俗名："+cursor.getString(2)+"\n"+"　　　"+cursor.getString(3)+"\n"
                    +"平均長度："+cursor.getString(5)+"\n\n"+"特徵："+"\n"+cursor.getString(6)+"\n\n"+"習性："+"\n"+cursor.getString(7)+"\n\n"+"分佈："+"\n"+cursor.getString(8));
            spanStr.setSpan(span, spanStr.length()-1, spanStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            fishdata.setText(spanStr);
        }
        cursor.close();
        db.close();
        dbManager.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity3, menu);
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
    public void backto2Click(View v){
        finish();
    }
}
