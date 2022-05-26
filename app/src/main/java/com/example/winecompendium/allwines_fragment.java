package com.example.winecompendium;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link allwines_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class allwines_fragment extends Fragment {

    private TextView txtNavName;
    private Button btn_addwines;



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

    private Button addwineTEST;
    private GridLayout layout;

    DatabaseReference dbRef;
    public String WineName;

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
        addwineTEST = getView().findViewById(R.id.addwine);
        layout = getView().findViewById(R.id.container);

        addwineTEST.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                populateCards();
            }
        });

    }

    private void populateCards()
    {
        //Populates a spinner (WineTypes) from the FireBase DB
        dbRef = FirebaseDatabase.getInstance().getReference("WineExample");
        Query query = dbRef.child("Name");

        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    WineName = snapshot.getValue(String.class);
                    //Toast.makeText(getView().getContext(), "Output is: " + WineName, Toast.LENGTH_SHORT).show();

                    String WineType = "White", WineDesc = "Its a white wine";

                    addCard(WineName, WineType, WineDesc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.e("error", error.getMessage());
            }
        });
    }

    private void addCard(String WineName, String WineType, String WineDesc)
    {
        View wine_cardview = getLayoutInflater().inflate(R.layout.card_wine, null);

        TextView name = wine_cardview.findViewById(R.id.card_wineName);
        TextView type = wine_cardview.findViewById(R.id.card_WineType);
        TextView desc = wine_cardview.findViewById(R.id.card_WineDesc);

        layout.addView(wine_cardview);
    }
}