package com.example.a160220;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    int REQUEST_LOCATION_PERMISSION=0;
    FusedLocationProviderClient fusedLocationClient;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.button);
        textView=findViewById(R.id.textView);
       fusedLocationClient= LocationServices.getFusedLocationProviderClient(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprawdzLokalizacje();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            sprawdzLokalizacje();



        }
        else {
            Toast.makeText(this, "Nie wyrażono zgody na dalsze działanie aplikacji", Toast.LENGTH_SHORT).show();
        }
    }

private void sprawdzLokalizacje(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{  Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
        else {
            Log.d("lokalizacja", "wyrażona zgoda na lokalizację ");
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    double szerokoscGeograficzna=location.getLatitude();
                    double dlugoscGeograficzna=location.getLongitude();
                    String opis="Długość geograficzna: "+dlugoscGeograficzna+" szerokość: "+szerokoscGeograficzna+" czas"+location.getTime();

                     textView.setText(opis);
                }
            }
        });
        }

}
}
