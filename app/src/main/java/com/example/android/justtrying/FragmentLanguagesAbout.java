package com.example.android.justtrying;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentLanguagesAbout extends Fragment {

    //use of an inflater to take the layout file and pass an object to the calling fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_language_about, container, false);

        return rootView;
    }
}
