package com.example.android.justtrying;


import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView wordSearch;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private static final String TAG = "MainActivity";

    private AdView mAdView;
    ArrayList<String> khasiWords = new ArrayList<>();
    ArrayList<String> englishWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordSearch = findViewById(R.id.imageView2);
        Intent i = getIntent();
        khasiWords = i.getStringArrayListExtra("khasi_words");
        englishWords = i.getStringArrayListExtra("english_words");
        wordSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchWord.class);
                intent.putStringArrayListExtra("khasi_words",khasiWords);
                intent.putStringArrayListExtra("english_words",englishWords);
                Log.i(TAG, "onSuccess in MainActivity: " + khasiWords.size());
                Log.i(TAG, "First Khasi word in MainActivity " + khasiWords.get(0));
                Log.i(TAG, "First English Word in Main Activity" + englishWords.get(0));
                Log.i(TAG, "Last Khasi word in MainActivity " + khasiWords.get(khasiWords.size() - 1));
                Log.i(TAG, "Last English Word in Main Activity" + englishWords.get(englishWords.size() - 1));
                startActivity(intent);

            }
        });
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);



        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        navigationView.setCheckedItem(R.id.id1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



        switch(menuItem.getItemId()) {
            case R.id.id1:

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, FavActivity.class);
                        startActivity(intent);
                    }
                },200);

                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.id2:

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intentAlphabets = new Intent(MainActivity.this, Language.class);
                        startActivity(intentAlphabets);
                    }
                },200);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.id3:
//                Intent intentRecents = new Intent(MainActivity.this, Language.class);
//                startActivity(intentRecents);
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true  ;
    }

    @Override
    public void onBackPressed() {

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}




//import android.support.v7.widget.LinearLayoutManager;
//        import android.support.v7.widget.RecyclerView;
//        import android.util.Log;
//import android.view.View;
//
//        import android.content.Intent;
//        import android.widget.Button;
//        import android.widget.EditText;
//
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//
//        import com.google.android.gms.tasks.OnFailureListener;
//        import com.google.android.gms.tasks.OnSuccessListener;
//
//        import com.google.firebase.firestore.CollectionReference;
//        import com.google.firebase.firestore.DocumentChange;
//        import com.google.firebase.firestore.DocumentReference;
//        import com.google.firebase.firestore.EventListener;
//        import com.google.firebase.firestore.FirebaseFirestore;
//        import com.google.firebase.firestore.FirebaseFirestoreException;
//        import com.google.firebase.firestore.QueryDocumentSnapshot;
//        import com.google.firebase.firestore.QuerySnapshot;
//
//        import java.util.ArrayList;
//        import java.util.List;
//
//        import javax.annotation.Nullable;
//import android.support.annotation.NonNull;
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
