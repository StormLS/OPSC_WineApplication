package com.example.winecompendium;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add_wines_bottletype_dialogue#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_wines_bottletype_dialogue extends androidx.fragment.app.DialogFragment {

    private Button btnClose;
    private Button btnDone;
    private EditText txtInput;
    private TextView txtHeading;

    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private String userID;
    private String input;
    private int tot; //the total number of elements within a child


    public add_wines_bottletype_dialogue() {
        // Required empty public constructor
    }

    public static add_wines_bottletype_dialogue newInstance(String title) {
        add_wines_bottletype_dialogue fragment = new add_wines_bottletype_dialogue();
        Bundle args = new Bundle();
        args.putString("Wine Description",title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_wines_bottletype_dialogue, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        TextView desc = (TextView) view.findViewById(R.id.txtAddOrigin_Cat);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("add", "Add Item");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        desc.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        txtInput = getView().findViewById(R.id.txtAddOrigin_Cat);
        userID = fUser.getUid().toString();
        btnClose = getView().findViewById(R.id.btnClose);
        btnDone = getView().findViewById(R.id.btnDone);
        txtHeading = getView().findViewById(R.id.txtHeading);

        input = txtInput.getText().toString();

        SetHeading();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Getting string values
                input = txtInput.getText().toString();

                //Check if field has been entered
                if (input.equals(" ") || input.equals("") || input.equals(null))
                {
                    //If the field has not been entered
                    Toast.makeText(getContext(),"Please make sure the field has been entered.",Toast.LENGTH_SHORT).show();
                    return;
                }
                else //If the field has been entered
                {
                    AddItemToBottleType();
                    CloseDialogueBox();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseDialogueBox();
            }
        });

    }

    /*
    ----------------------------------------Close the dialogue box----------------------------------
     */
    private void CloseDialogueBox() {
        this.dismiss();
    }
    //----------------------------------------------------------------------------------------------


    /*
    -----------------------------Update the heading of the dialogue box-----------------------------
     */
    private void SetHeading() {
        String heading = getString(R.string.add_cat,"Bottle Type");
        txtHeading.setText(heading);
    }
    //----------------------------------------------------------------------------------------------


    //------------------------Implementation for adding a new bottle type---------------------------
    private void AddItemToBottleType(){
        tot = 0;
        input = txtInput.getText().toString();

        DatabaseReference refBottleType = database.getInstance().getReference("Users").child(userID).child("Categories").child("BottleType");

        Set<String> BottleTypeList = new HashSet<>();
        Map<String, Object> BottleTypeItem = new HashMap<>();


        refBottleType.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item: snapshot.getChildren()) {
                    //Retrieve all the bottle type items and populate to list string
                    BottleTypeList.add(item.getValue().toString());

                    //Retrieve the total number of items in the list
                    tot = BottleTypeList.size();

                    //Populate map
                    BottleTypeItem.put(String.valueOf(tot), item.getValue());
                    BottleTypeItem.put(String.valueOf(tot+1),input);
                }
                //Update the child by overwriting previous bottle types with bottle types + newly added bottle type
                refBottleType.setValue(BottleTypeItem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ADDBT","ErrorAddingBottleType");
            }
        });

        Toast.makeText(getContext(),"Bottle Type successfully added.",Toast.LENGTH_SHORT).show();

    }
    //----------------------------------------------------------------------------------------------
}