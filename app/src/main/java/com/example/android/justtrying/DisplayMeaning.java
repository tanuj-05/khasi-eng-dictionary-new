package com.example.android.justtrying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    String khasiMeaning;
    String word;
    private TextView textViewWord;
    private TextView textViewMeaning;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference khasiToEnglish = db.collection("translations");
    private CollectionReference englishToKhasi = db.collection("englishToKhasi");
    private int switchCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_meaning);
        Intent i = getIntent();
        word = i.getStringExtra("word");
        switchCheck = i.getIntExtra("check_switch",0);
        textViewWord = findViewById(R.id.text_view_word);
        textViewWord.setText(word);
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
    }
}
