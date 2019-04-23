package com.example.android.justtrying;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FavActivity extends AppCompatActivity {



    private ArrayList<String> items;

    private ArrayAdapter<String> adapter;

    private ListView listView;

    private String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        listView = findViewById(R.id.listforFav);

        items = FileHelperFavourite.readDataFav(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);

        listView.setAdapter(adapter);

    }
}
