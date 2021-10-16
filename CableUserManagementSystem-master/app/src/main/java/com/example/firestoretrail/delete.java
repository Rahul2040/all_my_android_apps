package com.example.firestoretrail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class delete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Button b1= findViewById(R.id.button10);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b1=findViewById(R.id.button11);
                b1.setVisibility(View.INVISIBLE);
                EditText et = findViewById(R.id.editText20);
                String cusid = et.getText().toString();
                TextView tv = findViewById(R.id.textView4);
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
                                        TextView tv = findViewById(R.id.textView4);
                                        tv.setText(cus.fetchcusinfo(0));
                                        Button b1=findViewById(R.id.button11);
                                        b1.setVisibility(View.VISIBLE);
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
        Button b2=findViewById(R.id.button11);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.editText20);
                String cusid = et.getText().toString();

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
                                    String docid="";
                                    for (QueryDocumentSnapshot i : queryDocumentSnapshots) {
                                        cus = i.toObject(cusinfo.class);
                                        docid=i.getId();
                                        break;
                                    }
                                    DocumentReference docref=noteref.document(docid);
                                    docref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(),"Successfully Deleted!!!",Toast.LENGTH_SHORT).show();
                                            TextView tv = findViewById(R.id.textView4);
                                            tv.setText("");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),"Some Error has happened!! NOT DELETED!!",Toast.LENGTH_SHORT).show();
                                        }
                                    });
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
        Button b3=findViewById(R.id.button14);
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