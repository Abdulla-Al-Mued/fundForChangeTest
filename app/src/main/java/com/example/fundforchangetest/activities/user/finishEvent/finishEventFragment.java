package com.example.fundforchangetest.activities.user.finishEvent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fundforchangetest.Models.finishEventModel;
import com.example.fundforchangetest.Models.pendingModel;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.eventRequest.pendingEventController;
import com.example.fundforchangetest.adapters.finishEventAdapter;
import com.example.fundforchangetest.adapters.pendingEventsAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link finishEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class finishEventFragment extends Fragment {




    RecyclerView recyclerView;
    List<finishEventModel> datalist;
    finishEventAdapter adapter;





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public finishEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment finishEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static finishEventFragment newInstance(String param1, String param2) {
        finishEventFragment fragment = new finishEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finish_event, container, false);
        recyclerView = view.findViewById(R.id.recview2);
        //Toast.makeText(getActivity(), "No problem", Toast.LENGTH_SHORT).show();
        finishEventController ob = new finishEventController(adapter,recyclerView,datalist,getContext());
        ob.showItems();


        return view;
    }
}