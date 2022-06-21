package com.example.winecompendium;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add_wines_subtype_category_dialogue#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_wines_subtype_category_dialogue extends androidx.fragment.app.DialogFragment {

    private String getHeading;
    private String userID;
    private String selectedItem;
    private String input;
    private int tot;

    private Button btnDone;
    private Button btnClose;
    private TextView txtHead;
    private TextView txtInput;

    private Spinner spinnerWineType;

    private addwines_fragment addWines = new addwines_fragment();

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef;
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();


    public add_wines_subtype_category_dialogue() {
        // Required empty public constructor
    }

    public static add_wines_subtype_category_dialogue newInstance(String title) {
        add_wines_subtype_category_dialogue fragment = new add_wines_subtype_category_dialogue();
        Bundle args = new Bundle();
        args.putString("Subtype",title);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_wines_subtype_category_dialogue, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        userID = fUser.getUid();
        getHeading = "Wine SubType";

        spinnerWineType = getView().findViewById(R.id.spinWineType);
        txtHead = getView().findViewById(R.id.txtHeading);
        txtInput = getView().findViewById(R.id.txtAddOrigin_Cat);
        btnDone = getView().findViewById(R.id.btnDone);
        btnClose = getView().findViewById(R.id.btnClose);

         SetHeading();
         PopulateSpinner();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get string variables
                selectedItem = spinnerWineType.getSelectedItem().toString();
                input = txtInput.getText().toString();

                //Check if field has been entered
                if (input.equals(null) || input.equals("") || input.equals(" "))
                {
                    //If field has not been entered
                    Toast.makeText(getContext(),"Please make sure the field has been entered.",Toast.LENGTH_LONG).show();
                     txtInput.setFocusable(true);
                     return;
                }
                else //If field has been entered
                {
                    AddItemToSubtype();
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


    //Update the heading of the dialogue box
    private void SetHeading() {
        String heading = getString(R.string.add_cat,getHeading);
        txtHead.setText(heading);
    }

    // --------------------------------- Populate spinner with the wine types ----------------------
    private void PopulateSpinner() {

        dbRef = db.getInstance().getReference("Users").child(userID).child("Categories/WineType");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                final List<String> subtypeList = new ArrayList<String>();

                for(DataSnapshot item : snapshot.getChildren()) {
                    String subtypes = item.getValue(String.class);

                    if (subtypes != null) {
                        subtypeList.add(subtypes);
                    }
                }
                //Populate spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, subtypeList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerWineType.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
        spinnerWineType.setSelection(0);

    }
    //----------------------------------------------------------------------------------------------


    // -----------------Implementation for if user wants to add a new wine subtype-------------------
    private void AddItemToSubtype() {
        input = txtInput.getText().toString();
        tot = 0;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refSubType = database.getInstance().getReference("Users").child(userID).child("Categories").child("SubType").child(selectedItem);

        Set<String> SubTypeList = new HashSet<>();
        Map<String, Object> SubTypeItem = new HashMap<>();

        refSubType.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item: snapshot.getChildren()) {
                    //Retrieve all the Subtype items and populate to list string
                    SubTypeList.add(item.getValue().toString());

                    //Retrieve the total number of items in the list
                    tot = SubTypeList.size();

                    //Populate map
                    SubTypeItem.put(String.valueOf(tot), item.getValue());
                    SubTypeItem.put(String.valueOf(tot+1),input);
                }
                //Update the child by overwriting previous subtypes types with subtypes + newly added subtype
                refSubType.setValue(SubTypeItem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("AddSubtypeTag",error.getMessage());
            }
        });

        Toast.makeText(getContext(),"Subtype successfully added.",Toast.LENGTH_SHORT).show();
    }
    //----------------------------------------------------------------------------------------------


    private void CloseDialogueBox() {
       this.dismiss();
    }


}