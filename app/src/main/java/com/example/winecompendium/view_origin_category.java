package com.example.winecompendium;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link view_origin_category#newInstance} factory method to
 * create an instance of this fragment.
 */
public class view_origin_category extends Fragment{

    private GridLayout layout;
    private String userID;
    private EditText txtItems;
    private Integer tot;
    private ImageButton btnAdd;
    public static String _heading = "Origin";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public view_origin_category() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static view_origin_category newInstance(String param1, String param2) {
        view_origin_category fragment = new view_origin_category();
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
        return inflater.inflate(R.layout.fragment_view_origin_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        userID = fUser.getUid();

        layout = getView().findViewById(R.id.container);
        txtItems = getView().findViewById(R.id.edtNum3);
        btnAdd = getView().findViewById(R.id.btnAddOrigin);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAddOriginItemDialogue();
            }
        });

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
        Query query = dataRef.child("Users").child(userID).child("Categories").child("Origin");
        ArrayList<String> originList = new ArrayList<>();

        //check if current user is logged in
        if (fUser != null) {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        for(DataSnapshot item : snapshot.getChildren())
                        {
                            //Retrieving the wine type category names
                            originList.add(item.getValue().toString());

                            //Removes any square brackets from the array
                            String origin = originList.toString().replace("[", "").
                                    replace("]", "");

                            //Display the card with the retrieved wine type name
                            AddCard(origin);
                            originList.clear();

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
    private void AddCard(String originName) {

        View cardView = getLayoutInflater().inflate(R.layout.card_view_cat_origin, null);
        TextView title = cardView.findViewById(R.id.txtOriginTitle);

        title.setText(originName);

        layout.addView(cardView);
    }
    //----------------------------------------------------------------------------------------------

    /*
    ---------------------Retrieve the total number of items in the category-------------------------
     */
    private void LoadNumItems() {

        tot = 0;

        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("Categories").child("Origin");

        //List temporarily holds the items in the category
        Set<String> CatList = new HashSet<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item: snapshot.getChildren()) {
                    CatList.add(item.getValue().toString());
                }

                tot = CatList.size();
                txtItems.setText(String.valueOf(tot));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    //----------------------------------------------------------------------------------------------

    /*
   -----------------Show the add new item to bottle type dialogue box-------------------------------
   */
    private void ShowAddOriginItemDialogue()
    {
        FragmentManager fm =  getChildFragmentManager();
        add_wines_add_origin_dialogue addItemDialogue = add_wines_add_origin_dialogue.newInstance("Category Item");
        addItemDialogue.show(fm, "fragment_add_item");
    }
    //----------------------------------------------------------------------------------------------

}