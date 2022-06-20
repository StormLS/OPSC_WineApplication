package com.example.winecompendium;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link view_bottletype_category#newInstance} factory method to
 * create an instance of this fragment.
 */
public class view_bottletype_category extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public view_bottletype_category() {
        // Required empty public constructor
    }

    private GridLayout layout;
    private String userID;
    private TextView txtItems;


    // TODO: Rename and change types and number of parameters
    public static view_bottletype_category newInstance(String title) {
        view_bottletype_category fragment = new view_bottletype_category();
        Bundle args = new Bundle();
        args.putString("Subtype",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        return inflater.inflate(R.layout.fragment_view_bottletype_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        userID = fUser.getUid();

        layout = getView().findViewById(R.id.container);
        txtItems = getView().findViewById(R.id.txtBT_Items);

        PopulateCards();
        RunLoadingScreen();
        LoadNumItems();
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
        },1300); //Loading screen will run for 5 seconds
    }
    //----------------------------------------------------------------------------------------------

    /*
    ---------------------------------- Retrieving category data ------------------------------------
    */
    private void PopulateCards() {

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();

        userID = fUser.getUid();
        Query query = dataRef.child("Users").child(userID).child("Categories").child("BottleType");
        ArrayList<String> bottleTypeList = new ArrayList<>();

        //check if current user is logged in
        if (fUser != null) {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        for(DataSnapshot item : snapshot.getChildren())
                        {
                            //Retrieving the wine type category names
                            bottleTypeList.add(item.getValue().toString());

                            //Removes any square brackets from the array
                            String bottles = bottleTypeList.toString().replace("[", "").
                                    replace("]", "");

                            //Display the card with the retrieved wine type name
                            AddCard(bottles);
                            bottleTypeList.clear();

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

    /*
   -------------------------- This method will display populated card views -----------------------
    */
    private void AddCard(String BottletypeName) {

        View cardView = getLayoutInflater().inflate(R.layout.card_view_cat_bottletype, null);
        TextView title = cardView.findViewById(R.id.txtBottletypeTitle);

        title.setText(BottletypeName);

        layout.addView(cardView);
    }
    //----------------------------------------------------------------------------------------------

    private void LoadNumItems() {

        DatabaseReference ref;

        ref = FirebaseDatabase.getInstance().getReference("Users").child(userID)
                .child("AddGoal_Categories").child("Goals").child("BottleType");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChildren())
                {
                    Integer total = snapshot.child("Total Wines").getValue(Integer.class);
                    txtItems.setText("Current number of items: " + total);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}