package com.vizzy.bmicalculator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class FourthActivity extends AppCompatActivity {

    TextView tvData;
    DatabaseHandler db;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        tvData=(TextView)findViewById(R.id.tvData);
        sp=getSharedPreferences("myp1",MODE_PRIVATE);
        db=new DatabaseHandler(this);

        ArrayList<String> s=db.getdata();

        tvData.setText("History details:");

        if(s.size()==0)
        {
            tvData.setText("No records to show");
        }
        else
        {
            StringBuffer sb=new StringBuffer();

            for(int i=0;i<s.size();i++)
            {
                sb.append(s.get(i));
                sb.append("\n");
            }

            tvData.setText(sb);
        }

    }
}
