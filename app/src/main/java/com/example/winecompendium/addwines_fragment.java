package com.example.winecompendium;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addwines_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addwines_fragment extends Fragment
{

    private Button btnSetDesc;
    private Button browseGallery;
    private Button openCamera;
    private Button addwine;
    private EditText WineName;
    private EditText WineAlco;
    private EditText WineYear;

    private Spinner spinner_wineType;
    private Spinner spinner_wineSubtype;
    private Spinner spinner_wineOrigin;
    private Spinner spinner_wineBottleType;

    private ImageButton btnAddItemType;
    private ImageButton btnAddItemSubtype;
    private ImageButton btnAddItemOrigin;
    private ImageButton btnAddItemBottleType;

    private DatabaseReference dbRef;
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

    ArrayAdapter<String> adapter_wineType;
    ArrayAdapter<String> adapter_wineSubtype;
    ArrayAdapter<String> adapter_wineOrigin;
    ArrayAdapter<String> adapter_wineBottleType;

    ArrayList<String> spinnerList_WineTypes;
    ArrayList<String> spinnerList_wineSubtype;
    ArrayList<String> spinnerList_wineOrigin;
    ArrayList<String> spinnerList_wineBottleType;

    public static String _heading = "text";
    private ImageView wineImage;

    private String userID;


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
        spinner_wineType = getView().findViewById(R.id.wineType);
        spinner_wineSubtype = getView().findViewById(R.id.wineSubtype);
        spinner_wineOrigin = getView().findViewById(R.id.wineOrigin);
        spinner_wineBottleType = getView().findViewById(R.id.wineBottleType);
        btnSetDesc = getView().findViewById(R.id.btn_set_desc);
        browseGallery = getView().findViewById(R.id.browseGallery);
        wineImage = getView().findViewById(R.id.wineImage);
        openCamera = getView().findViewById(R.id.openCamera);
        addwine = getView().findViewById(R.id.addwine);
        WineName = getView().findViewById(R.id.WineName);
        WineAlco =  getView().findViewById(R.id.WineAlco);
        WineYear =  getView().findViewById(R.id.WineYear);
        btnAddItemType = getView().findViewById(R.id.btnAddWines_Type);
        btnAddItemSubtype = getView().findViewById(R.id.btn_AddWines_Subtype);
        btnAddItemOrigin = getView().findViewById(R.id.btnAddWines_Origin);
        btnAddItemBottleType = getView().findViewById(R.id.btnAddWines_BottleType);

        userID = fUser.getUid();
        populateAllSpinners();

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

        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                openCamera();
            }
        });

        btnAddItemType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAddItemDialogueBox();
                _heading = "Wine Type";
            }
        });

        btnAddItemSubtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAddItemDialogueBox();
                _heading = "Wine Subtype";
            }
        });

        btnAddItemOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAddItemDialogueBox();
                _heading = "Origin";
            }
        });

        btnAddItemBottleType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAddItemDialogueBox();
                _heading = "Bottle Type";
            }
        });
    }


    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int TAKE_IMAGE_REQUEST = 100;
    private Uri mImageUri;

    private void openCamera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_IMAGE_REQUEST);
    }

    private void openFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == TAKE_IMAGE_REQUEST )
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            wineImage.setImageBitmap(photo);
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            mImageUri = data.getData();

           // Picasso.with(getView().getContext()).load(mImageUri).into(wineImage);
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

    public String ReturnHeading() {
        return _heading;
    }

    /*
    Show the add new item to category dialogue box
     */
    private void ShowAddItemDialogueBox() {
        FragmentManager fm =  getChildFragmentManager();
        add_wines_category_dialogue addItemDialogue = add_wines_category_dialogue.newInstance("Category Item");
        addItemDialogue.show(fm, "fragment_add_item");
    }


    private void populateAllSpinners()
    {
        //Populates a spinner (WineTypes) from the FireBase DB
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("Categories").child("WineType");
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

        //Populates a spinner (WineTypes) from the FireBase DB

          /*
        TODO: ---------------Populate spinner according to the wine type selected-------------------
         */

        spinner_wineType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Populates a spinner (Subtype) from the FireBase DB
                String selectedWineType =spinner_wineType.getSelectedItem().toString();
                dbRef = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("Categories").child("SubType").child(selectedWineType);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinner_wineType.setSelection(0);
            }
        });

        //Todo: ------------------------------------------------------------------------------------

        //Populates a spinner (Origin) from the FireBase DB
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("Categories").child("Origin");
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
        //Populates a spinner (Origin) from the FireBase DB

        //Populates a spinner (BottleType) from the FireBase DB
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("Categories").child("BottleType");
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
        //Populates a spinner (BottleType) from the FireBase DB


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

                //TODO: Add wine implementation
                {
                    Toast.makeText(getContext(), "Please fill in all details!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}