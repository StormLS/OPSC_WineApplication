package com.example.winecompendium;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ProgressBar;
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
 * Use the {@link view_winetype_category#newInstance} factory method to
 * create an instance of this fragment.
 */
public class view_winetype_category extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public view_winetype_category() {
        // Required empty public constructor
    }

    private GridLayout layout;
    private String wine_name;
    private String userID;
    private ProgressBar pBar;
    private TextView txtGoal;
    private TextView txtTotal;


    // TODO: Rename and change types and number of parameters
    public static view_winetype_category newInstance(String title) {
        view_winetype_category fragment = new view_winetype_category();
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
        return inflater.inflate(R.layout.fragment_view_winetype_category_dialogue, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        userID = fUser.getUid();

        layout = getView().findViewById(R.id.container);
        pBar = getView().findViewById(R.id.pBarWineTypes);
        txtTotal = getView().findViewById(R.id.txtTotalWineType);
        txtGoal = getView().findViewById(R.id.txtGoalWineType);

        CheckForGoalImplementation();
        PopulateCards();
        RunLoadingScreen();

    }

    /*
    --------------------------Check if user has entered a goal amount-------------------------------
     */
    private void CheckForGoalImplementation() {

        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("AddGoal_Categories").child("Goals").child("WineType");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChildren())
                {
                    RetrieveGoalData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //----------------------------------------------------------------------------------------------


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


    private void RetrieveGoalData() {

        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference("Users");

        //Retrieving goal number
        ref.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                Integer value = dataSnapshot.child(userID).child("AddGoal_Categories").child("Goals").child("WineType").
                        child("Goal Num").getValue(Integer.class);

                SetProgressBar(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void SetProgressBar(Integer goal) {

        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference("Users");

        //Retrieving goal number
        ref.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                Integer value = dataSnapshot.child(userID).child("AddGoal_Categories").child("Goals").child("WineType").
                        child("Total Wines").getValue(Integer.class);

                pBar.setMax(goal);
                pBar.setProgress(value);

                txtGoal.setText(String.valueOf(goal));
                txtTotal.setText(String.valueOf(value));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void PopulateCards() {

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();

        userID = fUser.getUid();
        Query query = dataRef.child("Users").child(userID).child("Categories").child("WineType");
        ArrayList<String> winetypeList = new ArrayList<>();

        //check if current user is logged in
        if (fUser != null) {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        for(DataSnapshot item : snapshot.getChildren())
                        {
                            //Retrieving the wine type category names
                            winetypeList.add(item.getValue().toString());

                            //Removes any square brackets from the array
                            String wines = winetypeList.toString().replace("[", "").
                                           replace("]", "");

                            //Display the card with the retrieved wine type name
                            AddCard(wines);
                            winetypeList.clear();

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

    private void AddCard(String WinetypeName) {

        View cardView = getLayoutInflater().inflate(R.layout.card_view_cat_winetype, null);
        TextView title = cardView.findViewById(R.id.txtWinetypeTitle);

        title.setText(WinetypeName);

        layout.addView(cardView);

    }

}