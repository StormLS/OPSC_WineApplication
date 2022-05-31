package com.example.winecompendium;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
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
    private Button btn_addwines;
    private Button addwineTEST;
    private Button btnViewWine;
    private GridLayout layout;

    private String userID;
    private String wineName;
    private String wineType;
    private String wineDesc;
    private String wineImageLink;

    private DatabaseReference dbRef;
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();


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

        View wine_cardview = getLayoutInflater().inflate(R.layout.card_wine, null);

        addwineTEST = getView().findViewById(R.id.addwine);
        layout = getView().findViewById(R.id.container);
        btnViewWine = getView().findViewById(R.id.button6);

        userID = fUser.getUid();
        populateCards();

        btnViewWine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowViewWineDialogue();
            }
        });


    }

    private StorageReference storageReference;

    private void populateCards()
    {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        userID = fUser.getUid();
        Bitmap wineBitmap = null;

        //query based on current user id. Finding the first name of current user to display on dashboard
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
        Query query = dataRef.child("Users").child(userID).child("CollectedWines");

        //check if current user is logged in
        if (fUser != null)
        {
            query.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    if (snapshot.exists()) {
                        for (DataSnapshot ds : snapshot.getChildren())
                        {
                            //Getting the first name value
                            wines wine = ds.getValue(wines.class);
                            wineName = wine.getWineName();
                            wineType = wine.getWineType();
                            wineDesc = wine.getWineDesc();
                            wineImageLink = wine.getWineImage();

                            Bitmap wineBitmap = null;
                            StorageReference fileRef = storageReference.child("WineImage/" + userID ).child(wineImageLink);
                            try
                            {
                                final File localFile = File.createTempFile("wineImage", "jpg");
                                fileRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>()
                                {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot)
                                    {
                                        Bitmap wineBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                        addCard(wineName,wineType,wineDesc, wineBitmap);

                                        Toast.makeText(getView().getContext(), "Image was retrieved", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener()
                                {
                                    @Override
                                    public void onFailure(@NonNull Exception e)
                                    {
                                        Toast.makeText(getView().getContext(), "Error in image retrieval\n" + e, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(getView().getContext(), "Major Error:\n" + e, Toast.LENGTH_LONG).show();
                            }

                            Log.d("wine card","display wine card");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("error", error.getMessage());
                }
            });

        } else {
            Toast.makeText(getContext(),"Error populating wine cards",Toast.LENGTH_SHORT).show();
        }
    }

    /*
    Show the add new wine type to category dialogue box
   */
    private void ShowViewWineDialogue()
    {
        FragmentManager fm =  getChildFragmentManager();
        viewwine_fragment_dialogue viewWineDialogue = viewwine_fragment_dialogue.newInstance("WineType item");
        viewWineDialogue.show(fm, "view_wine_dialogue");
    }

    private void addCard(String WineName, String WineType, String WineDesc, Bitmap WineImage)
    {
        View wine_cardview = getLayoutInflater().inflate(R.layout.card_wine, null);

        TextView name = wine_cardview.findViewById(R.id.card_wineName);
        TextView type = wine_cardview.findViewById(R.id.card_WineType);
        TextView desc = wine_cardview.findViewById(R.id.card_WineDesc);
        ImageView image = wine_cardview.findViewById(R.id.card_WineImage);

        name.setText(WineName);
        type.setText(WineType);
        desc.setText(WineDesc);
        image.setImageBitmap(WineImage);

        name.setText(WineName);

        layout.addView(wine_cardview);
    }
}