package com.example.winecompendium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class dashboard extends AppCompatActivity
{
    //Initialize variables
    DrawerLayout drawerLayout;

    private ImageButton btn_mycollections;
    private ImageButton btn_allcatagories;
    private ImageButton btn_analytics;
    private ImageButton btn_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);

        //----------- findViewByID() & setOnClickListener() here for Dashboard variables -----------
        btn_mycollections = findViewById(R.id.btn_mycollections);
        btn_allcatagories = findViewById(R.id.btn_allcatagories);
        btn_analytics = findViewById(R.id.btn_analytics);
        btn_settings = findViewById(R.id.btn_settings);
        //------------------------------------------------------------------------------------------

        // ------------------------------- Buttons on the Dashboard --------------------------------
        btn_mycollections.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast btn_collections_pressed = Toast.makeText(getApplicationContext(), "'Button Pressed: MyCollections", Toast.LENGTH_SHORT);
                btn_collections_pressed.show();
                //Intent i = new Intent(dashboard.this, myCollections.class);
                //startActivity(i);
            }
        });

        btn_allcatagories.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast btn_all_categories_pressed = Toast.makeText(getApplicationContext(), "Button Pressed: All Categories", Toast.LENGTH_SHORT);
                btn_all_categories_pressed.show();
            }
        });

        btn_analytics.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast btn_analytics_pressed = Toast.makeText(getApplicationContext(), "Button Pressed: Analytics", Toast.LENGTH_SHORT);
                btn_analytics_pressed.show();
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast btn_settings_pressed = Toast.makeText(getApplicationContext(), "Button Pressed: Settings", Toast.LENGTH_SHORT);
                btn_settings_pressed.show();
            }
        });
        //------------------------------------------------------------------------------------------
    }

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
        //Recreate activity
        recreate();
    }
    
    public void ClickMyCollections(View view)
    {
        //Redirect activity to MyCollections
        redirectActivity(this, mycollections.class);
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
        redirectActivity(this, Settings.class);
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
                //Finish activity
                activity.finishAffinity();
                //Exit app
                System.exit(0);
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
            super.onBackPressed();
        }
    }
}