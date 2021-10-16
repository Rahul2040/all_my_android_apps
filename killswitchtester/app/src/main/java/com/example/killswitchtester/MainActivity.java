package com.example.killswitchtester;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1= findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* InputStream is= null;
                String str1="";
                try {
                    is = getAssets().open("file.txt");
                    int size=is.available();
                    byte[] buffer=new byte[size];
                    is.read(buffer);
                    is.close();
                    str1=new String(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                //  Log.e("text",str1);

                FirebaseFirestore db= FirebaseFirestore.getInstance();
                final CollectionReference noteref= db.collection("List");
              //  final String finalStr = str1;
                noteref.orderBy("cusid").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int tot=queryDocumentSnapshots.size();
                        cusinfo cus[]=new cusinfo[tot];
                        String docids[]=new String[tot];
                        Log.e("e",tot+" objects has been created");
                        int i1=0;
                        String data="";
                        for (QueryDocumentSnapshot i : queryDocumentSnapshots) {
                            cus[i1] = i.toObject(cusinfo.class);
                            docids[i1]=i.getId();
                            data+=cus[i1].fetchcusinfo(1);
                            i1++;
                        }
                        Intent intent= new Intent(Intent.ACTION_SEND);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");
                        String currentDateandTime =  "TOTAL  list before updating for the month generated on "+sdf.format(new Date());
                        intent.putExtra(Intent.EXTRA_SUBJECT,currentDateandTime);
                        intent.putExtra(Intent.EXTRA_TEXT,data);
                        intent.setType("message/rfc822");
                        startActivity(Intent.createChooser(intent,"choose an email client"));
                        AlertDialog.Builder altdial= new AlertDialog.Builder(MainActivity.this);
                        altdial.setMessage("there are "+tot+" no of documents which are going to be affected. Do you want to proceed?").setCancelable(false)
                                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(),
                                                "proceeding for the update process!!",
                                                Toast.LENGTH_SHORT).show();

                                        for(int i=0;i<tot;i++){
                                            int cash=0;
                                            int ob=cus[i].bal;
                                            int total=ob+cus[i].addon+cus[i].pack;
                                            int bal=total;
                                            String current_id=cus[i].cusid;
                                            int current_i=i;
                                            db.collection("List").document(docids[i])
                                                    .update(
                                                            "ob", ob,
                                                            "bal", bal,
                                                            "total", total,
                                                            "cash", cash
                                                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.e("e", current_id +" successfully updated!!!!");
                                                    if(current_i==tot-1) {
                                                        Toast.makeText(getApplicationContext(),
                                                                "All the documents were successfully!! updated",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                    else if(current_i%50==0&& current_i!=0){
                                                        Toast.makeText(getApplicationContext(),
                                                                "successfully updated "+current_i+" records !!!!",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                    else if(current_i==15){
                                                        Toast.makeText(getApplicationContext(),
                                                                "Updation process has begun and running successfully!!!",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }

                                    }
                                }).setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert=altdial.create();
                        alert.setTitle("CONFIRMATION NEEDED TO PROCEED!!!");
                        alert.show();
                       /* cusinfo cus[]=new cusinfo[tot];
                        String docids[]=new String[tot];
                        Log.e("e",tot+" objects has been created");
                        int i1=0;
                        for (QueryDocumentSnapshot i : queryDocumentSnapshots) {
                            cus[i1] = i.toObject(cusinfo.class);
                            docids[i1]=i.getId();
                            i1++;
                        }
                        for(int i=0;i<tot;i++){
                           int cash=0;
                            int ob=cus[i].bal;
                            int total=ob+cus[i].addon+cus[i].pack;
                            int bal=total;
                            String current_id=cus[i].cusid;
                            db.collection("List").document(docids[i])
                                    .update(
                                            "ob", ob,
                                            "bal", bal,
                                            "total", total,
                                            "cash", cash
                                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.e("e", current_id +" successfully updated!!!!");
                                }
                            });
                        }*/


                       /* int count2=queryDocumentSnapshots.size();
                        if(count2>0){
                            Log.e("e","First Delete all the record and do this!!!! there are "+count2+"no of records!!!! you idiot!!!");
                            Toast.makeText(getApplicationContext(),
                                    "Database is not empty!!!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.e("e","database is empty !! goodjob!!");
                            int count = 0;

                            for (String line : finalStr.split("\n")) {
                                count++;
                                String[] s = line.split(",");
                                cusinfo c1 = new cusinfo(s[0], s[1], s[2], Integer.parseInt(s[3]), Integer.parseInt(s[4]), Integer.parseInt(s[5]), Integer.parseInt(s[7]), s[9]);
                                // Log.e("R",c1.fetchcusinfo(1));
                               // c1 = altercus(c1);
                                Log.e("D", c1.fetchcusinfo(1));
                                final cusinfo finalC = c1;
                                noteref.document(Integer.toString(count)).set(c1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.e("e", finalC.cusid + " successfully written!");
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e("e", "Error writing document", e);
                                            }
                                        });

                            }
                        }*/

                    }
                });



            }
        });
    }
    cusinfo altercus(cusinfo c1){
        c1.cash=0;
        c1.ob=c1.bal;
        c1.total=c1.ob+c1.addon+c1.pack;
        c1.bal=c1.total-c1.cash;
        return c1;
    }
}