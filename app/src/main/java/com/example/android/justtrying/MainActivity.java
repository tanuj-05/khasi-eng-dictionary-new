package com.example.android.justtrying;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    int randomNum, checkNum;
    String khasi, currentWord;
    private int checkSwitch;
    ArrayList<String> khasiWords = new ArrayList<>();
    ArrayList<String> englishMeanings = new ArrayList<>();
    ArrayList<String> englishWords = new ArrayList<>();
    ArrayList<String> khasiMeanings = new ArrayList<>();
    String exampleWords[] = {"yoyo", "huihui", "woohoo"};

    private TextView wordOfTheDay;
    private ImageView wordSearch;

    //Declaration of Navigation Drawer variables
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    //Declaration of Database variables
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference khasiToEnglish = db.collection("kToE");

    private AdView mAdView;
    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aSwitch = findViewById(R.id.switch1);
        Intent i = getIntent();
        khasiWords = i.getStringArrayListExtra("khasi_words");
        englishMeanings = i.getStringArrayListExtra("english_meanings");
        englishWords = i.getStringArrayListExtra("english_words");
        khasiMeanings = i.getStringArrayListExtra("khasi_meanings");
        wordOfTheDay = findViewById(R.id.textView5);
        randomNum = 0;
        checkNum = 0;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Random r = new Random();
                randomNum = r.nextInt(3);
                if (randomNum == checkNum) {
                    if (randomNum == 2) {
                        randomNum -= 1;
                    } else {
                        randomNum += 1;
                    }

                }
                checkNum = randomNum;
                currentWord = exampleWords[randomNum];

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI
                        wordOfTheDay.setText(currentWord);
                    }
                });

            }
        };
        timer.schedule(timerTask, 0, 10000);
        wordSearch = findViewById(R.id.imageView2);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checkSwitch = 1;
                }else {
                    checkSwitch = 0;
                }
            }
        });
        wordSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchWord.class);
                intent.putStringArrayListExtra("khasi_words", khasiWords);
                intent.putStringArrayListExtra("english_meanings", englishMeanings);
                intent.putStringArrayListExtra("english_words", englishWords);
                intent.putStringArrayListExtra("khasi_meanings", khasiMeanings);
                intent.putExtra("switch_check",checkSwitch);
                Log.i(TAG, "Size of Khasi words in MainActivity: " + khasiWords.size());
                Log.i(TAG, "Size of englishMeanings in MainActivity: " + englishMeanings.size());
                Log.i(TAG, "First Khasi word in MainActivity " + khasiWords.get(0));
                Log.i(TAG, "First English Meaning in Main Activity" + englishMeanings.get(0));
                Log.i(TAG, "Last Khasi word in MainActivity " + khasiWords.get(khasiWords.size() - 1));
                Log.i(TAG, "Last English Word in Main Activity" + englishMeanings.get(englishMeanings.size() - 1));
                Log.i(TAG, "Size of English words in MainActivity: " + englishWords.size());
                Log.i(TAG, "Size of khasiMeanings in MainActivity: " + khasiMeanings.size());
                Log.i(TAG, "First English word in MainActivity " + englishWords.get(0));
                Log.i(TAG, "First Khasi Meaning in Main Activity" + khasiMeanings.get(0));
                Log.i(TAG, "Last English word in MainActivity " + englishWords.get(englishWords.size() - 1));
                Log.i(TAG, "Last Khasi Meaning in Main Activity" + khasiMeanings.get(khasiMeanings.size() - 1));
                startActivity(intent);
            }
        });


        //used to set action bar preferances
        ActionBar actionBar = getSupportActionBar();

        // used to make a blend between action bar and image view below to appear as one UI element
        actionBar.setElevation(0);


        // Defining ad view and loading it into the ad view
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        //Used to create an OnClick Listener for the menu options in the navigation drawer.
        navigationView.setNavigationItemSelectedListener(this);

        //Used to open and close the navigation drawer with use from the action bar
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        //to make sure that Action Bar toogle happens (listener) when the drawer is activated/in focus
        mDrawerLayout.addDrawerListener(mToggle);

        //to ensure the state of the drawer is synced with the drawer layout (mDrawerLayout)
        mToggle.syncState();

        //used to highlight the first item in the drawer (default case)
        navigationView.setCheckedItem(R.id.id1);

        //used to display the menu icon on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    /*

    Function that listens for events in navigation items (menu items) and passes the requests to the UI Thread.

    The use of Handlers help for background threads to communicate with the main thread (UI thread).

    A delay of 200 mili-seconds to ensure a lag free close of the Nav drawer and then the message/runnable is executed.
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.id1:

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, FavActivity.class);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent);
                    }
                },200);

                break;

            case R.id.id2:

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intentAlphabets = new Intent(MainActivity.this, Language.class);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intentAlphabets);
                    }
                },200);
                break;

            case R.id.id3:

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intentRecents = new Intent(MainActivity.this, RecentsActivity.class);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intentRecents);
                    }
                },200);

                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    /*

    Function used to ensure that when the back button is pressed the Main Activity layout is seen.

    Makes sure that the app does not close using the back button.

     */

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    /*

    Function used to handle menu item presses by returning true (By default this method is called on the menu)

    if item is not selected, we call the superclass implementation of onOptionsItemSelected.
    (so that the compiler knows that there is no user define menu item)

     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}