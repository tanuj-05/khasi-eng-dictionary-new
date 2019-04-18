package com.example.android.justtrying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class DisplayMeaning extends AppCompatActivity {
    String englishMeaning;
    String khasiWord;
    private TextView textViewWord;
    private TextView textViewWord2;
    private TextView textViewMeaning;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference khasiToEnglish = db.collection("translations");
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
        khasiWord = i.getStringExtra("khasi_word");
        textViewWord = findViewById(R.id.text_view_word);
        textViewWord2 = findViewById(R.id.text_view_word2);
        textViewWord.setText(khasiWord);
        textViewWord2.setText(khasiWord);
        textViewMeaning = findViewById(R.id.text_view_meaning);
        db.disableNetwork()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Do offline things
                        // ...
                        khasiToEnglish.whereEqualTo("khasi", khasiWord)
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
                                Toast.makeText(DisplayMeaning.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String word = textViewWord.getText().toString();
                String meaning = textViewMeaning.getText().toString();
                intent.putExtra(Intent.EXTRA_TEXT,"Word: "+word + "\n" + "Meaning: " + meaning + "\n\n\n" + "Yours truly\nKhasi Dictioanry");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Khasi Dictionary");

                startActivity(Intent.createChooser(intent,"Share the meaning with..."));
            }
        });


        mBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

    }





}
