package com.example.firestoretrail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class cashEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_entry);

        Button b1= findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.editText);
                String cusid = et.getText().toString();
                TextView tv = findViewById(R.id.textView);
                tv.setText("");
                if(!cusid.equals("")){
                cusid=checkcusid(cusid);

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    final CollectionReference noteref = db.collection("List");
                    noteref.whereEqualTo("cusid", cusid).
                            get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    cusinfo cus = new cusinfo();
                                    int count = 0;
                                    for (QueryDocumentSnapshot i : queryDocumentSnapshots) {
                                        count++;
                                        cus = i.toObject(cusinfo.class);
                                        TextView tv = findViewById(R.id.textView);
                                        tv.setText(cus.fetchcusinfo(0));
                                        break;

                                    }
                                    if (count == 0) {
                                        Toast.makeText(getApplicationContext(),
                                                "No customer found!!!",
                                                Toast.LENGTH_SHORT).show();
                                    } else Toast.makeText(getApplicationContext(),
                                            "Customer found!!",
                                            Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext(),"Please Enter CUSID",Toast.LENGTH_SHORT).show();
                }
            }

        });
        Button b2= findViewById(R.id.button5);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.editText),et1=findViewById(R.id.editText2);
                String cusid = et.getText().toString(),cash=et1.getText().toString();
                TextView tv = findViewById(R.id.textView);
                tv.setText("");
                if(!cusid.equals("") || ! cash.equals("")){
                    cusid=checkcusid(cusid);
                    final int cash1=Integer.parseInt(cash);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    final String[] r = new String[1];
                    r[0] = "";
                    final int[] flag = new int[1];
                    final CollectionReference noteref = db.collection("List");
                    noteref.whereEqualTo("cusid", cusid).
                            get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    cusinfo cus = new cusinfo();
                                    int count = 0;
                                    for (QueryDocumentSnapshot i : queryDocumentSnapshots) {
                                        count++;
                                        cus = i.toObject(cusinfo.class);
                                        String docid=i.getId();
                                        DocumentReference docref= noteref.document(docid);
                                        cus.cash+=cash1;
                                        cus.bal=cus.total-cus.cash;
                                        docref.set(cus);
                                        TextView tv = findViewById(R.id.textView);
                                        tv.setText("");
                                        EditText et1=findViewById(R.id.editText2);
                                        et1.setText("");
                                        Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_SHORT).show();
                                        char acc=cus.acc.charAt(0);
                                        Log.e("acc","customer account is "+acc);
                                        String smc1= (cus.smc);
                                        smc1="Pay "+smc1.replaceAll(" ","").replaceAll("Y","E").toUpperCase();
                                        ClipboardManager clip= (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clip1= ClipData.newPlainText("",smc1);
                                        assert clip != null;
                                        clip.setPrimaryClip(clip1);
                                        if(acc=='R'){
                                           // Toast.makeText(getApplicationContext(),"customer account is "+cus.acc,Toast.LENGTH_SHORT).show();
                                            Uri uri= Uri.parse("smsto:919444309707");
                                            Intent i1= new Intent(Intent.ACTION_SENDTO,uri);
                                            i1.setPackage("com.whatsapp");

                                            startActivity(i1);
                                        }
                                        else if(acc=='M'){
                                            Uri uri= Uri.parse("smsto:919498397129");
                                            Intent i1= new Intent(Intent.ACTION_SENDTO,uri);
                                            i1.setPackage("com.whatsapp");

                                            startActivity(i1);

                                        }
                                        else{
                                            Toast t3 = Toast.makeText(getApplicationContext(), "Recharge Not possible but details updated", Toast.LENGTH_SHORT);
                                            t3.show();
                                        }
                                        break;

                                    }
                                    if (count == 0) {
                                        Toast.makeText(getApplicationContext(),
                                                "No customer found!!!",
                                                Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(),"Please Enter CUSID",Toast.LENGTH_SHORT).show();
                }
            }


        });
        Button b3=findViewById(R.id.button6);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public String checkcusid(String cusid){
        cusid=cusid.toUpperCase().replaceAll("-","");
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
