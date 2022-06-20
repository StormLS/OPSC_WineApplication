package com.example.winecompendium;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.Spinner;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link view_subtype_category#newInstance} factory method to
 * create an instance of this fragment.
 */
public class view_subtype_category extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public view_subtype_category() {
        // Required empty public constructor
    }

    private GridLayout layout;
    private String userID;
    private DatabaseReference dataRef;
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    private ArrayList<String> spinList_WineTypes;
    private ArrayAdapter<String> adapter_wineType;
    private Spinner spinner;
    private ArrayList<String> spinnerList_wineSubtype;
    private TextView txtItems;


    // TODO: Rename and change types and number of parameters
    public static view_subtype_category newInstance(String title) {
        view_subtype_category fragment = new view_subtype_category();
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
        return inflater.inflate(R.layout.fragment_view_subtype_category, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        dataRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = fUser.getUid();

        spinner = getView().findViewById(R.id.spinner);
        layout = getView().findViewById(R.id.container);
        txtItems = getView().findViewById(R.id.txtBT_Items);

        PopulateSpinner();
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
        },2000); //Loading screen will run for 5 seconds
    }
    //----------------------------------------------------------------------------------------------


    /*
     ----------------------------------- Populate Wine Type Spinner --------------------------------
    */
    private void PopulateSpinner() {

        DatabaseReference refWineType = dataRef.child(userID).child("Categories").child("WineType");

        spinList_WineTypes = new ArrayList<>();

        refWineType.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot item : snapshot.getChildren())
                {
                    spinList_WineTypes.add(item.getValue().toString());
                }

                adapter_wineType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinList_WineTypes);
                spinner.setAdapter(adapter_wineType);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedWineType = spinner.getSelectedItem().toString();
                layout.removeAllViews();
                PopulateCard(selectedWineType);
                LoadNumItems(selectedWineType);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
               //
            }
        });


    }
    //----------------------------------------------------------------------------------------------

    /*
    ---------------------------------- Retrieving category data ------------------------------------
     */
    private void PopulateCard(String selectedWine) {

        DatabaseReference newRef = dataRef.child(userID).child("Categories").child("SubType").child(selectedWine);
        spinnerList_wineSubtype = new ArrayList<>();

        newRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot item : snapshot.getChildren())
                {
                    //Retrieving the wine type category names
                    spinnerList_wineSubtype.add(item.getValue().toString());

                    //Removes any square brackets from the array
                    String subtypes = spinnerList_wineSubtype.toString().replace("[", "").
                            replace("]", "");

                    //Display the card with the retrieved wine type name
                    AddCard(subtypes);
                    spinnerList_wineSubtype.clear();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    /*
    -------------------------- This method will display populated card views -----------------------
     */
    private void AddCard(String SubtypeName) {

        View cardView = getLayoutInflater().inflate(R.layout.card_view_cat_subtype, null);
        TextView title = cardView.findViewById(R.id.txtSubtypeTitle);

        title.setText(SubtypeName);

        layout.addView(cardView);
    }
    //----------------------------------------------------------------------------------------------

    private void LoadNumItems(String selectedWine) {

        DatabaseReference ref;

        ref = FirebaseDatabase.getInstance().getReference("Users").child(userID)
                .child("AddGoal_Categories").child("Goals").child("SubType").child(selectedWine);

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