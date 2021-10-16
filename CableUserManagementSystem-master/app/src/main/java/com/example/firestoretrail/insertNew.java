package com.example.firestoretrail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.sql.SQLException;
import java.sql.Statement;

public class insertNew extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new);
        Button b = (Button) findViewById(R.id.button13);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button b1 = (Button) findViewById(R.id.button12);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText et=(EditText) findViewById(R.id.editText11) ;
                EditText et2=(EditText) findViewById(R.id.editText12) ;
                EditText et3=(EditText) findViewById(R.id.editText13) ;
                EditText et4=(EditText) findViewById(R.id.editText14) ;
                EditText et5=(EditText) findViewById(R.id.editText15) ;
                EditText et6=(EditText) findViewById(R.id.editText16) ;
                EditText et7=(EditText) findViewById(R.id.editText17) ;
                String cid= et.getText().toString();
                String name= et2.getText().toString();
                String smc= et3.getText().toString();
                String ob= et4.getText().toString();
                String pack= et5.getText().toString();
                String add= et6.getText().toString();
                String acc= et7.getText().toString();
                if(!cid.equals("")&&!smc.equals("")&&!ob.equals("")&&!pack.equals("")&&!add.equals("")&&!acc.equals("")){
                    cid=checkcusid(cid);
                    if(name.equals("")) name="!";
                    final cusinfo cus= new cusinfo(cid,name,smc.toUpperCase().replaceAll("E","Y").replaceAll(" ",""),Integer.parseInt(ob),Integer.parseInt(pack),Integer.parseInt(add),acc);
                    final FirebaseFirestore db= FirebaseFirestore.getInstance();
                    final CollectionReference noteref= db.collection("List");
                    noteref.whereEqualTo("cusid", cid).
                            get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                    int count = queryDocumentSnapshots.size();
                                    if (count > 0) {
                                        Toast.makeText(getApplicationContext(),
                                                "CUSID ALREADY EXITS",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        FirebaseFirestore db1= FirebaseFirestore.getInstance();
                                        CollectionReference noteref1= db1.collection("List");
                                        noteref1.add(cus).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(getApplicationContext(),"Successfully Inserted!!!",Toast.LENGTH_SHORT).show();
                                                EditText et=(EditText) findViewById(R.id.editText11) ;
                                                EditText et2=(EditText) findViewById(R.id.editText12) ;
                                                EditText et3=(EditText) findViewById(R.id.editText13) ;
                                                EditText et4=(EditText) findViewById(R.id.editText14) ;
                                                EditText et5=(EditText) findViewById(R.id.editText15) ;
                                                EditText et6=(EditText) findViewById(R.id.editText16) ;
                                                EditText et7=(EditText) findViewById(R.id.editText17) ;
                                                et.setText("");
                                                et2.setText("");
                                                et3.setText("");
                                                et4.setText("");
                                                et5.setText("");
                                                et6.setText("");
                                                et7.setText("");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(),"FAILED TO INSERT!!!!",Toast.LENGTH_SHORT).show();
                                            }
                                        });
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
                    Toast t1= Toast.makeText(getApplicationContext(), "Invalid Details", Toast.LENGTH_SHORT);
                    t1.show();
                }




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
