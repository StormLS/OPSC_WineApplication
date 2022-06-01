package com.example.winecompendium;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link allwines_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class allwines_fragment extends Fragment {

    private TextView txtWName;
    private TextView txtWType;
    private TextView txtWDesc;
    private TextView txtNavName;
    private GridLayout layout;

    private Spinner filter;

    private int wineCounterINT = 0;
    private TextView wineCounterUI;

    private String userID;
    private String wineName;
    private String wineType;
    private String wineDesc;
    private String wineImageLink;
    private String wineKey;
    public static String currentKey = "TestKey";

    private DatabaseReference dbRef;
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    private StorageReference storageReference;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public allwines_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment allwines_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static allwines_fragment newInstance(String param1, String param2) {
        allwines_fragment fragment = new allwines_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_allwines_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        wineCounterUI = getView().findViewById(R.id.wineCounter);
        layout = getView().findViewById(R.id.container);

        filter = getView().findViewById(R.id.wineTypeFilter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.filter_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter.setAdapter(adapter);

        String x = filter.getSelectedItem().toString();
        Toast.makeText(getContext(), x, Toast.LENGTH_LONG).show();

        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                wineCounterINT = 0;
                layout.removeAllViews();
                populateCards();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        //spinner_wineType = getView().findViewById(R.id.wineType);

        userID = fUser.getUid();
        RunLoadingScreen();
    }

    /*
     ---------Display Loading screen dialogue to allow for the all wines fragment to fully load-----
    */
    private void RunLoadingScreen() {

        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        builder.setView(inflater.inflate(R.layout.custom_loading_dialogue,null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
            }
        },3900); //Loading screen will run for 5 seconds
    }
    //----------------------------------------------------------------------------------------------

    private void populateCards()
    {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        userID = fUser.getUid();

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
        Query query = dataRef.child("Users").child(userID).child("CollectedWines");

        //check if current user is logged in
        if (fUser != null) {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        for (DataSnapshot ds : snapshot.getChildren())
                        {
                            //Getting the wine values
                            wines wine = ds.getValue(wines.class);
                            wineName = wine.getWineName().trim();
                            wineType = wine.getWineType().trim();
                            wineDesc = wine.getWineDesc().trim();
                            wineImageLink = wine.getWineImage().trim();
                            wineKey = ds.getKey();

                            String winetypeFilter = filter.getSelectedItem().toString();

                            if(winetypeFilter.equals("None"))
                            {
                                addCard(wineName, wineType, wineDesc, wineImageLink, wineKey);
                            }
                            else if(winetypeFilter.equals(wineType))
                            {
                                addCard(wineName, wineType, wineDesc, wineImageLink, wineKey);
                            }
                            else
                            {
                                String temp = String.valueOf(wineCounterINT);
                                wineCounterUI.setText(temp);
                            }

                            Log.d("wine card", "display wine card");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("error", error.getMessage());
                }
            });
        }
    }

    private String winetypeFilter = "None"; // This is the DEBUG FILTER

    public String ReturnCurrentKey()
    {

        return currentKey;
    }



    private void addCard(String WineName, String WineType, String WineDesc, String WineImageLink, String WineKey)
    {
        wineCounterINT++;

        View wine_cardview = getLayoutInflater().inflate(R.layout.card_wine, null);

        TextView name = wine_cardview.findViewById(R.id.card_wineName);
        TextView type = wine_cardview.findViewById(R.id.card_WineType);
        TextView desc = wine_cardview.findViewById(R.id.card_WineDesc);
        ImageView image = wine_cardview.findViewById(R.id.card_WineImage);
        Button btnViewWine = wine_cardview.findViewById(R.id.btnViewWine);

        name.setText(WineName);
        type.setText(WineType);
        desc.setText(WineDesc);


        //Retrieving the image based on the image link
        StorageReference fileRef = storageReference.child("WineImage/" + userID ).child(WineImageLink);
        try
        {

            final File localFile = File.createTempFile("wineImage", "jpg");
            fileRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot)
                {
                    Bitmap wineBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());

                    image.setImageBitmap(wineBitmap);
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Log.e("error", e.getMessage());
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getView().getContext(), "Major Error:\n" + e, Toast.LENGTH_LONG).show();
        }

        //Load the cards
        layout.addView(wine_cardview);


        //If the user clicks on the view wine button
        btnViewWine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentKey = WineKey;
                ShowViewWineDialogue();
            }
        });

        String temp = String.valueOf(wineCounterINT);
        wineCounterUI.setText(temp);
    }

    /*
    Show the add View Wine dialogue box
    */
    private void ShowViewWineDialogue()
    {
        FragmentManager fm =  getChildFragmentManager();
        viewwine_fragment_dialogue viewWineDialogue = viewwine_fragment_dialogue.newInstance("WineType item");
        viewWineDialogue.show(fm, "view_wine_dialogue");

    }



}