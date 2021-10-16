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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class smctocusid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smctocusid);
        Button b2=findViewById(R.id.button17);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button b1=findViewById(R.id.button16);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.editTextTextPersonName);
                String smc = et.getText().toString().toUpperCase().replaceAll(" ","");
                TextView tv = findViewById(R.id.textView5);
                tv.setText("");
                if (!smc.equals("")) {


                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    final CollectionReference noteref = db.collection("List");
                    noteref.whereEqualTo("smc", smc).
                    get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    cusinfo cus = new cusinfo();
                                    int count = 0;
                                    for (QueryDocumentSnapshot i : queryDocumentSnapshots) {
                                        count++;
                                        cus = i.toObject(cusinfo.class);
                                        TextView tv = findViewById(R.id.textView5);
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
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter CUSID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}