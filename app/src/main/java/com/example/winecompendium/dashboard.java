package com.example.winecompendium;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class dashboard extends AppCompatActivity {

    //Initialise variables

    DrawerLayout drawerLayout;
    private Button btn_mycollections;
    private Button btn_allcatagories;
    private Button btn_analytics;
    private Button btn_settings;
    private TextView txtDashboardUserName;
    private TextView txtNavName;
    private String userID; //currently logged in user's ID
    public static String _name = "User"; //this variable will be accessed from all pages


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //running method that updates the first name of current user on dashboard
        fetchUserFirstName();

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);

        //----------- findViewByID() & setOnClickListener() here for Dashboard variables -----------
        btn_mycollections = findViewById(R.id.btn_mycollections);
        btn_allcatagories = findViewById(R.id.btn_allcatagories);
        btn_analytics = findViewById(R.id.btn_analytics);
        btn_settings = findViewById(R.id.btn_settings);
        txtDashboardUserName = findViewById(R.id.txtHelloUser);
        txtNavName = findViewById(R.id.txtNavUser);
        //------------------------------------------------------------------------------------------

        // ------------------------------- Buttons on the Dashboard --------------------------------
        btn_mycollections.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(dashboard.this, MyCollections.class);
                startActivity(i);
            }
        });

        btn_allcatagories.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(dashboard.this,categories.class);
                startActivity(i);
            }
        });

        btn_analytics.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(dashboard.this,analytics.class);
                startActivity(i);
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               Intent i = new Intent(dashboard.this,settings.class);
               startActivity(i);
            }
        });
        //------------------------------------------------------------------------------------------

    }

    //---------------------------- Getting currently logged in user details ------------------------
    public void fetchUserFirstName() {

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = fUser.getUid();

        //query based on current user id. Finding the first name of current user to display on dashboard
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
        Query query = dataRef.child("Users").child(userID).child("Account").orderByChild("userID").equalTo(userID);

        //check if current user is logged in
        if (fUser != null) {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            //Getting the first name value
                            users accName = ds.getValue(users.class);
                            _name = accName.getFirstName();
                            SetDashboardName(_name);
                            SetNavDrawerName(_name);
                            Log.d("firstname","accName");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("error", error.getMessage());
                }
            });

        } else {
            //No user is logged in
            Toast.makeText(this,"No user is logged in.",Toast.LENGTH_SHORT).show();
        }

    }
    //----------------------------------------------------------------------------------------------


    //---------------------------------method for dashboard display first name ---------------------
    private void SetDashboardName(String _username) {
        String hello = getString(R.string.hello_user,_username);
        txtDashboardUserName.setText(hello);
    }
    //----------------------------------------------------------------------------------------------

    //---------------------------------method for nav drawer display first name --------------------
    public void SetNavDrawerName(String _username) {
        String _fname = getString(R.string.nav_name,_username);
        txtNavName.setText(_fname);
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------- Drawer Management Code -----------------------------------
    public void ClickMenu(View view)
    {
        //Open the drawer
        openDrawer(drawerLayout);
    }
    public static void openDrawer(DrawerLayout drawerLayout)
    {
        //Open the drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view)
    {
        //Closes the drawer
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout)
    {
        //Close drawer layout
        //Check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            //When drawer is open
            //Close Drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    //----------------------------------- Drawer Management Code -----------------------------------

    //--------------------------------- Navigation Management Code ---------------------------------
    public void ClickHome(View view)
    {
        //Redirect activity to home page
        redirectActivity(this, dashboard.class);
    }

    public void ClickMyCollections(View view)
    {
        //Redirect activity to MyCollections
        redirectActivity(this, MyCollections.class);
    }

    public void ClickMyCategories(View view)
    {
        //Redirect activity to MyCategories
        redirectActivity(this, categories.class);
    }

    public void ClickMyAnalytics(View view)
    {
        //Redirect activity to MyAnalytics
        redirectActivity(this, analytics.class);
    }

    public void ClickContact(View view)
    {
        //Redirect activity to Contacts
        redirectActivity(this, contact.class);
    }

    public void ClickFAQ(View view)
    {
        //Redirect activity to FAQ
        redirectActivity(this, faq.class);
    }

    public void ClickAboutUs(View view)
    {
        //Redirect activity to About Us
        redirectActivity(this, AboutUs.class);
    }

    public void ClickSettings(View view)
    {
        //Redirect activity to About Us
        redirectActivity(this, settings.class);
    }

    public void ClickLogout(View view)
    {
        //Logs the out of the application
        logout(this);
    }

    public static void redirectActivity(Activity activity, Class aClass)
    {
        //Initialise Intent
        Intent intent = new Intent(activity, aClass);
        //Set Flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
    //--------------------------------- Navigation Management Code ---------------------------------

    //------------------------------------ Logging out the User ------------------------------------
    public static void logout(Activity activity)
    {
        //Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Set Title
        builder.setTitle("Logout");
        //Set Message
        builder.setMessage("Are you sure you want to logout?");
        //Positive yes button
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int i)
            {
                FirebaseAuth.getInstance().signOut();
                activity.finishAffinity();
                redirectActivity(activity, login.class);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int i)
            {
                //Dismiss dialog
                dialog.dismiss();
            }
        });
        // Show dialog
        builder.show();
    }
    //------------------------------------ Logging out the User ------------------------------------

    @Override
    protected void onPause()
    {
        super.onPause();
        //Close drawer
        closeDrawer(drawerLayout);
    }

    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        {
            logout(this);
        }
    }
}