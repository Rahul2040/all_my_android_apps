package com.example.firestoretrail;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1= findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,cashEntry.class));
            }
        });
        Button b2= findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,dataEditing.class));
            }
        });
        Button b3= findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,stageDetails.class));
            }
        });
        Button b4=findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,smctocusid.class));
            }
        });




    }
    public String checkcusid(String cusid){
        cusid=cusid.toUpperCase();
        char[] arr= cusid.toCharArray();
        if(Character.isLetter(arr[0])){
            cusid="-"+cusid;
            char[] arr1= cusid.toCharArray();

            char temp=arr1[0];

            arr1[0]=arr1[1];
            arr1[1]=temp;
            String r= String.valueOf(arr1);
            return r;

        }
        return cusid;
    }
}
