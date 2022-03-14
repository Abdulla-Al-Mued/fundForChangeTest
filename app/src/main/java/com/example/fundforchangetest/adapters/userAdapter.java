package com.example.fundforchangetest.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fundforchangetest.Models.userModel;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.users.userDetails;

import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.myViewHolder> {



    List<userModel> datalist;
    private Context mCtx;


    public userAdapter(List<userModel> datalist, Context mCtx) {
        this.datalist = datalist;
        this.mCtx = mCtx;
    }


    @NonNull
    @Override
    public userAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_single_row,parent,false);

        return new userAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter.myViewHolder holder, int position) {

        holder.t1.setText(datalist.get(position).getName());
        holder.t2.setText(datalist.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView t1,t2;


        public myViewHolder(@NonNull View itemView)  {
            super(itemView);

            t1 = itemView.findViewById(R.id.txt3);
            t2 = itemView.findViewById((R.id.txt4));


            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            userModel product = datalist.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, userDetails.class);
            intent.putExtra("product", product);
            mCtx.startActivity(intent);
        }
    }


}
