package com.example.winecompendium;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link setDesc_dialogue#newInstance} factory method to
 * create an instance of this fragment.
 */
public class setDesc_dialogue extends androidx.fragment.app.DialogFragment {

    private Button btnClose;

    public setDesc_dialogue() {
        // Required empty public constructor
    }

    public static setDesc_dialogue newInstance(String title) {
        setDesc_dialogue fragment = new setDesc_dialogue();
        Bundle args = new Bundle();
        args.putString("Wine Description",title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_description_dialogue, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        TextView desc = (TextView) view.findViewById(R.id.txtDesc);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("desc", "Enter description");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        desc.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        btnClose = getView().findViewById(R.id.btnCloseDialogue);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseDialogueBox();
            }
        });

    }

    /*
    Close the dialogue box
     */
    private void CloseDialogueBox() {
        dismiss();
    }
}