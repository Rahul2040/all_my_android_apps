package com.example.firestoretrail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class stageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_details);
        final Spinner sp1= findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.stages,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);
        Button b = (Button) findViewById(R.id.button7);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button b1 = (Button) findViewById(R.id.button6);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Spinner sp1= findViewById(R.id.spinner3);
                String selectedstage=sp1.getSelectedItem().toString();
                if(selectedstage.equals("TOTAL LIST")){
                    Log.e("TotalList","it worked!!");
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    final CollectionReference noteref = db.collection("List");
                    noteref.orderBy("cusid").get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    cusinfo cus = new cusinfo();
                                    String data="CusID,NAME,SMC,OB,PACK,Additional,TOTAL,CASH,BAL,ACC\n";
                                    int count = 0;
                                    for (QueryDocumentSnapshot i : queryDocumentSnapshots) {
                                        count++;
                                        cus = i.toObject(cusinfo.class);
                                        String e=cus.fetchcusinfo(1);
                                        data+=e;
                                        Log.e("TotalList",e);
                                    }
                                    if (count == 0) {
                                        Toast.makeText(getApplicationContext(),
                                                "No customer found!!!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Intent intent= new Intent(Intent.ACTION_SEND);
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");
                                        String currentDateandTime =  sp1.getSelectedItem().toString()+" list generated on "+sdf.format(new Date());
                                        intent.putExtra(Intent.EXTRA_SUBJECT,currentDateandTime);
                                        intent.putExtra(Intent.EXTRA_TEXT,data);
                                        intent.setType("message/rfc822");
                                        startActivity(Intent.createChooser(intent,"choose an email client"));
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),
                                            "There is some issue",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else if(selectedstage.equals("HERTIAGE_VGN")){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    final CollectionReference noteref = db.collection("List");
                    noteref.orderBy("cusid").startAt("A").endAt("Z\uf8ff").get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    cusinfo cus = new cusinfo();
                                    String data="CusID,NAME,SMC,OB,PACK,Additional,TOTAL,CASH,BAL,ACC\n";
                                    int count = 0;
                                    for (QueryDocumentSnapshot i : queryDocumentSnapshots) {
                                        count++;
                                        cus = i.toObject(cusinfo.class);
                                        String e=cus.fetchcusinfo(1);
                                        data+=e;
                                        Log.e("TotalList",e);
                                    }
                                    if (count == 0) {
                                        Toast.makeText(getApplicationContext(),
                                                "No customer found!!!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Intent intent= new Intent(Intent.ACTION_SEND);
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");
                                        String currentDateandTime =  sp1.getSelectedItem().toString()+" list generated on "+sdf.format(new Date());
                                        intent.putExtra(Intent.EXTRA_SUBJECT,currentDateandTime);
                                        intent.putExtra(Intent.EXTRA_TEXT,data);
                                        intent.setType("message/rfc822");
                                        startActivity(Intent.createChooser(intent,"choose an email client"));
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),
                                            "There is some issue",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                else{
                    String num=getStartVal(selectedstage);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    final CollectionReference noteref = db.collection("List");
                    noteref.orderBy("cusid").startAt(num).endAt(num+"\uf8ff").get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    cusinfo cus = new cusinfo();
                                    String data="CusID,NAME,SMC,OB,PACK,Additional,TOTAL,CASH,BAL,ACC\n";
                                    int count = 0;
                                    for (QueryDocumentSnapshot i : queryDocumentSnapshots) {
                                        count++;
                                        cus = i.toObject(cusinfo.class);
                                        String e=cus.fetchcusinfo(1);
                                        data+=e;
                                        Log.e("TotalList",e);
                                    }
                                    if (count == 0) {
                                        Toast.makeText(getApplicationContext(),
                                                "No customer found!!!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Intent intent= new Intent(Intent.ACTION_SEND);
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");
                                        String currentDateandTime =  sp1.getSelectedItem().toString()+" list generated on "+sdf.format(new Date());
                                        intent.putExtra(Intent.EXTRA_SUBJECT,currentDateandTime);
                                        intent.putExtra(Intent.EXTRA_TEXT,data);
                                        intent.setType("message/rfc822");
                                        startActivity(Intent.createChooser(intent,"choose an email client"));
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),
                                            "There is some issue",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }


            }
        });
    }
    public String getStartVal(String a){
        switch(a){
            case "PK": return "1";
            case "KAK": return "2";
            case "SDK": return "3";
            case "ABIRAMI NAGAR": return "4";
            case "AYYAPPAN NAGAR": return "5";
            case "DEVI NAGAR": return "6";
        }
        return "";
    }
}