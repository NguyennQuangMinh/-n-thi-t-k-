package com.example.project_cuoiky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBarden;
    SeekBar seekBarquat;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference chuoidulieu  = database.getReference();
    DatabaseReference nhietdo = FirebaseDatabase.getInstance().getReference("esp_guilen/nhietdo");
    DatabaseReference doam = FirebaseDatabase.getInstance().getReference("esp_guilen/doam");
    Button btb1, ttb1;
    Button btb2, ttb2;
    Button bc2tb, tc2tb;
    TextView txt1;
    String den_str, quat_str, r1_str, r2_str;
    private Float docnhietdo;
    private int docdoam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBarden=(SeekBar) findViewById(R.id.sb1);
        seekBarquat=(SeekBar) findViewById(R.id.sb2);
        txt1=findViewById(R.id.txt1);
        btb1=(Button)findViewById(R.id.btb1);
        ttb1=(Button)findViewById(R.id.ttb1);
        btb2=(Button)findViewById(R.id.btb2);
        ttb2=(Button)findViewById(R.id.ttb2);
        bc2tb=(Button)findViewById(R.id.bc2tb);
        tc2tb=(Button)findViewById(R.id.tc2tb);
        chuoidulieu.child("android_guilen/chuoidulieu").setValue("0#0#0#0");//den # quat # relay1 # relay2

        seekBarden.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int luugiatriden, boolean b) {
                den_str=String.valueOf(luugiatriden);
                chuoidulieu.child("android_guilen/chuoidulieu").setValue(den_str+"#"+quat_str+"#"+r1_str+"#"+r2_str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarquat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int luugiatriquat, boolean b) {
                quat_str=String.valueOf(luugiatriquat);
                chuoidulieu.child("android_guilen/chuoidulieu").setValue(den_str+"#"+quat_str+"#"+r1_str+"#"+r2_str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        nhietdo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                docnhietdo = dataSnapshot.getValue(Float.class);
                txt1.setText("Nhiệt độ: "+ docnhietdo+"\u2103"+"   "+"Độ ẩm: "+docdoam+"%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        doam.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                docdoam = dataSnapshot.getValue(int.class);
                txt1.setText("Nhiệt độ: "+ docnhietdo+"\u2103"+"   "+"Độ ẩm: "+docdoam+"%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1_str="1";
                chuoidulieu.child("android_guilen/chuoidulieu").setValue(den_str+"#"+quat_str+"#"+r1_str+"#"+r2_str);
            }
        });
        ttb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1_str="0";
                chuoidulieu.child("android_guilen/chuoidulieu").setValue(den_str+"#"+quat_str+"#"+r1_str+"#"+r2_str);
            }
        });
        btb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r2_str="1";
                chuoidulieu.child("android_guilen/chuoidulieu").setValue(den_str+"#"+quat_str+"#"+r1_str+"#"+r2_str);
            }
        });
        ttb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r2_str="0";
                chuoidulieu.child("android_guilen/chuoidulieu").setValue(den_str+"#"+quat_str+"#"+r1_str+"#"+r2_str);
            }
        });
        bc2tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1_str="1";
                r2_str="1";
                chuoidulieu.child("android_guilen/chuoidulieu").setValue(den_str+"#"+quat_str+"#"+r1_str+"#"+r2_str);
            }
        });
        tc2tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1_str="0";
                r2_str="0";
                chuoidulieu.child("android_guilen/chuoidulieu").setValue(den_str+"#"+quat_str+"#"+r1_str+"#"+r2_str);
            }
        });
    }
}