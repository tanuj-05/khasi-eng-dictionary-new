package com.example.android.justtrying;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity{


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);


        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


//public class MainActivity extends AppCompatActivity implements WordListAdapter.OnWordListener {
//
//
//    private static final String TAG = "MainActivity";
//    private static final String KEY_TITLE = "english";
//    private static final String KEY_DESCRIPTION = "khasi";
//
//
//
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private CollectionReference translations = db.collection("translations");
//    private DocumentReference noteRef = db.collection("translations").document("word1");
//
//
//    private TextView textViewData;
//    private EditText editTextView;
//    private RecyclerView viewDataBase;
//    private WordListAdapter wordListAdapter;
//    private List<Words> wordsList;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        wordsList = new ArrayList<>();
//        wordListAdapter = new WordListAdapter(wordsList, this);
//
//
//
//        textViewData = findViewById(R.id.text_data);
//        editTextView = findViewById(R.id.word_to_search);
//        Button b = findViewById(R.id.b1);
//        viewDataBase = (RecyclerView) findViewById(R.id.recycleList);
//
//        viewDataBase.setHasFixedSize(true);
//        viewDataBase.setLayoutManager(new LinearLayoutManager(this));
//        viewDataBase.setAdapter(wordListAdapter);
//
//
//
//
//
//
//        db.collection("translations").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                if(e != null) {
//                    Log.d(TAG, "Error: " + e.getMessage());
//                }
//
//                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()) {
//                    if(doc.getType()==DocumentChange.Type.ADDED) {
//                        Words words = doc.getDocument().toObject(Words.class);
//                        wordsList.add(words);
//
//                        wordListAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//
//        });
//
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                load(v);
//            }
//        });
//    }
//    public void load(View v){
//        //String k;
//
//        String wordToCheck = editTextView.getText().toString();
//
//        translations.whereEqualTo("khasi",wordToCheck)
//                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                int count = 0;
//                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                    String khasi = documentSnapshot.getString(KEY_DESCRIPTION);
//                    String english = documentSnapshot.getString(KEY_TITLE);
//                    count++;
//                    textViewData.setText("Khasi:" + khasi + "\nEnglish: " + english);
//                }
//                //count == 0 will signify that the word we're searching fr isn't present in the database
//                if(count == 0){
//                    Toast.makeText(MainActivity.this, "No such word in database", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
//                Log.d(TAG,e.toString());
//            }
//        });
//    }
//
//    @Override
//    public void onWordClick(int position, String name) {
//
//
//
//        Intent intent = new Intent(this, WordFound.class);
//        intent.putExtra("word", name);
//        startActivity(intent);
//
//        Log.d(TAG, "onWordClick: " + name );
//
//
//    }
//}
