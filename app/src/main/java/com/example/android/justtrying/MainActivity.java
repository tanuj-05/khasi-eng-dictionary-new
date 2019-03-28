package com.example.android.justtrying;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String KEY_TITLE = "english";
    private static final String KEY_DESCRIPTION = "khasi";
    //FirebaseApp.initializeApp(this);
    private FirebaseFirestore db = FirebaseFirestore.getInstance();;
    private TextView textViewData;
    private EditText editTextView;
    private CollectionReference translations = db.collection("translations");
    private DocumentReference noteRef = db.collection("translations").document("word1");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewData = findViewById(R.id.text_data);
        editTextView = findViewById(R.id.word_to_search);
        Button b = findViewById(R.id.b1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(v);
            }
        });
    }
    public void load(View v){
        //String k;

        String wordToCheck = editTextView.getText().toString();
        translations.whereEqualTo("khasi",wordToCheck)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                int count = 0;
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String khasi = documentSnapshot.getString(KEY_DESCRIPTION);
                    String english = documentSnapshot.getString(KEY_TITLE);
                    count++;
                    textViewData.setText("Khasi:" + khasi + "\nEnglish: " + english);
                }
                //count == 0 will signify that the word we're searching fr isn't present in the database
                if(count == 0){
                    Toast.makeText(MainActivity.this, "No such word in database", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG,e.toString());
            }
        });
    }
}
