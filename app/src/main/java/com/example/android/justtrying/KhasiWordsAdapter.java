package com.example.android.justtrying;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class KhasiWordsAdapter extends RecyclerView.Adapter<KhasiWordsAdapter.KhasiWordViewHolder> implements Filterable {
    private ArrayList<String> mKhasiWords;
    private ArrayList<String> khasiWordsFull;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    private Filter khasiWordsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(khasiWordsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String item : khasiWordsFull) {
                    if (item.toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mKhasiWords.clear();
            mKhasiWords.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public KhasiWordsAdapter(ArrayList<String> khasiWords) {
        mKhasiWords = khasiWords;
        khasiWordsFull = new ArrayList<>(mKhasiWords);
    }

    @NonNull
    @Override
    public KhasiWordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.individual_word, viewGroup, false);
        KhasiWordViewHolder khasiWordViewHolder = new KhasiWordViewHolder(v, mListener);
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

    @Override
    public Filter getFilter() {
        return khasiWordsFilter;
    }

    public static class KhasiWordViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public KhasiWordViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
