package com.example.android.justtrying;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class KhasiWordsAdapter extends RecyclerView.Adapter<KhasiWordsAdapter.KhasiWordViewHolder> {
    private ArrayList<String> mKhasiWords;
    public static class KhasiWordViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public KhasiWordViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_view);
        }
    }
    public KhasiWordsAdapter(ArrayList<String> khasiWords){
        mKhasiWords = khasiWords;
    }

    @NonNull
    @Override
    public KhasiWordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.individual_word,viewGroup,false);
        KhasiWordViewHolder khasiWordViewHolder = new KhasiWordViewHolder(v);
        return khasiWordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KhasiWordViewHolder khasiWordViewHolder, int i) {
        String currenKhasiWord = mKhasiWords.get(i);
        khasiWordViewHolder.mTextView.setText(currenKhasiWord);
    }

    @Override
    public int getItemCount() {
        return mKhasiWords.size();
    }
}
