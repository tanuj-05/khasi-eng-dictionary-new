package com.example.android.justtrying;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchWord extends AppCompatActivity {
    ArrayList<String> khasiWords = new ArrayList<>();
    ArrayList<String> englishWords = new ArrayList<>();
    private TextView prompt;
    private RecyclerView mRecyclerView;
    private KhasiWordsAdapter mAdapter;
   // private RecyclerView.LayoutManager mLayoutManager;
   private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        Intent i = getIntent();
        khasiWords = i.getStringArrayListExtra("khasi_words");
        englishWords = i.getStringArrayListExtra("english_words");
        prompt = findViewById(R.id.text_view_prompt);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new KhasiWordsAdapter(khasiWords);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new KhasiWordsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String a = khasiWords.get(position);
                Intent intent = new Intent(SearchWord.this, DisplayMeaning.class);
                intent.putExtra("khasi_word",a);
                startActivity(intent);
            }
        });
        mRecyclerView.setVisibility(View.GONE);
        //Adding dividers between recyclerview items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.expandActionView();
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        EditText searchBox = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchBox.requestFocus();//To make edit_text have focus on activity startup with keyboard open
        searchBox.setTextColor(getResources().getColor(R.color.colorWord));
        searchBox.setHintTextColor(getResources().getColor(R.color.colorWord));
        searchBox.setBackgroundResource(R.drawable.rounded_edittext);
        searchView.setIconifiedByDefault(false);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint(getResources().getString(R.string.query_hint));
        int pixel = dpToPx(-22);
        searchView.setPadding(pixel, 0, 0, 0);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String checkString = s.replaceAll("\\s+", "");
                if (checkString.length() != 0) {
                    prompt.setVisibility(View.GONE);
                    mAdapter.getFilter().filter(s);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
                if (checkString.length() == 0) {
                    prompt.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                }

                return false;
            }
        });
        return true;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
