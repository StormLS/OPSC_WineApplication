package com.example.winecompendium;

import android.os.Bundle;
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
 * Use the {@link add_wines_add_origin_dialogue#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_wines_add_origin_dialogue extends androidx.fragment.app.DialogFragment {

    private Button btnClose;
    private Button btnDone;
    private EditText txtInput;
    private TextView txtHeading;

    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private String userID;
    private String input;
    private int tot; //the total number of elements within a child


    public add_wines_add_origin_dialogue() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static add_wines_add_origin_dialogue newInstance(String title) {
        add_wines_add_origin_dialogue fragment = new add_wines_add_origin_dialogue();
        Bundle args = new Bundle();
        args.putString("Wine Description",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_wines_add_origin_dialogue, container, false);
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

                //Getting string value
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
                    AddItemToOrigin();
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
    ------------------------------------Close the dialogue box--------------------------------------
     */
    private void CloseDialogueBox() {
        this.dismiss();
    }
    //----------------------------------------------------------------------------------------------

    /*
    -----------------------------Update the heading of the dialogue box-----------------------------
     */
    private void SetHeading() {
        String heading = getString(R.string.add_cat,"Origin");
        txtHeading.setText(heading);
    }
    //----------------------------------------------------------------------------------------------


    //------------------Implementation for if user wants to add a new origin------------------------
    private void AddItemToOrigin(){
        tot = 0;
        input = txtInput.getText().toString();

        DatabaseReference refOrigin = database.getInstance().getReference("Users").child(userID).child("Categories").child("Origin");

        Set<String> OriginList = new HashSet<>();
        Map<String, Object> OriginItem = new HashMap<>();


        refOrigin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item: snapshot.getChildren()) {
                    //Retrieve all the Bottle Type items and populate to list string
                    OriginList.add(item.getValue().toString());

                    //Retrieve the total number of items in the list
                    tot = OriginList.size();

                    //Populate map
                    OriginItem.put(String.valueOf(tot), item.getValue());
                    OriginItem.put(String.valueOf(tot+1),input);
                }
                refOrigin.setValue(OriginItem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Toast.makeText(getContext(),"Origin successfully added.",Toast.LENGTH_SHORT).show();

    }
    //----------------------------------------------------------------------------------------------

}