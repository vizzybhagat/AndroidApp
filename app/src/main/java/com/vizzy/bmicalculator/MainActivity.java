package com.vizzy.bmicalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    TextView tvPersonalDetails;
    EditText etName,etPhone,etAge;
    RadioGroup rgGender;
    RadioButton rbMale,rbFemale;
    Button btnRegister;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPersonalDetails=(TextView)findViewById(R.id.tvPersonalDetails);
        etName=(EditText)findViewById(R.id.etName);
        etAge=(EditText)findViewById(R.id.etAge);
        etPhone=(EditText)findViewById(R.id.etPhone);
        rgGender=(RadioGroup)findViewById(R.id.rgGender);
        btnRegister=(Button)findViewById(R.id.btnRegister);

        sp=getSharedPreferences("myp1",MODE_PRIVATE);
        String n=sp.getString("n","");

        if(!n.equals(""))
        {
            Intent i=new Intent(MainActivity.this,SecondActivity.class);
            startActivity(i);
            finish();
        }
        else {

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String name=etName.getText().toString();
                    String age = etAge.getText().toString();
                    String phone = etPhone.getText().toString();


                    if (name.length() == 0) {
                        etName.setError("Name is empty");
                        etName.requestFocus();
                        return;
                    }

                    if (age.length() == 0) {
                        etAge.setError("Age is empty");
                        etAge.requestFocus();
                        return;
                    }
                    if (phone.length() != 10) {
                        etPhone.setError("Invalid phone number");
                        etPhone.requestFocus();
                        return;
                    }

                    int index=rgGender.getCheckedRadioButtonId();
                    RadioButton rb=(RadioButton)findViewById(index);

                    String gender= rb.getText().toString();


                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("n", name);
                    editor.putString("a", age);
                    editor.putString("p", phone);
                    editor.putString("g", gender);
                    editor.commit();

                    Toast.makeText(MainActivity.this, "data saved successfully", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(i);
                    finish();

                }
            });
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
