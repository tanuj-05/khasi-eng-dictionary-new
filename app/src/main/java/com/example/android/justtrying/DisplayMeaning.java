package com.example.android.justtrying;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DisplayMeaning extends AppCompatActivity implements View.OnClickListener {
    String englishMeaning;
    String khasiMeaning;
    String word;
    //String khasiWord;
    private int stateFavButton = 0;
    private static int checkButStatus = 0;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;
    private TextView textViewWord;
    private TextView textViewWord2;
    private TextView textViewMeaning;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference khasiToEnglish = db.collection("translations");
    private CollectionReference englishToKhasi = db.collection("englishToKhasi");
    private int switchCheck;
    private ImageButton mBut;

    private FloatingActionButton mFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Action Bar settings
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        setContentView(R.layout.activity_display_meaning);

        //Share Action provider
        mFAB = findViewById(R.id.floatingActionButton);

        //Fav button
        mBut =  findViewById(R.id.favButton);

        Intent i = getIntent();
        word = i.getStringExtra("word");
        switchCheck = i.getIntExtra("check_switch",0);
        textViewWord = findViewById(R.id.text_view_word);
        textViewWord.setText(word);
        textViewWord2 = findViewById(R.id.text_view_word2);
        textViewWord2.setText(word);
        textViewMeaning = findViewById(R.id.text_view_meaning);
        db.disableNetwork()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Do offline things
                        // ...
                        if (switchCheck == 0){
                            khasiToEnglish.whereEqualTo("khasi", word)
                                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                        KhasiToEnglish khasiToEnglish = queryDocumentSnapshot.toObject(KhasiToEnglish.class);
                                        englishMeaning = khasiToEnglish.getEnglish();
                                        textViewMeaning.setText(englishMeaning);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DisplayMeaning.this, "Error while fetching English meaning!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                                    englishToKhasi.whereEqualTo("english",word)
                                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                                KhasiToEnglish khasiToEnglish = documentSnapshot.toObject(KhasiToEnglish.class);
                                                khasiMeaning = khasiToEnglish.getKhasi();
                                                textViewMeaning.setText(khasiMeaning);
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(DisplayMeaning.this, "Error while fetching Khasi Meaning!!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                    }
                });


        mBut.setOnClickListener(this);

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String word = textViewWord.getText().toString();
                String meaning = textViewMeaning.getText().toString();
                intent.putExtra(Intent.EXTRA_TEXT, "Word: " + word + "\n" + "Meaning: " + meaning + "\n\n\n" + "Yours truly\nKhasi Dictioanry");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Khasi Dictionary");

                startActivity(Intent.createChooser(intent, "Share the meaning with..."));
            }
        });

        checkButStatus = checkDb(textViewWord.getText().toString());

        if(checkButStatus == 1)
        {
            mBut.setColorFilter(getResources().getColor(R.color.favSelected));
            stateFavButton = 1;
        }

        else{
            mBut.setColorFilter(getResources().getColor(R.color.colorText));
        }

    }


    @Override
    public void onClick(View view) {
        String word = textViewWord.getText().toString();

        if (stateFavButton == 0 ) {
            stateFavButton = 1;
            mBut.setColorFilter(getResources().getColor(R.color.favSelected));
            Toast.makeText(DisplayMeaning.this, "Word added to Favourites!", Toast.LENGTH_SHORT).show();
            FileHelperFavourite.writeData(word, this);
        } else {
            stateFavButton = 0;
            mBut.setColorFilter(getResources().getColor(R.color.colorText));
            //mBut.setEnabled(false);
            //FileHelperFavourite.flushEverything(this);
            Toast.makeText(DisplayMeaning.this, "Word Removed from Favourites!", Toast.LENGTH_SHORT).show();
            FileHelperFavourite.removeData(word,this);
        }
    }


    public int checkDb(String word) {

        int check = FileHelperFavourite.check(word,this);

        if(check == 1) {
            return 1;
        }
        else
            return 0;

    }



}
