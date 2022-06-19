package com.example.winecompendium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link my_categories_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class my_categories_fragment extends Fragment {

    private ImageButton btnType;
    private  ImageButton btnSubtype;
    private  ImageButton btnOrigin;
    private  ImageButton btnBottleType;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private categories cat = new categories();


    public my_categories_fragment() {
        // Required empty public constructor
    }

    public static my_categories_fragment newInstance(String param1, String param2) {
        my_categories_fragment fragment = new my_categories_fragment();
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
        return inflater.inflate(R.layout.fragment_my_categories_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        btnType = getView().findViewById(R.id.btnViewWineType);
        btnSubtype = getView().findViewById(R.id.btnViewSubtype);
        btnOrigin = getView().findViewById(R.id.btnViewOrigin);
        btnBottleType = getView().findViewById(R.id.btnViewBottleType);


        btnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ShowViewWineTypeDialogue();
            }
        });

        btnSubtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowViewSubtypeDialogue();
            }
        });

        btnOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowViewOriginDialogue();
            }
        });

        btnBottleType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowViewBottleTypeDialogue();
            }
        });

    }

    private void ShowViewWineTypeDialogue() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();

        ft.replace(R.id.fragment_layout, new view_winetype_category(), null);
        ft.addToBackStack(view_winetype_category.class.getName());
        ft.commit();

        AccessButtons();
    }

    private void ShowViewSubtypeDialogue() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();

        ft.replace(R.id.fragment_layout, new view_subtype_category(), null);
        ft.addToBackStack(view_subtype_category.class.getName());
        ft.commit();
        AccessButtons();
    }

    private void ShowViewOriginDialogue() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();

        ft.replace(R.id.fragment_layout, new view_origin_category(), null);
        ft.addToBackStack(view_origin_category.class.getName());
        ft.commit();

        AccessButtons();
    }

    private void ShowViewBottleTypeDialogue() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();

        ft.replace(R.id.fragment_layout, new view_bottletype_category(), null);
        ft.addToBackStack(view_bottletype_category.class.getName());
        ft.commit();

        AccessButtons();
    }


    /*
    ---------- Change the background colours of the menu buttons on categories page ----------------
    */
    private void AccessButtons() {

        ((categories)getActivity()).ButtonSelectedViewCat();
        ((categories)getActivity()).ButtonUnselectedCat();

    }
    //----------------------------------------------------------------------------------------------

}