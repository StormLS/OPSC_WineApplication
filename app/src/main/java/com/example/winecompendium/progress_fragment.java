package com.example.winecompendium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link progress_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class progress_fragment extends Fragment {

    private String userID;
    private ProgressBar pTypeBar;
    private ProgressBar pSubTypeBar;
    private ProgressBar pOriginBar;
    private ProgressBar pBottleBar;
    private EditText txtTypeTotal;
    private EditText txtTypeGoal;
    private EditText txtSubTotal;
    private EditText txtSubGoal;
    private EditText txtOriginTotal;
    private EditText txtOriginGoal;
    private EditText txtBottleTotal;
    private EditText txtBottleGoal;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public progress_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment progress_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static progress_fragment newInstance(String param1, String param2) {
        progress_fragment fragment = new progress_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        return inflater.inflate(R.layout.fragment_progress_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        userID = fUser.getUid();
        pTypeBar = getView().findViewById(R.id.pBarWineTypes);
        pSubTypeBar = getView().findViewById(R.id.pBarSubtypes);
        pOriginBar = getView().findViewById(R.id.pBarOrigin);
        pBottleBar = getView().findViewById(R.id.pBarBottleType);
        txtTypeTotal = getView().findViewById(R.id.txtTypeTotal);
        txtTypeGoal = getView().findViewById(R.id.txtTypeGoal);
        txtSubTotal = getView().findViewById(R.id.txtSubtypeTotal);
        txtSubGoal = getView().findViewById(R.id.txtSubtypeGoal);
        txtOriginTotal = getView().findViewById(R.id.txtOriginTotal);
        txtOriginGoal = getView().findViewById(R.id.txtOriginGoal);
        txtBottleTotal = getView().findViewById(R.id.txtBTTotal);
        txtBottleGoal = getView().findViewById(R.id.txtBTGoal);

        CheckForGoalImplementation();
    }

    /*
    --------------------------Check if user has entered a goal amount-------------------------------
    -------------------------- Then update the progress bar if true --------------------------------
     */
    private void CheckForGoalImplementation() {

        DatabaseReference ref1;
        DatabaseReference ref2;
        DatabaseReference ref3;
        DatabaseReference ref4;

        ref1 = FirebaseDatabase.getInstance().getReference("Users").child(userID)
                .child("AddGoal_Categories").child("Goals").child("WineType");

        /*
        Wine Type Goal implementation
         */
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChildren())
                {
                    Integer goal = snapshot.child("Goal Num").getValue(Integer.class);
                    Integer total = snapshot.child("Total Wines").getValue(Integer.class);

                    pTypeBar.setMax(goal);
                    pTypeBar.setProgress(total);
                    txtTypeGoal.setText(String.valueOf(goal));
                    txtTypeTotal.setText(String.valueOf(total));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


         /*
        Wine SubType Goal implementation
         */
        ref2 = FirebaseDatabase.getInstance().getReference("Users").child(userID)
                .child("AddGoal_Categories").child("Goals").child("SubType");

        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChildren())
                {
                    Integer goal = snapshot.child("Goal Num").getValue(Integer.class);
                    Integer total = snapshot.child("Total Wines").getValue(Integer.class);

                    pSubTypeBar.setMax(goal);
                    pSubTypeBar.setProgress(total);
                    txtSubGoal.setText(String.valueOf(goal));
                    txtSubTotal.setText(String.valueOf(total));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


         /*
        Origin Goal implementation
         */
        ref3 = FirebaseDatabase.getInstance().getReference("Users").child(userID)
                .child("AddGoal_Categories").child("Goals").child("Origin");

        ref3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChildren())
                {
                    Integer goal = snapshot.child("Goal Num").getValue(Integer.class);
                    Integer total = snapshot.child("Total Wines").getValue(Integer.class);

                    pOriginBar.setMax(goal);
                    pOriginBar.setProgress(total);
                    txtOriginGoal.setText(String.valueOf(goal));
                    txtOriginTotal.setText(String.valueOf(total));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


         /*
         Bottle Type Goal implementation
         */
        ref4 = FirebaseDatabase.getInstance().getReference("Users").child(userID)
                .child("AddGoal_Categories").child("Goals").child("BottleType");

        ref4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChildren())
                {
                    Integer goal = snapshot.child("Goal Num").getValue(Integer.class);
                    Integer total = snapshot.child("Total Wines").getValue(Integer.class);

                    pBottleBar.setMax(goal);
                    pBottleBar.setProgress(total);
                    txtBottleGoal.setText(String.valueOf(goal));
                    txtBottleTotal.setText(String.valueOf(total));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //----------------------------------------------------------------------------------------------

}