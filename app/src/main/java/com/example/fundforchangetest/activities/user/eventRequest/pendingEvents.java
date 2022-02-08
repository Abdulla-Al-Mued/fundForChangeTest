package com.example.fundforchangetest.activities.user.eventRequest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.Models.pendingModel;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.adapters.commonRecyclerAdapter;
import com.example.fundforchangetest.adapters.pendingEventsAdapter;

import java.util.List;


public class pendingEvents extends Fragment {

    RecyclerView recyclerView;
    List<pendingModel> datalist;
    pendingEventsAdapter adapter;
    pendingEventController ob;

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
        Toast.makeText(getActivity(), "No problem", Toast.LENGTH_SHORT).show();
        pendingEventController ob = new pendingEventController(adapter,recyclerView,datalist,getContext());
        ob.showItems();
        return view;
    }
}