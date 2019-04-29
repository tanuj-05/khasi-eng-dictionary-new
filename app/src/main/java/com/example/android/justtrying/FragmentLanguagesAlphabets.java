package com.example.android.justtrying;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

import static java.lang.Thread.sleep;

public class FragmentLanguagesAlphabets extends Fragment {

    private TextToSpeech textToSpeech;
    private GridLayout gridLayout;

    //use of an inflater to take the layout file and pass an object to the calling fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_language_alphabets, container, false);




        return rootView;
    }

    // to set up text to speech

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textToSpeech = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("UK"));
                }
            }
        });

        gridLayout = (GridLayout)getView().findViewById(R.id.mainGrid);
        setSingleEvent(gridLayout);


    }

    //used to relieve the resources taken up by TTS
    @Override
    public void onDestroy() {
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }


    private void setSingleEvent(GridLayout gridLayout) {

        //loop through all the children of the grid layout
        for(int i = 0; i<gridLayout.getChildCount();i++){
            final CardView cardView=(CardView)gridLayout.getChildAt(i);

            final int finalI= i;

            //listener to handle child click events and fire up TTS
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch(finalI) {

                        case 0:
                            textToSpeech.speak("ah", TextToSpeech.QUEUE_ADD, null);
                            break;

                        case 1:
                            textToSpeech.speak("bee", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 2:
                            textToSpeech.speak("kay", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 3:
                            textToSpeech.speak("dee", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 4:
                            textToSpeech.speak("A", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 5:
                            textToSpeech.speak("ek", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 6:
                            textToSpeech.speak("eng", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 7:
                            textToSpeech.speak("esh", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 8:
                            textToSpeech.speak("E", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 9:
                            textToSpeech.speak("yee", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 10:
                            textToSpeech.speak("J", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 11:
                            textToSpeech.speak("L", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 12:
                            textToSpeech.speak("M", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 13:
                            textToSpeech.speak("N", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 14:
                            textToSpeech.speak("en-g", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 15:
                            textToSpeech.speak("O", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 16:
                            textToSpeech.speak("P", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 17:
                            textToSpeech.speak("R", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 18:
                            textToSpeech.speak("S", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 19:
                            textToSpeech.speak("T", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 20:
                            textToSpeech.speak("ooh", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 21:
                            textToSpeech.speak("W", TextToSpeech.QUEUE_ADD, null);
                            break;
                        case 22:
                            textToSpeech.speak("Y", TextToSpeech.QUEUE_ADD, null);
                            break;

                        default:
                            Toast.makeText(getContext().getApplicationContext(),"wrong!",Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

}
