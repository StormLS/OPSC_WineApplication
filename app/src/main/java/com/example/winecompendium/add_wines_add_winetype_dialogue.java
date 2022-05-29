package com.example.winecompendium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Use the {@link add_wines_add_winetype_dialogue#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_wines_add_winetype_dialogue extends androidx.fragment.app.DialogFragment {

    private Button btnClose;
    private Button btnDone;
    private TextView DialogueHeading;
    private EditText txtInput;
    private EditText txtSubtype;

    private addwines_fragment addWines = new addwines_fragment();

    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference UsersRef = database.getReference("Users");

    private String userID;
    private String input;
    private String subtype;
    private int tot; //the total number of elements within a child.


    public add_wines_add_winetype_dialogue() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static add_wines_add_winetype_dialogue newInstance(String title) {
        add_wines_add_winetype_dialogue fragment = new add_wines_add_winetype_dialogue();
        Bundle args = new Bundle();
        args.putString("Wine Description",title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_wines_add_winetype_dialogue, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        DialogueHeading = getView().findViewById(R.id.txtHeading);
        btnClose = getView().findViewById(R.id.btnClose);
        btnDone = getView().findViewById(R.id.btnDone);
        txtInput = getView().findViewById(R.id.txtAddCat);
        txtSubtype = getView().findViewById(R.id.txtAddSubtype);
        userID = fUser.getUid().toString();



        //Set the heading of the dialogue
        DialogueHeading.setText("Add " + addWines.ReturnHeading());

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Getting string values
                input = txtInput.getText().toString();
                subtype = txtSubtype.getText().toString();

                //Check if both fields have been entered
                if (input.equals(" ") || input.equals("") || input.equals(null))
                {
                    if (subtype.equals(" ") || subtype.equals("") || subtype.equals(null))
                    {
                        //If one or neither are entered
                        Toast.makeText(getContext(), "Please make sure both fields have been entered.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else //If both fields are entered
                {
                    AddItemToWineType();
                    AddItemToSubType();
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



    // ------------------Implementation for if user wants to add a new wine type--------------------
    private void AddItemToWineType(){

        tot = 0;

        DatabaseReference refWineType = database.getInstance().getReference("Users").child(userID).child("Categories").child("WineType");

        Set<String> WineTypeList = new HashSet<>();
        Map<String, Object> WineTypeItem = new HashMap<>();


        refWineType.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item: snapshot.getChildren()) {
                    //Retrieve all the Wine Type items and populate to list string
                    WineTypeList.add(item.getValue().toString());

                    //Retrieve the total number of items in the list
                    tot = WineTypeList.size();

                    //Populate map
                    WineTypeItem.put(String.valueOf(tot), item.getValue());
                    WineTypeItem.put(String.valueOf(tot+1),input);
                }
                //Refresh the fragment
                //TODO: ??/

                //Update the child by overwriting previous Wine types with wine types + newly added wine type
                refWineType.setValue(WineTypeItem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    //----------------------------------------------------------------------------------------------

    // ------------------------Implementation for user adding new subtype --------------------------
    private void AddItemToSubType() {

        DatabaseReference refSubType = database.getInstance().getReference("Users").child(userID).child("Categories").child("SubType").child(input);

        Map<String, Object> SubTypeItem = new HashMap<>();
        SubTypeItem.put(String.valueOf(1),subtype);

        //Update the child
        refSubType.updateChildren(SubTypeItem);
        Toast.makeText(getContext(),"Wine type and Subtype successfully added.",Toast.LENGTH_SHORT).show();

    }
    //----------------------------------------------------------------------------------------------

    /*
   Close the dialogue box
    */
    private void CloseDialogueBox() {
        this.dismiss();
    }
}