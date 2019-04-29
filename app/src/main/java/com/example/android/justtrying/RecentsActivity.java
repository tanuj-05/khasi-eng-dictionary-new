package com.example.android.justtrying;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/*
    Class to populate the Activity for Recents

 */

public class RecentsActivity extends AppCompatActivity {


    private ArrayList<String> items;

    private ArrayAdapter<String> adapter;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents);


        listView = findViewById(R.id.listforRecents);

        //we store the file contents in the array list
        items = FileHelperFavourite.readDataRecents(this);

        // we pass the list of item to the array adapter

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);

        // we display data to inside the listview
        listView.setAdapter(adapter);


    }
}
