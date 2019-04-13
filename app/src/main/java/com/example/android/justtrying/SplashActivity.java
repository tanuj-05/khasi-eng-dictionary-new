package com.example.android.justtrying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.*;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    ArrayList<String> khasiWords = new ArrayList<>();
    ArrayList<String> englishWords = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference khasiToEnglish = db.collection("translations");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LoadData loadData = new LoadData();
        loadData.start();

    }

    class LoadData extends Thread {
        @Override
        public void run() {
            super.run();
            khasiToEnglish.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        KhasiToEnglish khasiToEnglish = documentSnapshot.toObject(KhasiToEnglish.class);
                        String khasi = khasiToEnglish.getKhasi();
                        String english = khasiToEnglish.getEnglish();
                        khasiWords.add(khasi);
                        englishWords.add(english);
                    }
                    loadingDone();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SplashActivity.this, "Error in addOnFailure", Toast.LENGTH_SHORT).show();
                }
            });
        }
        private void loadingDone(){
            Log.i(TAG, "first item in the arraylist is:" + khasiWords.get(0));
            Log.i(TAG, "first item in the arraylist is:" + englishWords.get(0));
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            i.putStringArrayListExtra("khasi_words",khasiWords);
            i.putStringArrayListExtra("english_words",englishWords);
            Log.i(TAG, "onSuccess: " + khasiWords.size());
            startActivity(i);
            finish();
        }
    }
}
