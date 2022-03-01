package com.example.fundforchangetest.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fundforchangetest.Models.finishEventModel;
import com.example.fundforchangetest.Models.pendingModel;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.user.eventRequest.pendingDetails;
import com.example.fundforchangetest.activities.user.finishEvent.finishEventDetails;

import java.util.List;

public class finishEventAdapter extends RecyclerView.Adapter<finishEventAdapter.myViewHolder>{
    List<finishEventModel> datalist;
    private Context mCtx;

    public finishEventAdapter(List<finishEventModel> datalist, Context mCtx) {
        this.datalist = datalist;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finish_event_single_row,parent,false);

        return new finishEventAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.t1.setText(datalist.get(position).getName());
        holder.t2.setText(datalist.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView t1,t2;


        public myViewHolder(@NonNull View itemView)  {
            super(itemView);

            t1 = itemView.findViewById(R.id.txt1);
            t2 = itemView.findViewById((R.id.txt2));


            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            finishEventModel product = datalist.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, finishEventDetails.class);
            intent.putExtra("product", product);
            mCtx.startActivity(intent);
        }
    }
}
