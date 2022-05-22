package com.example.winecompendium;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addwines_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addwines_fragment extends Fragment implements AdapterView.OnItemSelectedListener
{

    private Button btnSetDesc;
    private Button browseGallery;

    private Spinner spinner_wineType;
    private Spinner spinner_wineSubtype;
    private Spinner spinner_wineOrigin;
    private Spinner spinner_wineBottleType;

    DatabaseReference dbRef;

    ArrayAdapter<String> adapter_wineType;
    ArrayAdapter<String> adapter_wineSubtype;
    ArrayAdapter<String> adapter_wineOrigin;
    ArrayAdapter<String> adapter_wineBottleType;

    ArrayList<String> spinnerList_WineTypes;
    ArrayList<String> spinnerList_wineSubtype;
    ArrayList<String> spinnerList_wineOrigin;
    ArrayList<String> spinnerList_wineBottleType;



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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        populateAllSpinners();
        /*
        Implementation for showing the set description dialogue box
         */
        btnSetDesc = getView().findViewById(R.id.btn_set_desc);
        browseGallery = getView().findViewById(R.id.browseGallery);

        btnSetDesc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ShowDialogueBox();
            }
        });

        browseGallery.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openFileChooser();
            }
        });
    }

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private ImageView wineImage;

    private void openFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("Image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            mImageUri = data.getData();

            wineImage.setImageURI(mImageUri);
        }
    }

    /*
         Show the set description dialogue box
        */
    private void ShowDialogueBox()
    {
        FragmentManager fm =  getChildFragmentManager();
        setDesc_dialogue setDescriptionDialogue = setDesc_dialogue.newInstance("Wine Description");
        setDescriptionDialogue.show(fm, "fragment_edit_name");

    }

    private void populateAllSpinners()
    {
        //Populates a spinner (WineTypes) from the FireBase DB
        spinner_wineType = getView().findViewById(R.id.wineType);
        dbRef = FirebaseDatabase.getInstance().getReference("Category").child("WineType");
        spinnerList_WineTypes = new ArrayList<>();
        adapter_wineType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerList_WineTypes);
        spinner_wineType.setAdapter(adapter_wineType);
        dbRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot item : snapshot.getChildren())
                {
                    spinnerList_WineTypes.add(item.getValue().toString());
                }
                adapter_wineType.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
        spinner_wineType.setOnItemSelectedListener(this);
        //Populates a spinner (WineTypes) from the FireBase DB

        //Populates a spinner (Subtype) from the FireBase DB
        spinner_wineSubtype = getView().findViewById(R.id.wineSubtype);
        dbRef = FirebaseDatabase.getInstance().getReference("Category").child("Subtype").child("Sparkling");
        spinnerList_wineSubtype = new ArrayList<>();
        adapter_wineSubtype = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerList_wineSubtype);
        spinner_wineSubtype.setAdapter(adapter_wineSubtype);
        dbRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot item : snapshot.getChildren())
                {
                    spinnerList_wineSubtype.add(item.getValue().toString());
                }
                adapter_wineSubtype.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
        spinner_wineSubtype.setOnItemSelectedListener(this);
        //Populates a spinner (Subtype) from the FireBase DB

        //Populates a spinner (Origin) from the FireBase DB
        spinner_wineOrigin = getView().findViewById(R.id.wineOrigin);
        dbRef = FirebaseDatabase.getInstance().getReference("Category").child("Origin");
        spinnerList_wineOrigin = new ArrayList<>();
        adapter_wineOrigin = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerList_wineOrigin);
        spinner_wineOrigin.setAdapter(adapter_wineOrigin);
        dbRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot item : snapshot.getChildren())
                {
                    spinnerList_wineOrigin.add(item.getValue().toString());
                }
                adapter_wineOrigin.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }

        });
        spinner_wineOrigin.setOnItemSelectedListener(this);
        //Populates a spinner (Origin) from the FireBase DB


        //Populates a spinner (BottleType) from the FireBase DB
        spinner_wineBottleType = getView().findViewById(R.id.wineBottleType);
        dbRef = FirebaseDatabase.getInstance().getReference("Category").child("BottleType");
        spinnerList_wineBottleType = new ArrayList<>();
        adapter_wineBottleType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerList_wineBottleType);
        spinner_wineBottleType.setAdapter(adapter_wineBottleType);
        dbRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot item : snapshot.getChildren())
                {
                    spinnerList_wineBottleType.add(item.getValue().toString());
                }
                adapter_wineBottleType.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
        spinner_wineBottleType.setOnItemSelectedListener(this);
        //Populates a spinner (BottleType) from the FireBase DB

        Button addwine = (Button) getView().findViewById(R.id.addwine);
        EditText WineName = (EditText) getView().findViewById(R.id.WineName);
        EditText WineAlco = (EditText) getView().findViewById(R.id.WineAlco);
        EditText WineYear = (EditText) getView().findViewById(R.id.WineYear);

        addwine.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (WineName.getText().toString().isEmpty()
                        || spinner_wineType.getSelectedItem().toString().isEmpty()
                        || spinner_wineSubtype.getSelectedItem().toString().isEmpty()
                        || spinner_wineOrigin.getSelectedItem().toString().isEmpty()
                        || spinner_wineBottleType.getSelectedItem().toString().isEmpty()
                        || WineAlco.getText().toString().isEmpty()
                        || WineYear.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Please fill in all details!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}