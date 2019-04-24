package com.example.android.justtrying;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
    ArrayList<String> englishMeanings = new ArrayList<>();
    ArrayList<String> englishWords = new ArrayList<>();
    ArrayList<String> khasiMeanings = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference khasiToEnglish = db.collection("translations");
    private CollectionReference englishToKhasi = db.collection("englishToKhasi");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LoadData loadData = new LoadData();
        loadData.start();
        loadSecondCollection loadSecondCollection = new loadSecondCollection();
        loadSecondCollection.start();
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
                        englishMeanings.add(english);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SplashActivity.this, "Error while loading data from 'translations' collection", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class loadSecondCollection extends Thread {
        @Override
        public void run() {
            super.run();
            englishToKhasi.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        KhasiToEnglish khasiToEnglish = documentSnapshot.toObject(KhasiToEnglish.class);
                        String english = khasiToEnglish.getEnglish();
                        String khasi = khasiToEnglish.getKhasi();
                        englishWords.add(english);
                        khasiMeanings.add(khasi);
                    }
                   loadingDone();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SplashActivity.this, "Error while loading data from 'englishToKhasi' collection", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void loadingDone() {
//            Log.i(TAG, "first item in khasiWords is:" + khasiWords.get(0));
//            Log.i(TAG, "first item in englishMeanings is:" + englishMeanings.get(0));
//            Log.i(TAG, "first item in englishWords is:" + englishWords.get(0));
//            Log.i(TAG, "first item in khasiMeanings is:" + khasiMeanings.get(0));
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            i.putStringArrayListExtra("khasi_words", khasiWords);
            i.putStringArrayListExtra("english_meanings", englishMeanings);
            i.putStringArrayListExtra("english_words", englishWords);
            i.putStringArrayListExtra("khasi_meanings", khasiMeanings);
            Log.i(TAG, "Size of khasiWords " + khasiWords.size());
            Log.i(TAG, "Size of englishWords" + englishWords.size());
            startActivity(i);
            finish();
        }
    }
}
