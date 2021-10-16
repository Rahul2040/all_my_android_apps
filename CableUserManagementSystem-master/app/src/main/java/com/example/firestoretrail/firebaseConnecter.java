package com.example.firestoretrail;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class firebaseConnecter {

    public boolean addNew(String cusid, String name, String smc, int ob, int pack, int addon, String acc){
        final int[] flag = {-1};
        cusinfo cus =new cusinfo( cusid,  name,  smc,  ob,  pack,  addon, acc);
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        CollectionReference noteref= db.collection("List");
        noteref.add(cus)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                      flag[0] =1;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        flag[0] =0;
                    }
                });
        if(flag[0]==1) return true;
        else return false;
    }
    public boolean update(String cusid, final String field, final String newval){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        final CollectionReference noteref= db.collection("List");
        final String[] docid = new String[1];
        final int[] flag = new int[1];
        noteref.whereEqualTo("cusid",cusid).
                get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        cusinfo cus=new cusinfo();
                        int count=0;
                        for(QueryDocumentSnapshot i:queryDocumentSnapshots ){
                            count++;
                            cus = i.toObject(cusinfo.class);
                            docid[0] =i.getId();
                            break;

                        }
                        if(count==0) { flag[0]=0; return ;}
                        switch(field){
                            case "name": cus.name=newval; break;
                            case "smc": cus.smc=newval; break;
                            case "ob": cus.ob=Integer.parseInt(newval);  cus.total= cus.ob+cus.pack+cus.addon; cus.bal=cus.total-cus.cash; break;
                            case "pack":  cus.pack=Integer.parseInt(newval);  cus.total= cus.ob+cus.pack+cus.addon; cus.bal=cus.total-cus.cash; break;
                            case "addon":  cus.addon=Integer.parseInt(newval);  cus.total= cus.ob+cus.pack+cus.addon; cus.bal=cus.total-cus.cash; break;
                            case "acc": cus.smc=newval; break;
                        }
                        DocumentReference docref= noteref.document(docid[0]);
                        docref.set(cus);
                        flag[0]=1;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        flag[0]=0;
                    }
                });
        Log.e("flagvalue",Integer.toString(flag[0]));
        if(flag[0]==1) return true;
        else return false;
    }

    public String search(String cusid){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        final String[] r = new String[1];
        r[0]="";
        final int[] flag = new int[1];
        final CollectionReference noteref= db.collection("List");
        noteref.whereEqualTo("cusid",cusid).
                get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        cusinfo cus=new cusinfo();
                        int count=0;
                        for(QueryDocumentSnapshot i:queryDocumentSnapshots ){
                            count++;
                            cus = i.toObject(cusinfo.class);
                           r[0]=cus.fetchcusinfo(0);
                            break;

                        }
                        if(count==0) {flag[0]=0;  return;}
                        else flag[0]=1;

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        flag[0]=0;
                    }
                });
        return r[0];



    }
}
