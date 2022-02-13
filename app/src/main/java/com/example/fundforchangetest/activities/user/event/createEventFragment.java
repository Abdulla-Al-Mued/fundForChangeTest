package com.example.fundforchangetest.activities.user.event;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fundforchangetest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class createEventFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button add;
    addEvent ob ;
    String t1,t2,t3,t4,t5,t6,t7;
    int t8;
    //private Activity rootView;


    public createEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static createEventFragment newInstance(String param1, String param2) {
        createEventFragment fragment = new createEventFragment();
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


        /**/
        /*dbroot = FirebaseFirestore.getInstance();

        add = (Button) getView().findViewById(R.id.submitBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insertdata();
            }
        });*/



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        TextInputLayout txt = (TextInputLayout) view.findViewById(R.id.event_Name);
        TextInputLayout txt2 = (TextInputLayout) view.findViewById(R.id.eventDescriptionField);
        TextInputLayout txt3 = (TextInputLayout) view.findViewById(R.id.eventLocation);
        TextInputLayout txt4 = (TextInputLayout) view.findViewById(R.id.eventDonationGoal);
        TextInputLayout txt5 = (TextInputLayout) view.findViewById(R.id.createEventemailField);
        TextInputLayout txt6 = (TextInputLayout) view.findViewById(R.id.createEventPhoneNumber);
        TextInputLayout txt7 = (TextInputLayout) view.findViewById(R.id.createEventNid);

        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



        add = (Button) getView().findViewById(R.id.submitBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                t1 = txt.getEditText().getText().toString().trim();
                t2 = txt2.getEditText().getText().toString().trim();
                t3 = txt3.getEditText().getText().toString().trim();
                t4 = txt4.getEditText().getText().toString().trim();
                t5 = txt5.getEditText().getText().toString().trim();
                t6 = txt6.getEditText().getText().toString().trim();
                t7 = txt7.getEditText().getText().toString().trim();
                t8 = Integer.parseInt(t4);


                /*if(t1.isEmpty()){
                    txt.getEditText().setError("Field Cannot Be empty");
                }
                else if(t2.isEmpty()){
                    txt2.getEditText().setError("Field Cannot Be empty");
                }
                else if(t3.isEmpty()){
                    txt3.getEditText().setError("Field Cannot Be empty");
                }
                else if(t4.isEmpty()){
                    txt4.getEditText().setError("Field Cannot Be empty");
                }
                else if(t5.isEmpty() || t5.matches(checkEmail)){
                    if(t5.isEmpty())
                        txt5.getEditText().setError("Field Cannot Be empty");
                    else
                        txt5.getEditText().setError("Enter a Valid email");
                }
                else if(t6.isEmpty() || t6.length()!=11){
                    if(t6.isEmpty())
                        txt6.getEditText().setError("Field Cannot Be empty");
                    else
                        txt6.getEditText().setError("Invalid number");
                }
                else if(t7.isEmpty()){
                    txt7.getEditText().setError("Field Cannot Be empty");
                }
                else{

                }*/

                ob = new addEvent(t1,t2,t3,t8,t5,t5,t7,txt,txt2,txt3,txt4,txt5,txt6,txt7);

                ob.insert();
                txt.getEditText().setText("");
                txt2.getEditText().setText("");
                txt3.getEditText().setText("");
                txt4.getEditText().setText("");
                txt5.getEditText().setText("");
                txt6.getEditText().setText("");
                txt7.getEditText().setText("");
                Toast.makeText(getContext(), "Your Response is recorded ", Toast.LENGTH_SHORT).show();

            }
        });


    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        //insertdata();

        return view;
    }


    public void insertdata(){

        Map<String, String> items = new HashMap<>();


        /*items.put("name",t1.getText().toString().trim());
        items.put("description",t2.getText().toString().trim());
        items.put("location",t3.getText().toString().trim());
        items.put("donation goal",t4.getText().toString().trim());
        items.put("email",t5.getText().toString().trim());
        items.put("phone",t6.getText().toString().trim());
        items.put("NID",t7.getText().toString().trim());
        items.put("status","pending");


        dbroot.collection("event").add(items)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        //Toast.makeText(getContext(), "Created Successfully", Toast.LENGTH_SHORT).show();
                        t1.setText("");
                        t2.setText("");
                        t3.setText("");
                        t4.setText("");
                        t5.setText("");
                        t6.setText("");
                        t7.setText("");

                        Toast.makeText(getContext(), "Created Successfully", Toast.LENGTH_SHORT).show();
                    }
                });*/

    }
}