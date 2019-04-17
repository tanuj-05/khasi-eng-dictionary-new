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
    String khasiWord;
    private TextView textViewWord;
    private TextView textViewMeaning;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference khasiToEnglish = db.collection("translations");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_meaning);
        Intent i = getIntent();
        khasiWord = i.getStringExtra("khasi_word");
        textViewWord = findViewById(R.id.text_view_word);
        textViewWord.setText(khasiWord);
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
    }
}
