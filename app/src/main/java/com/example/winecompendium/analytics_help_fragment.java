package com.example.winecompendium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link analytics_help_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class analytics_help_fragment extends androidx.fragment.app.DialogFragment {


    public analytics_help_fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static analytics_help_fragment newInstance(String title) {
        analytics_help_fragment fragment = new analytics_help_fragment();
        Bundle args = new Bundle();
        args.putString("Wine Description",title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analytics_help_fragment, container, false);
    }
}