package com.example.firestoretrail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Modify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        final Spinner sp1= findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.column,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);
        Button b1 = (Button) findViewById(R.id.button11);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
        Button b2 = (Button) findViewById(R.id.button9);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.editText9);
                String cusid = et.getText().toString();
                TextView tv = findViewById(R.id.textView11);
                tv.setText("");
                if (!cusid.equals("")) {
                    cusid = checkcusid(cusid);

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
                                        TextView tv = findViewById(R.id.textView11);
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
        Button b3=findViewById(R.id.button10);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.editText9);
                String cusid = et.getText().toString();
                EditText et1=findViewById(R.id.editText10);
                String newval=et1.getText().toString();
                if (!cusid.equals("")|| !newval.equals("")) {
                    cusid = checkcusid(cusid);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    final CollectionReference noteref = db.collection("List");
                    noteref.whereEqualTo("cusid", cusid).
                            get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    cusinfo cus = new cusinfo();
                                    String docid="";
                                    int count=0;
                                    for (QueryDocumentSnapshot i : queryDocumentSnapshots) {
                                        count++;
                                        cus = i.toObject(cusinfo.class);
                                        docid=i.getId();
                                        Log.e("e2",docid);
                                        break;
                                    }
                                    if(count>0){
                                    DocumentReference docref= noteref.document(docid);
                                    EditText et=findViewById(R.id.editText10);
                                    String newval=et.getText().toString();
                                    Spinner sp1=findViewById(R.id.spinner1);
                                    String field=sp1.getSelectedItem().toString();
                                    cus=altercus(field,cus,newval);
                                    docref.set(cus).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(),"Successfully Updated!!!!",Toast.LENGTH_SHORT).show();
                                            TextView tv = findViewById(R.id.textView11);
                                            tv.setText("");
                                            EditText et=findViewById(R.id.editText10);
                                            et.setText("");


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),
                                                    "There is some issue",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"No Customer Found!!!!",Toast.LENGTH_SHORT).show();
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
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Proper values", Toast.LENGTH_SHORT).show();
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
    public cusinfo altercus(String Field, cusinfo cus,String newval){
        if(Field.equals("name")) { cus.name=newval; return cus; }
        else if(Field.equals("smc")) { cus.smc=newval.toUpperCase().replaceAll(" ","").replaceAll("E","Y"); return cus; }
        else if(Field.equals("ob")) { cus.ob=Integer.parseInt(newval) ;  cus.total= cus.ob+ cus.addon+ cus.pack; cus.bal=cus.total-cus.cash; return cus; }
        else if(Field.equals("pack")) { cus.pack=Integer.parseInt(newval) ;  cus.total= cus.ob+ cus.addon+ cus.pack; cus.bal=cus.total-cus.cash; return cus; }
        else if(Field.equals("addon")) { cus.addon=Integer.parseInt(newval) ;  cus.total= cus.ob+ cus.addon+ cus.pack; cus.bal=cus.total-cus.cash; return cus; }
        else  { cus.acc=newval; return cus; }
    }

}