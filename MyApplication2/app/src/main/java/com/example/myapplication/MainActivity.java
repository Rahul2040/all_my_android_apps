package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    /*ip[0] =String.valueOf(task.getResult().getValue());
                    task.get
                    Log.e("firebase", ip[0]);*/
                    DataSnapshot ds=task.getResult();
                    String ip=ds.child("ip address").getValue().toString();
                    String date= ds.child("date and time").getValue().toString();
                    TextView tv= findViewById(R.id.textView);
                    tv.setText("ip address: "+ip+" last updated on "+date);
                    try

                    {
                        Log.e("ip","ip is :"+ip);
                        Log.e("connection","connect init!!");
                        Class.forName("com.mysql.jdbc.Driver");
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        Connection connection = DriverManager.getConnection("jdbc:mysql://"+ip+":3306/testdb?user=myuser&password=mypass");
                        Log.e("connection","connect successs!!");
                        Statement statement = connection.createStatement();

                        ResultSet resultSet = statement.executeQuery("SELECT * FROM table1");
                        String records="";
                        while(resultSet.next()) {

                            records += resultSet.getString(1) + " " + resultSet.getString(2) + "\n";

                        }
                        TextView tv1=findViewById(R.id.textView2);
                        tv1.setText(records);


                    }

                    catch(Exception e)

                    {
                        Log.e("sdjs", String.valueOf(e));



                    }






                }
            }
        });




    }
}