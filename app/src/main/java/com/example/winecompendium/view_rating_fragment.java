package com.example.winecompendium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link view_rating_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class view_rating_fragment extends androidx.fragment.app.DialogFragment {

    private Button btnClose;


    // TODO: Rename and change types and number of parameters
    public static view_rating_fragment newInstance(String title) {
        view_rating_fragment fragment = new view_rating_fragment();
        Bundle args = new Bundle();
        args.putString("Subtype",title);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_rating_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        btnClose = getView().findViewById(R.id.btnCloseRating);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseDialogueBox();
            }
        });
    }

    private void CloseDialogueBox() {
        this.dismiss();
    }
}