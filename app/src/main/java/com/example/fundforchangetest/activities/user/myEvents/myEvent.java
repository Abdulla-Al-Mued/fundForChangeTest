package com.example.fundforchangetest.activities.user.myEvents;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fundforchangetest.Models.myEventModel;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.finishEvent.finishEventController;
import com.example.fundforchangetest.adapters.myEventAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link myEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myEvent extends Fragment {

    RecyclerView recview;
    List<myEventModel> datalist;
    myEventAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public myEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment myevent.
     */
    // TODO: Rename and change types and number of parameters
    public static myEvent newInstance(String param1, String param2) {
        myEvent fragment = new myEvent();
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
        View view = inflater.inflate(R.layout.fragment_myevent, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("datafile", Context.MODE_PRIVATE);
        String uMail = sp.getString("email","");

        recview = view.findViewById(R.id.my_event_recview);
        myEventController ob = new myEventController(adapter,recview,datalist,getContext(),uMail);
        ob.showItems();


        return view;
    }
}