package com.example.winecompendium;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class allwines_fragment extends Fragment
{
    private TextView txtNavName;
    private Button btn_addwines;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public allwines_fragment()
    {
        // Required empty public constructor
    }

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

    private Button addwineTEST;
    private GridLayout layout;

    private DatabaseReference dbRef;

    //When information isn't read properly from the DB
    public String WineName;
    public String WineType;
    public String WineDesc;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        queryData();
        addwineTEST = getView().findViewById(R.id.addwine);
        layout = getView().findViewById(R.id.container);

        addwineTEST.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addCard(WineName, WineType, WineDesc);
            }
        });
    }

    private void queryData()
    {
        //Populates a spinner (WineTypes) from the FireBase DB
        dbRef = FirebaseDatabase.getInstance().getReference("WineExample");

        Query name_query = dbRef.child("Name");
        name_query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    //TODO Code for Database retrieval of information
                    WineName = snapshot.getValue(String.class);
                    //TODO Code for Database retrieval of information
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.e("error", error.getMessage());
            }
        });

        Query type_query = dbRef.child("Type");
        type_query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    //TODO Code for Database retrieval of information
                    WineType = snapshot.getValue(String.class);
                    //TODO Code for Database retrieval of information
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.e("error", error.getMessage());
            }
        });

        Query desc_query = dbRef.child("Desc");
        desc_query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    //TODO Code for Database retrieval of information
                    WineDesc = snapshot.getValue(String.class);
                    //TODO Code for Database retrieval of information
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.e("error", error.getMessage());
            }
        });

        /*--------------------- STILL NEED TO ADD CODE FOR IMAGE RETRIEVAL -------------------------
        Query desc_query = dbRef.child("Desc");
        type_query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    //TODO Code for Database retrieval of information
                    WineType = snapshot.getValue(String.class);
                    //TODO Code for Database retrieval of information
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.e("error", error.getMessage());
            }
        });
        --------------------- STILL NEED TO ADD CODE FOR IMAGE RETRIEVAL -------------------------*/
    }

    private void addCard(String WineName, String WineType, String WineDesc)
    {
        View wine_cardview = getLayoutInflater().inflate(R.layout.card_wine, null);

        TextView name = wine_cardview.findViewById(R.id.card_wineName);
        TextView type = wine_cardview.findViewById(R.id.card_WineType);
        TextView desc = wine_cardview.findViewById(R.id.card_WineDesc);

        name.setText(WineName);
        type.setText(WineType);
        desc.setText(WineDesc);

        if (name.getText().toString().isEmpty() || type.getText().toString().isEmpty() || desc.getText().toString().isEmpty())
        {
            Log.d("Data was empty: (", name.getText().toString() + ") (" + type.getText().toString() + "( )" + desc.getText().toString() + ")");
        }
        else
        {
            Log.d("Output: ", name.getText().toString() + " " + type.getText().toString() + " " + desc.getText().toString());

            layout.addView(wine_cardview);
        }

    }
}