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
    private FirebaseFirestore db = FirebaseFirestore.getInstance();//Firebase database reference
    private CollectionReference khasiToEnglish = db.collection("translations");//reference to collection "translations"
    private CollectionReference englishToKhasi = db.collection("englishToKhasi");//reference to collection "englishToKhasi"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //starting a background thread to load data from 'translations' collection
        LoadData loadData = new LoadData();
        loadData.start();
        //Starting a background thread to load data from 'englishToKhasi' collection
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
                    //on successfully connecting to the database collection we enter here
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        //coverting each document to an object
                        KhasiToEnglish khasiToEnglish = documentSnapshot.toObject(KhasiToEnglish.class);
                        //and extracting required info from the object
                        String khasi = khasiToEnglish.getKhasi();
                        String english = khasiToEnglish.getEnglish();
                        //adding the extracted khasi words and english meanings to array lists
                        khasiWords.add(khasi);
                        englishMeanings.add(english);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //If there is any failure in connecting to the collection , we display a toast message
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
                    //The same thing here as the previous
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        KhasiToEnglish khasiToEnglish = documentSnapshot.toObject(KhasiToEnglish.class);
                        String english = khasiToEnglish.getEnglish();
                        String khasi = khasiToEnglish.getKhasi();
                        englishWords.add(english);
                        khasiMeanings.add(khasi);
                    }
                    //When execution reaches here, it means all the data has been loaded
                    //We thus call function loadingDone to launch Main Activity
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
            //Log messages for debugging purposes
            Log.i(TAG, "first item in khasiWords is:" + khasiWords.get(0));
            Log.i(TAG, "first item in englishMeanings is:" + englishMeanings.get(0));
            Log.i(TAG, "first item in englishWords is:" + englishWords.get(0));
            Log.i(TAG, "first item in khasiMeanings is:" + khasiMeanings.get(0));
            //Creating an intent to launch MainActivity
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            //putting the array lists populated in this activity into the intent as extras
            i.putStringArrayListExtra("khasi_words", khasiWords);
            i.putStringArrayListExtra("english_meanings", englishMeanings);
            i.putStringArrayListExtra("english_words", englishWords);
            i.putStringArrayListExtra("khasi_meanings", khasiMeanings);
            Log.i(TAG, "Size of khasiWords " + khasiWords.size());
            Log.i(TAG, "Size of englishWords" + englishWords.size());
            //starting main activity
            startActivity(i);
            //killing this activity
            finish();
        }
    }
}
