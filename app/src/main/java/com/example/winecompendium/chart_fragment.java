package com.example.winecompendium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chart_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chart_fragment extends Fragment {

    private ImageButton btnHelp;
    private EditText txtGoal;
    private EditText txtCollected;
    private AnyChartView myChart;
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

    List<DataEntry> dataEntries;

    private String userID;
    static Integer totalGoal = 0; //accumulated total goal amount
    static Integer totalCollected = 0; //accumulated total collected wines amount
    static Integer remaining; //the remaining number of wines to collect
    static Integer goalWineType; //the goal number of wine types
    static Integer goalSubtype; //the goal number of subtypes
    static Integer goalOrigin; //the goal number of origins
    static Integer goalBottle; //the goal number of bottle types
    static Integer totalWineType; ;//the total number of wine types collected already
    static Integer totalSubtype; //the total number of sub types collected already
    static Integer totalOrigin; //the total number of origins collected already
    static Integer totalBottle; //the total number of bottle types collected already

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public chart_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment chart_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static chart_fragment newInstance(String param1, String param2) {
        chart_fragment fragment = new chart_fragment();
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
        return inflater.inflate(R.layout.fragment_chart_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        //Getting currently logged in user's ID
        userID = fUser.getUid();

        myChart = getView().findViewById(R.id.any_chart_view);
        btnHelp = getView().findViewById(R.id.btnHelp);

        //Ensuring all values reset to 0 to avoid pie chart data to double
        remaining = 0;
        totalCollected = 0;
        totalGoal = 0;
        totalBottle = 0;
        totalOrigin = 0;
        totalSubtype = 0;
        totalWineType = 0;
        goalBottle = 0;
        goalOrigin = 0;
        goalSubtype = 0;
        goalWineType = 0;

        txtGoal = getView().findViewById(R.id.txtTotalGoal);
        txtCollected = getView().findViewById(R.id.txtTotalCollected);
        dataEntries = new ArrayList<>();
        SetUpChart();

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHelpBox();
            }
        });

    }

    /*
    -------------------------------------- Setting up the PIE chart --------------------------------
    */
    private void SetUpChart() {
        Pie pie = AnyChart.pie();

        DatabaseReference ref1;
        DatabaseReference ref2;
        DatabaseReference ref3;
        DatabaseReference ref4;

        //------------------- Retrieving the goal and total amount for wine types ------------------
        ref1 = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("AddGoal_Categories").child("Goals")
                .child("WineType");

        ref1.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                if (snapshot.hasChildren()) {
                   goalWineType = snapshot.child("Goal Num").getValue(Integer.class);
                   totalWineType = snapshot.child("Total Wines").getValue(Integer.class);

                   totalGoal =  totalGoal + goalWineType;
                   totalCollected = totalCollected + totalWineType;
                }
                else
                {
                    totalGoal = totalGoal + 0;
                    totalCollected = totalCollected + 0;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


        //------------------- Retrieving the goal and total amount for sub types -------------------
        ref2 = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("AddGoal_Categories").child("Goals")
                .child("SubType");

        ref2.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                if (snapshot.hasChildren()) {
                    goalSubtype = snapshot.child("Goal Num").getValue(Integer.class);
                    totalSubtype = snapshot.child("Total Wines").getValue(Integer.class);

                    totalGoal = totalGoal + goalSubtype;
                    totalCollected = totalCollected + totalSubtype;
                } else
                {
                    totalGoal = totalGoal + 0;
                    totalCollected = totalCollected + 0;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


        //------------------- Retrieving the goal and total amount for origin ----------------------
        ref3 = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("AddGoal_Categories").child("Goals")
                .child("Origin");

        ref3.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                if (snapshot.hasChildren()) {
                    goalOrigin = snapshot.child("Goal Num").getValue(Integer.class);
                    totalOrigin  = snapshot.child("Total Wines").getValue(Integer.class);

                    totalGoal = totalGoal + goalOrigin;
                    totalCollected = totalCollected + totalOrigin;
                }
                else
                {
                    totalGoal = totalGoal + 0;
                    totalCollected = totalCollected + 0;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


        //------------------- Retrieving the goal and total amount for bottle types ----------------
        ref4 = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("AddGoal_Categories").child("Goals")
                .child("BottleType");

        ref4.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                if (snapshot.hasChildren()) {
                    goalBottle = snapshot.child("Goal Num").getValue(Integer.class);
                    totalBottle  = snapshot.child("Total Wines").getValue(Integer.class);

                    totalGoal = totalGoal + goalBottle;
                    totalCollected = totalCollected + totalBottle;
                }
                else
                {
                    totalGoal = totalGoal + 0;
                    totalCollected = totalCollected + 0;
                }

                remaining = totalGoal - totalCollected;
                /*
                ------------------------------ PIE CHART IMPLEMENTATION ----------------------------
                 */

                //Categories and collected items correspond according to their index
                String[] categories = {"WineType","SubType","Origin","BottleType","Remaining"};
                Integer[] collected = {totalWineType, totalSubtype, totalOrigin, totalBottle, remaining};

                //populating array list used to populate pie chart data
                for (int i=0; i < categories.length; i++) {
                    dataEntries.add(new ValueDataEntry(categories[i],collected[i]));
                }

                //Updating the appearance of the pie chart
                pie.palette(new String[] { "#E5839E", "#CCE8E4", "#6EA8B9", "#C6C6C6","#585858" });
                pie.legend(false);

                //Loading the data
                pie.data(dataEntries);
                myChart.setChart(pie);

                //Setting edit texts
                txtGoal.setText(String.valueOf(totalGoal));
                txtCollected.setText(String.valueOf(totalCollected));

                //----------------------------------------------------------------------------------
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    /*
     Show the help dialogue
    */
    private void ShowHelpBox()
    {
        FragmentManager fm =  getChildFragmentManager();
        analytics_help_fragment help_dialogue = analytics_help_fragment.newInstance("Help");
        help_dialogue.show(fm, "fragment_help");
    }
}