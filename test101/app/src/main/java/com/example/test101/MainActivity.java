package com.example.test101;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b=findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.e("aajhdjahdad","inside onclick!!!");
                final ProgressBar pb= findViewById(R.id.progressBar);
                for(int i=0;i<=1e5;i++){
                    int j=i%100;
                    pb.setProgress(j);
                    Log.e("aajhdjahdad","i value: "+i);
                }

            }
        });
    }
}