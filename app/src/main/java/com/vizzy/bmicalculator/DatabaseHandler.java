package com.vizzy.bmicalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 20-12-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper{

    SQLiteDatabase db;
    Context context;

    public DatabaseHandler(Context context)
    {
        super(context,"Datadb",null,1);
        Log.d("123","db opened");
        this.context=context;
        db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Data(time TEXT,bmi TEXT)");
        Log.d("123","table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE if EXISTS Data");
        Log.d("123","table dropped");

    }

    public void add(String time,String bmi)
    {

        ContentValues values=new ContentValues();
        values.put("time",time);
        values.put("bmi",bmi);
        long rid=db.insert("Data",null,values);

        if(rid<0)
        {
            Toast.makeText(context, "Insert issue", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.d("123","data add");
            Toast.makeText(context, "Inserted", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<String> getdata()
    {
        Cursor cursor=db.query("Data",new String[]{"time", "bmi"},null,null,null,null,null);

        int currenttime=cursor.getColumnIndex("time");
        int index=cursor.getColumnIndex("bmi");

        cursor.moveToFirst();

        String details="";

        ArrayList<String> s=new ArrayList<String>();
        if(cursor!=null && (cursor.getCount()>0))
        {
            do {

                String time=cursor.getString(currenttime);
                String bmi=cursor.getString(index);

                details="On "+time+" your bmi was  "+bmi;

                s.add(details);


            }while (cursor.moveToNext());

            return s;
        }
        else
        {
            Log.d("123","data show");
            Toast.makeText(context, "No result to show", Toast.LENGTH_SHORT).show();

        }
        return s;
    }


    public void delete()
    {
        db.execSQL("DROP TABLE IF EXISTS Data");

    }

}
