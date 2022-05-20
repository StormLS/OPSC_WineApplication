package com.example.winecompendium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

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

public class MyCollections extends AppCompatActivity {

    private Button btnAllWines;
    private Button btnAddWines;
    private Button btnFavourites;
    private Button btnSelected;
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

        dashboard dash = new dashboard();
        SetNavDrawerName(dash._name);

        btnAllWines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allwines_fragment frag1 = new allwines_fragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_layout,frag1, frag1.getTag()).commit();
                ButtonSelected(btnAllWines);
            }
        });

        btnAddWines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addwines_fragment frag2 = new addwines_fragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_layout,frag2, frag2.getTag()).commit();
            }
        });

        btnFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favourites_fragment frag3 = new favourites_fragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_layout,frag3, frag3.getTag()).commit();
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



    //Updating the appearance of the button that is selected
    public void ButtonSelected(Button selectedBtn) {

        //background colour
        selectedBtn.setBackgroundColor(selectedBtn.getContext().getResources().getColor(R.color.customColourFour));

        /*
         Button stroke appearance
         */
        int dpSize =  2;
        DisplayMetrics dm = getResources().getDisplayMetrics() ;
        float strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpSize, dm);
        ShapeDrawable shapedrawable = new ShapeDrawable();
        shapedrawable.setShape(new RectShape());
        shapedrawable.getPaint().setColor(getResources().getColor(R.color.selectedBtnStroke));
        shapedrawable.getPaint().setStrokeWidth(0f);
        shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
      //  selectedBtn.setBackground(shapedrawable);
        selectedBtn.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);

        //Text colour
        selectedBtn.setTextColor(getResources().getColor(R.color.selectedBtnText));

    }




}