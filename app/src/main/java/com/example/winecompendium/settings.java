package com.example.winecompendium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class settings extends AppCompatActivity
{
    //Initialize variables
    DrawerLayout drawerLayout;
    private TextView txtNavName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupHyperlink();

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout_);
        txtNavName = findViewById(R.id.txtNavUser);

        dashboard dash = new dashboard();
        SetNavDrawerName(dash._name);
    }

    //---------------------------------method for nav drawer display first name --------------------
    private void SetNavDrawerName(String _username) {
        String _fname = getString(R.string.nav_name,_username);
        txtNavName.setText(_fname);
    }
    //----------------------------------------------------------------------------------------------

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
        //Redirect activity to MyCategories
        dashboard.redirectActivity(this, mycollections.class);
    }

    public void ClickMyCategories(View view)
    {
        //Redirect activity to MyCategories
        dashboard.redirectActivity(this, categories.class);
    }

    public void ClickMyAnalytics(View view)
    {
        //Redirect activity to MyAnalytics
        dashboard.redirectActivity(this, analytics.class);
    }

    public void ClickContact(View view)
    {
        dashboard.redirectActivity(this, contact.class);
    }

    public void ClickFAQ(View view)
    {
        //Redirect activity to FAQ
        dashboard.redirectActivity(this, faq.class);
    }

    public void ClickAboutUs(View view)
    {
        //Redirect activity to About Us
        dashboard.redirectActivity(this, AboutUs.class);
    }

    public void ClickSettings(View view)
    {
        recreate();
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

    private void setupHyperlink() {
        TextView linkTextView = findViewById(R.id.hyperlink);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}