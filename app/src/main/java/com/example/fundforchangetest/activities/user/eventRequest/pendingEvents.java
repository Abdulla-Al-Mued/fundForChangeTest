package com.example.fundforchangetest.activities.user.eventRequest;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.example.fundforchangetest.Models.pendingModel;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.adapters.pendingEventsAdapter;

import java.util.List;


public class pendingEvents extends Fragment {

    RecyclerView recyclerView;
    List<pendingModel> datalist, backup;
    pendingEventsAdapter adapter;
    EditText searchBox;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public static pendingEvents newInstance(String param1, String param2) {
        pendingEvents fragment = new pendingEvents();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_pending_events, container, false);

        recyclerView = view.findViewById(R.id.rec_view1);
        searchBox = view.findViewById(R.id.search_box);
        //Toast.makeText(getActivity(), "No problem", Toast.LENGTH_SHORT).show();
        pendingEventController ob = new pendingEventController(adapter,recyclerView,datalist,getContext(), backup);
        ob.showItems();

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                ob.processSearch(s.toString().toLowerCase().trim());

            }
        });

        return view;
    }
}