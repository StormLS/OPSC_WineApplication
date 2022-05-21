package com.example.winecompendium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MyCollections extends AppCompatActivity {

    private Button btnAllWines;
    private Button btnAddWines;
    private Button btnFavourites;
    private TextView txtNavName;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collections);

        btnAllWines = findViewById(R.id.btn_allwines_1);
        btnAddWines = findViewById(R.id.btn_addwines_1);
        btnFavourites = findViewById(R.id.btn_favs_1);
        drawerLayout = findViewById(R.id.drawer_layout);
        txtNavName = findViewById(R.id.txtNavUser);

        //Updating the nav drawer first name
        dashboard dash = new dashboard();
        SetNavDrawerName(dash._name);

        /*
         //Display the all wines fragment on create
         */
        allwines_fragment frag1 = new allwines_fragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_layout,frag1, frag1.getTag()).commit();
        //update appearance of button
        ButtonSelected((MaterialButton) btnAllWines);

        /*
         ------------------------------------Navigation---------------------------------------------
         */
        btnAllWines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allwines_fragment frag1 = new allwines_fragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_layout,frag1, frag1.getTag()).commit();

                //update appearance of button
                ButtonSelected((MaterialButton) btnAllWines);
                //return other buttons to normal unselected state
                ButtonUnselected((MaterialButton) btnAddWines, (MaterialButton) btnFavourites);
            }
        });

        btnAddWines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addwines_fragment frag2 = new addwines_fragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_layout,frag2, frag2.getTag()).commit();

                //update appearance of button
                ButtonSelected((MaterialButton) btnAddWines);
                //return other buttons to normal unselected state
                ButtonUnselected((MaterialButton) btnAllWines, (MaterialButton) btnFavourites);
            }
        });

        btnFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favourites_fragment frag3 = new favourites_fragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_layout,frag3, frag3.getTag()).commit();

                //update appearance of button
                ButtonSelected((MaterialButton) btnFavourites);
                //return other buttons to normal unselected state
                ButtonUnselected((MaterialButton) btnAddWines, (MaterialButton) btnAllWines);
            }
        });
        //------------------------------------------------------------------------------------------

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
        recreate();
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
    public void ButtonUnselected(MaterialButton unSelectedBtn1, MaterialButton unSelectedBtn2) {

        /*
        Button 1
         */
        //background colour
        unSelectedBtn1.setBackgroundColor(unSelectedBtn1.getContext().getResources().getColor(R.color.customColourFive));
        //Button stroke appearance
        unSelectedBtn1.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.unselectedBtnStroke)));
        //Text colour
        unSelectedBtn1.setTextColor(getResources().getColor(R.color.unselectedBtnText));

        /*
        Button 2
         */
        //background colour
        unSelectedBtn2.setBackgroundColor(unSelectedBtn2.getContext().getResources().getColor(R.color.customColourFive));
        //Button stroke appearance
        unSelectedBtn2.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.unselectedBtnStroke)));
        //Text colour
        unSelectedBtn2.setTextColor(getResources().getColor(R.color.unselectedBtnText));


    }
    //----------------------------------------------------------------------------------------------



}