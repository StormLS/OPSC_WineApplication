package com.example.winecompendium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add_wines_category_dialogue#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_wines_category_dialogue extends androidx.fragment.app.DialogFragment {

    private Button btnClose;
    private TextView DialogueHeading;
    private EditText txtInput;
    private addwines_fragment addWines = new addwines_fragment();
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference UsersRef = database.getReference("Users");
    private String userID;
    private String input;
    private int tot; //the total number of elements within a child

    public add_wines_category_dialogue() {
        // Required empty public constructor
    }

    public static add_wines_category_dialogue newInstance(String title) {
        add_wines_category_dialogue fragment = new add_wines_category_dialogue();
        Bundle args = new Bundle();
        args.putString("Wine Description",title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_wines_category_dialogue, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        TextView desc = (TextView) view.findViewById(R.id.txtAddCat);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("add", "Add Item");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        desc.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        DialogueHeading = getView().findViewById(R.id.txtHeading);
        txtInput = getView().findViewById(R.id.txtAddCat);
        userID = fUser.getUid().toString();
        btnClose = getView().findViewById(R.id.btnClose);


        //Set the heading of the dialogue
        DialogueHeading.setText("Add " + addWines.ReturnHeading());

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtInput != null) {
                    AddItemToCategory();
                }
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

    private void AddItemToCategory() {
        String catType = addWines.ReturnHeading();

        switch (catType) {
            case "Origin" : AddItemToOrigin();
            break;
            case "Bottle Type": AddItemToBottleType();
            break;
        }
    }


    //------------------Implementation for if user wants to add a new origin------------------------
    private void AddItemToOrigin(){

    }
    //----------------------------------------------------------------------------------------------

    //----------------Implementation for if user wants to add a new bottle type---------------------
    private void AddItemToBottleType(){

    }
    //----------------------------------------------------------------------------------------------
}