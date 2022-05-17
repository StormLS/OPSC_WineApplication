package com.example.winecompendium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class analytics extends AppCompatActivity
{
    //Initialize variables
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout_);
    }

    //----------------------------------- Drawer Management Code -----------------------------------
    public void ClickMenu(View view)
    {
        //Open the drawer
        dashboard.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view)
    {
        dashboard.closeDrawer(drawerLayout);
    }
    //----------------------------------- Drawer Management Code -----------------------------------

    //--------------------------------- Navigation Management Code ---------------------------------
    public void ClickHome(View view)
    {
        dashboard.redirectActivity(this, dashboard.class);
    }

    public void ClickMyCollections(View view)
    {
        dashboard.redirectActivity(this, mycollections.class);
    }

    public void ClickMyCategories(View view)
    {
        //Redirect activity to MyCategories
        dashboard.redirectActivity(this, categories.class);
    }

    public void ClickMyAnalytics(View view)
    {
        recreate();
    }

    public void ClickContact(View view)
    {
        //Redirect activity to Contacts
        dashboard.redirectActivity(this, contact.class);
    }

    public void ClickFAQ(View view)
    {
        //Redirect activity to FAQ
        dashboard.redirectActivity(this, faq.class);
    }

    public void ClickAboutUs(View view)
    {
        dashboard.redirectActivity(this, AboutUs.class);
    }

    public void ClickSettings(View view)
    {
        //Redirect activity to About Us
        dashboard.redirectActivity(this, settings.class);
    }
    //--------------------------------- Navigation Management Code ---------------------------------

    //------------------------------------ Logging out the User ------------------------------------
    public void ClickLogout(View view)
    {
        //Logs the out of the application
        dashboard.logout(this);
    }
    //------------------------------------ Logging out the User ------------------------------------

    @Override
    protected void onPause()
    {
        super.onPause();
        //Close drawer
        dashboard.closeDrawer(drawerLayout);
    }
}