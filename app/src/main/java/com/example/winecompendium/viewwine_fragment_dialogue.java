package com.example.winecompendium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link viewwine_fragment_dialogue#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewwine_fragment_dialogue extends androidx.fragment.app.DialogFragment {

    public viewwine_fragment_dialogue() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static viewwine_fragment_dialogue newInstance(String title) {
        viewwine_fragment_dialogue fragment = new viewwine_fragment_dialogue();
        Bundle args = new Bundle();
        args.putString("Subtype",title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_viewwine_dialogue, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

    }
}