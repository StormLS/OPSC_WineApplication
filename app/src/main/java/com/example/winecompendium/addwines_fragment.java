package com.example.winecompendium;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addwines_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addwines_fragment extends Fragment {

    private Button btnSetDesc;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addwines_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addwines_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addwines_fragment newInstance(String param1, String param2) {
        addwines_fragment fragment = new addwines_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setDesc_dialogue df = new setDesc_dialogue();
        df.setCancelable(false); //prevent user from closing dialogue outside the box

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addwines_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        /*
        Implementation for showing the set description dialogue box
         */
        btnSetDesc = getView().findViewById(R.id.btn_set_desc);

        btnSetDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogueBox();
            }
        });
    }

    /*
     Show the set description dialogue box
    */
    private void ShowDialogueBox() {
        FragmentManager fm =  getChildFragmentManager();
        setDesc_dialogue setDescriptionDialogue = setDesc_dialogue.newInstance("Wine Description");
        setDescriptionDialogue.show(fm, "fragment_edit_name");

    }


}