package com.example.winecompendium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add_goal_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_goal_fragment extends Fragment {

    private Spinner spinnerCat;
    private EditText edtTxtGoal;
    private String userID;
    private String selectedCat;
    private Integer numGoal;
    private Button btnAddGoal;
    private Integer tot;
    private ArrayList<String> spinnerListCategories;
    private ArrayAdapter<String> adapterCategories;
    private DatabaseReference ref;
    private DatabaseReference addRef;
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public add_goal_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add_goal_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static add_goal_fragment newInstance(String param1, String param2) {
        add_goal_fragment fragment = new add_goal_fragment();
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
        return inflater.inflate(R.layout.fragment_add_goal_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        userID = fUser.getUid();
        spinnerCat = getView().findViewById(R.id.spinnerCat);
        edtTxtGoal = getView().findViewById(R.id.txtGoal);
        btnAddGoal = getView().findViewById(R.id.btnSetGoal);

        PopulateSpinners();

        btnAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtTxtGoal.getText().toString().isEmpty())
                {
                    edtTxtGoal.setError("Please enter a value.");
                    return;
                }
                else
                {
                    RetrieveTotalNumWinesPerCat();
                    numGoal =  Integer.valueOf(edtTxtGoal.getText().toString());
                }
            }
        });


        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedCat = spinnerCat.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
            }
        });

    }


    /*
    ------------------------------- Populating spinner with categories -----------------------------
     */
    private void PopulateSpinners() {

        ref = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("AddGoal_Categories").child("AllCategories");

        spinnerListCategories = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot item : snapshot.getChildren())
                {
                    spinnerListCategories.add(item.getValue().toString());
                }

                adapterCategories = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerListCategories);
                spinnerCat.setAdapter(adapterCategories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


    }
    //----------------------------------------------------------------------------------------------

    /*
    ------------------ Retrieving the total number of wines per cat the user has -------------------
     */
    private void RetrieveTotalNumWinesPerCat() {

        tot = 0;

        ref = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("Categories").child(selectedCat);

        Set<String> WinesList = new HashSet<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item: snapshot.getChildren()) {

                    WinesList.add(item.getValue().toString());

                    //Retrieve the total number of wines in CollectedWines
                    tot = WinesList.size();
                }

                if (numGoal < tot) {
                    Toast.makeText(getContext(),"Please enter a value that is higher than the number of category items! " +
                            "Current number: " + tot,Toast.LENGTH_LONG).show();
                } else {
                    AddTotalToDB(tot);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    //----------------------------------------------------------------------------------------------

    /*
   -------------------- Write the total wines amount to the database per category ------------------
    */
    private void AddTotalToDB(int total) {

        addRef = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("AddGoal_Categories").child("Goals").child(selectedCat).child("Total Wines");

        addRef.setValue(total).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                AddToDatabase();
            }
        });
    }
    //----------------------------------------------------------------------------------------------


    /*
    ------------------------ Write the goal amount to the database per category --------------------
     */
    private void AddToDatabase() {

        ref = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("AddGoal_Categories").child("Goals").child(selectedCat).child("Goal Num");

        ref.setValue(numGoal).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Goal successfully set for category: " + selectedCat,Toast.LENGTH_SHORT).show();
                ResetUI();
            }
        });

    }
    //----------------------------------------------------------------------------------------------


    private void ResetUI() {
        edtTxtGoal.setText(null);
    }




}