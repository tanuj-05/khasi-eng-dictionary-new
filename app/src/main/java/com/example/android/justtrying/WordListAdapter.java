package com.example.android.justtrying;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {


    private OnWordListener mOnWordListener;
    public List<Words> wordsList;

    public WordListAdapter(List<Words> wordsList, OnWordListener onWordListener){
        this.wordsList = wordsList;
        this.mOnWordListener = onWordListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layout,viewGroup,false);
        return new ViewHolder(view , mOnWordListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.wordLists.setText(wordsList.get(i).getKhasi());
    }

    @Override
    public int getItemCount() {
        return wordsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View wView;

        public TextView wordLists;
        OnWordListener onWordListener;

        public ViewHolder(@NonNull View itemView, OnWordListener onWordListener) {
            super(itemView);

            this.onWordListener = onWordListener;
            wView = itemView;

            wordLists = (TextView) itemView.findViewById(R.id.wordList);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TextView justTry = (TextView) itemView.findViewById(R.id.wordList);
            String sharedFact = justTry.getText().toString();
            onWordListener.onWordClick(getAdapterPosition(),sharedFact);
        }
    }

    public interface OnWordListener{
        void onWordClick(int position,String word);
    }

}
