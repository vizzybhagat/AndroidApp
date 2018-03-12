package com.vizzy.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity implements
GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    TextView tvData,tvHeight,tvWeight,tvFeet,tvInch;
    EditText etWeight;
    Spinner spnFeet,spnInch;
    Button btnCalculate,btnViewHistory;
    SharedPreferences sp;
    TextView tvLocation;
    GoogleApiClient gac;
    Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvData=(TextView)findViewById(R.id.tvData);
        tvHeight=(TextView)findViewById(R.id.tvHeight);
        tvWeight=(TextView)findViewById(R.id.tvWeight);
        tvFeet=(TextView)findViewById(R.id.tvFeet);
        tvInch=(TextView)findViewById(R.id.tvInch);
        tvLocation=(TextView)findViewById(R.id.tvLocation);

        etWeight=(EditText)findViewById(R.id.etWeight);

        spnFeet=(Spinner)findViewById(R.id.spnFeet);
        spnInch=(Spinner)findViewById(R.id.spnInch);

        btnCalculate=(Button)findViewById(R.id.btnCalculate);
        btnViewHistory=(Button)findViewById(R.id.btnViewHistory);

        sp=getSharedPreferences("myp1",MODE_PRIVATE);


        final String feet[]={"1","2","3","4","5","6"};
        final String inch[]={"0","1","2","3","4","5","6","7","8","9","10","11"};

        ArrayAdapter feetAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,feet);
        ArrayAdapter inchAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,inch);

        spnFeet.setAdapter(feetAdapter);
        spnInch.setAdapter(inchAdapter);


        Intent i=getIntent();
        String msg=sp.getString("n","");

        tvData.setText(" Welcome "+msg);

        GoogleApiClient.Builder builder=new GoogleApiClient.Builder(this);
        builder.addOnConnectionFailedListener(this);
        builder.addConnectionCallbacks(this);
        builder.addApi(LocationServices.API);

        gac=builder.build();





        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String weight=etWeight.getText().toString();

                if(weight.length()==0)
                {
                    etWeight.setError("Weight is empty");
                    etWeight.requestFocus();
                    return;
                }

                int in=spnFeet.getSelectedItemPosition();
                double f=Double.parseDouble(feet[in]);

                int a=spnInch.getSelectedItemPosition();
                double inc=Double.parseDouble(inch[a]);

                final double meter=((f*12)+inc)*0.0254;


                double wt=Double.parseDouble(weight);

                double bmi=(wt/(meter*meter));

                String bmivalue=String.format("%4.2f",bmi);

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("bmi",bmivalue);
                editor.commit();

                Intent i=new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(i);
            }
        });

        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent i=new Intent(SecondActivity.this,FourthActivity.class);
            startActivity(i);


            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.m1,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.Website)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://"+"www.myfitnesspal.com"));
            startActivity(i);
        }

        if(item.getItemId()==R.id.About)
        {
            Toast.makeText(this, "Developed by Vizzy", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close this application?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id)
            {
                dialog.cancel();
            }
        });


        builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.setTitle("exit");
        alert.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(gac!=null)
            gac.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(gac!=null)
        {
            gac.disconnect();

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        loc=LocationServices.FusedLocationApi.getLastLocation(gac);
        if(loc!=null)
        {
            Geocoder g=new Geocoder(SecondActivity.this, Locale.ENGLISH);
            try {
                List<Address> a=g.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
                Address add=a.get(0);
                String msg=add.getCountryName()+" "+add.getPostalCode()+" "+add.getLocality()+" "+
                        add.getSubLocality()+" "+add.getThoroughfare()+" "+add.getSubThoroughfare()+" "+
                        add.getAdminArea()+" "+add.getSubAdminArea();
                tvLocation.setText("You are at "+msg);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(this, "check gps", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onConnectionSuspended(int i) {

        Toast.makeText(this, "Connection Suspended", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }


}
