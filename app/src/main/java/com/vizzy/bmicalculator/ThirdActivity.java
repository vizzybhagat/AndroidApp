package com.vizzy.bmicalculator;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class ThirdActivity extends AppCompatActivity {

    DatabaseHandler db;
    SharedPreferences sp;
    TextView tvData,tvData1,tvData2,tvData3,tvData4;
    Button btnShare,btnSave,btnBack;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


         db=new DatabaseHandler(this);
        tvData=(TextView)findViewById(R.id.tvData);
        tvData1=(TextView)findViewById(R.id.tvData1);
        tvData2=(TextView)findViewById(R.id.tvData2);
        tvData3=(TextView)findViewById(R.id.tvData3);
        tvData4=(TextView)findViewById(R.id.tvData4);

        btnShare=(Button)findViewById(R.id.btnShare);
        btnBack=(Button)findViewById(R.id.btnBack);
        btnSave=(Button)findViewById(R.id.btnSave);

        sp=getSharedPreferences("myp1",MODE_PRIVATE);


        String bmi =sp.getString("bmi","");

        tvData.setText("Your bmi is "+bmi);

        double bmivalue=Double.parseDouble(bmi);

        if(bmivalue<18.5)   {
            tvData.setText("Your bmi is "+bmi+" and you are underweight");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("d","You are underweight");
            editor.commit();
        }
        if(bmivalue>=18.5 && bmivalue<25) {
            tvData.setText("Your bmi is "+bmi+" and you are normal");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("d","You are normal");
            editor.commit();
        }
        if(bmivalue>=25 && bmivalue<30)  {
            tvData.setText("Your bmi is "+bmi+" and you are overweight");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("d","You are overweight");
            editor.commit();
        }
        if(bmivalue>=30)  {
            tvData.setText("Your bmi is "+bmi+" and you are obese");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("d","You are obese");
            editor.commit();
        }

        if(bmivalue<18.5) {
            tvData1.setTextColor(Color.parseColor("#ff0000"));
            tvData1.setText("below 18.5 is underweight");
        }
        else
        {
            tvData1.setText("below 18.5 is underweight");
        }
        if(bmivalue>=18.5 && bmivalue<25) {
            tvData2.setTextColor(Color.parseColor("#ff0000"));
            tvData2.setText("between 18.5 to 25 is Normal");
        }
        else
        {
            tvData2.setText("between 18.5 to 25 is Normal");
        }
        if(bmivalue>=25 && bmivalue<30) {
            tvData3.setTextColor(Color.parseColor("#ff0000"));
            tvData3.setText("between 25 to 30 is overweight");
        }
        else
        {
            tvData3.setText("between 25 to 30 is overweight");
        }
        if(bmivalue>30) {
            tvData4.setTextColor(Color.parseColor("#ff0000"));
            tvData4.setText("greater than 30 is obese");
        }
        else
        {
            tvData4.setText("greater than 30 is obese");
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name="Name:"+sp.getString("n","");
                String age="Age:"+sp.getString("a","");
                String phone="Phone:"+sp.getString("p","");
                String gender="Gender:"+sp.getString("g","");
                String bmi="BMI:"+sp.getString("bmi","");
                String msg=sp.getString("d","");

                Intent i=new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"+""));
                i.putExtra(Intent.EXTRA_TEXT,name+"\n"+age+"\n"+phone+"\n"+gender+"\n"+bmi+"\n"+msg);
                startActivity(i);


            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            Date current=Calendar.getInstance().getTime();

            SharedPreferences.Editor editor=sp.edit();
            editor.putString("time",current.toString());
            editor.commit();



                db.add(sp.getString("time"," "),sp.getString("bmi"," "));


            Intent i=new Intent(ThirdActivity.this,FourthActivity.class);
            startActivity(i);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
