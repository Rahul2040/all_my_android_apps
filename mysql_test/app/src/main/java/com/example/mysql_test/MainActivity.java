package com.example.mysql_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try

        {
            // Log.e("ip","ip is :"+ip);
            Log.e("connection","connect init!!");
            Class.forName("com.mysql.jdbc.Driver");
            Log.e("connection","connect successs!!");
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.40:3306/testdb","myuser","mypass");
            Log.e("connection","connect successs!!");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM table1");
            String records="";
            while(resultSet.next()) {

                records += resultSet.getString(1) + " " + resultSet.getString(2) + "\n";

            }
            TextView tv1=findViewById(R.id.testView);
            tv1.setText(records);


        }

        catch(Exception e)

        {
            Log.e("sdjs", String.valueOf(e));



        }
    }
}