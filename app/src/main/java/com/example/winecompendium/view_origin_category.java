package com.example.winecompendium;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link view_origin_category#newInstance} factory method to
 * create an instance of this fragment.
 */
public class view_origin_category extends Fragment{

    private GridLayout layout;


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

        layout = getView().findViewById(R.id.container);
        AddCard("Italy");
        AddCard("UK");
    }

    private void AddCard(String originName) {

        View cardView = getLayoutInflater().inflate(R.layout.card_view_cat_origin, null);
        TextView title = cardView.findViewById(R.id.txtOriginTitle);

        title.setText(originName);

        layout.addView(cardView);
    }
}