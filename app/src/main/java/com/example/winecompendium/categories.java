package com.example.winecompendium;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

public class categories extends AppCompatActivity
{
    //Initialize variables
    DrawerLayout drawerLayout;
    private TextView txtNavName;

    private MaterialButton btnCategories;
    private MaterialButton btnViewCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout_);
        txtNavName = findViewById(R.id.txtNavUser);
        btnCategories = findViewById(R.id.btn_categories);
        btnViewCategory = findViewById(R.id.btnShowCategory);

        dashboard dash = new dashboard();
        SetNavDrawerName(dash._name);

        /*
         Display the all categories fragment on create
        */
        my_categories_fragment frag1 = new my_categories_fragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_layout,frag1,frag1.getTag()).commit();
        ButtonSelected(btnCategories);

        //Navigation
        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_categories_fragment frag1 = new my_categories_fragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_layout,frag1,frag1.getTag()).commit();
                ButtonSelected(btnCategories);
                ButtonUnselected(btnViewCategory);
            }
        });

        btnViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_winetype_category frag2 = new view_winetype_category();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_layout,frag2,frag2.getTag()).commit();
                ButtonSelected(btnViewCategory);
                ButtonUnselected(btnCategories);
            }
        });
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
        dashboard.redirectActivity(this, MyCollections.class);
    }

    public void ClickMyCategories(View view)
    {
        recreate();
    }

    public void ClickMyAnalytics(View view)
    {
        //Redirect activity to MyAnalytics
        dashboard.redirectActivity(this, analytics.class);
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

    public void ReplaceFragment() {

        /*
        Display the correct fragment on the fragment layout
         */
        my_categories_fragment myCatFrag = new my_categories_fragment();
       // String fragNum = myCatFrag.ReturnFragmentNumber();
        Toast.makeText(this, myCatFrag.ReturnFragmentNumber(),Toast.LENGTH_SHORT).show();

        /*
        switch (fragNum) {
            case  "1":  {
                view_winetype_category frag = new view_winetype_category();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_layout,frag,frag.getTag()).commit();
            }
            break;
            case "2": {
                view_subtype_category frag = new view_subtype_category();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_layout,frag,frag.getTag()).commit();
            }
            break;
            case "3": {
                view_origin_category frag = new view_origin_category();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_layout,frag,frag.getTag()).commit();
            }
            break;
            case "4": {
                view_bottletype_category frag = new view_bottletype_category();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_layout,frag,frag.getTag()).commit();
            }
            break;
        }*/

        ButtonSelected(btnViewCategory);
        ButtonUnselected(btnCategories);

    }


    //------------------Updating the appearance of the button that is selected----------------------
    public void ButtonSelected(MaterialButton selectedBtn) {

        //background colour
        selectedBtn.setBackgroundColor(selectedBtn.getContext().getResources().getColor(R.color.customColourFour));
        //Button stroke appearance
        selectedBtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.selectedBtnStroke)));
        //Text colour
        selectedBtn.setTextColor(getResources().getColor(R.color.selectedBtnText));

    }
    //----------------------------------------------------------------------------------------------

    //------------------Returning to the appearance of the button that is unselected----------------
    public void ButtonUnselected(MaterialButton unSelectedBtn) {

        //background colour
        unSelectedBtn.setBackgroundColor(unSelectedBtn.getContext().getResources().getColor(R.color.customColourFive));
        //Button stroke appearance
        unSelectedBtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.unselectedBtnStroke)));
        //Text colour
        unSelectedBtn.setTextColor(getResources().getColor(R.color.unselectedBtnText));


    }
    //----------------------------------------------------------------------------------------------
}