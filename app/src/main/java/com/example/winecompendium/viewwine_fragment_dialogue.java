package com.example.winecompendium;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * Use the {@link viewwine_fragment_dialogue#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewwine_fragment_dialogue extends androidx.fragment.app.DialogFragment {

    private Button btnCloseDialogue;
    private Button btnShowRating;
    private Button btnShowCategory;
    private TextView txtwineName;
    private TextView txtwineType;
    private TextView txtwineSubtype;
    private TextView txtwineOrigin;
    private TextView txtwineBottleType;
    private TextView txtwineDesc;
    private TextView txtwinePerc;
    private TextView txtwineYear;
    private TextView txtwineAcquired;
    private ImageView imgViewWine;

    private String wineName;
    private String wineType;
    private String wineSubtype;
    private String wineOrigin;
    private String wineBottleType;
    private String wineDesc;
    private Float winePerc;
    private String wineYear;
    private String wineAcquired;
    private static Float wineRating;
    private Bitmap wineImage;
    private String wineImgLink;



    private String userID;
    private static String currentKey;

    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();



    public viewwine_fragment_dialogue() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static viewwine_fragment_dialogue newInstance(String title) {
        viewwine_fragment_dialogue fragment = new viewwine_fragment_dialogue();
        Bundle args = new Bundle();
        args.putString("Subtype",title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_viewwine_dialogue, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        userID = fUser.getUid();

        btnCloseDialogue = getView().findViewById(R.id.btnCloseViewWine);
        btnShowRating = getView().findViewById(R.id.btnViewRating);
        btnShowCategory = getView().findViewById(R.id.btnViewCategory);
        txtwineName = getView().findViewById(R.id.txtWineName);
        txtwineType = getView().findViewById(R.id.txtWineType);
        txtwineSubtype = getView().findViewById(R.id.txtWineSubtype);
        txtwineBottleType = getView().findViewById(R.id.txtBottleType);
        txtwineOrigin = getView().findViewById(R.id.txtOrigin);
        txtwineAcquired = getView().findViewById(R.id.txtDateAcquired);
        txtwineYear = getView().findViewById(R.id.txtYear);
        txtwinePerc = getView().findViewById(R.id.txtPercent);
        txtwineDesc = getView().findViewById(R.id.txtDesc3);
        imgViewWine = getView().findViewById(R.id.imgWine);

        txtwineDesc.setEnabled(false);

        allwines_fragment frag = new allwines_fragment();
        currentKey = frag.ReturnCurrentKey(); //the key of the wine that is currently selected

        PopulateViewing();

        btnCloseDialogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseDialogueBox();
            }
        });

        btnShowRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowRatingDialogueBox();
            }
        });

        btnShowCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),categories.class);
                startActivity(i);
                CloseDialogueBox();
            }
        });
    }

    private void PopulateViewing() {

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
        Query ref2 = dataRef.child("Users").child(userID).child("CollectedWines").orderByKey().equalTo(currentKey);


        ref2.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists()) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        //Retrieving the wine
                        wines wine = ds.getValue(wines.class);

                        wineName = wine.getWineName();
                        wineType = wine.getWineType();
                        wineSubtype = wine.getWineSubtype();
                        wineOrigin = wine.getWineOrigin();
                        wineBottleType = wine.getWineBottleType();
                        wineYear = wine.getWineYear();
                        wineAcquired = wine.getWineDateAcquired();
                        winePerc = wine.getWinePerc();
                        wineDesc = wine.getWineDesc();
                        wineImgLink = wine.getWineImage();
                        wineRating = wine.getWineRating();

                    }

                    txtwineName.setText(wineName);
                    txtwineType.setText(wineType);
                    txtwineSubtype.setText(wineSubtype);
                    txtwineBottleType.setText(wineBottleType);
                    txtwineOrigin.setText(wineOrigin);
                    txtwineAcquired.setText(wineAcquired);
                    txtwineYear.setText(wineYear);
                    txtwinePerc.setText((winePerc) + "%");
                    txtwineDesc.setText(wineDesc);
                    RetrieveImage(wineImgLink);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("error", error.getMessage());
            }
            });

    }

    public Float ReturnProgress() {
        return wineRating;
    }

    private void RetrieveImage(String imgLink) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        //Retrieving the image based on the image link
        StorageReference fileRef = storageReference.child("WineImage/" + userID ).child(imgLink);
        try
        {

            final File localFile = File.createTempFile("wineImage", "jpg");
            fileRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot)
                {
                    wineImage = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imgViewWine.setImageBitmap(wineImage);
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Log.e("error_retrieving_img", e.getMessage());
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getView().getContext(), "Major Error:\n" + e, Toast.LENGTH_LONG).show();
        }

    }

    private void ShowRatingDialogueBox() {
        FragmentManager fm =  getChildFragmentManager();
        view_rating_fragment viewRatingDialogue = view_rating_fragment.newInstance("WineType item");
        viewRatingDialogue.show(fm, "view_rating_dialogue");
    }

    private void CloseDialogueBox() {
        this.dismiss();
    }

}