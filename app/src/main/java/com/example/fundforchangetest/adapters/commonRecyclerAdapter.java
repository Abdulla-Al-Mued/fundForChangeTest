package com.example.fundforchangetest.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fundforchangetest.Models.Model;
import com.example.fundforchangetest.R;
import com.example.fundforchangetest.activities.detailsActivity;

import java.util.List;

public class commonRecyclerAdapter extends RecyclerView.Adapter<commonRecyclerAdapter.myViewHolder>{

    List<Model> datalist;
    private Context mCtx;
    private List<String> IDs;

    public commonRecyclerAdapter(Context mCtx, List<Model> datalist) {
        this.datalist = datalist;
        this.mCtx = mCtx;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.t1.setText(datalist.get(position).getName());
        holder.t2.setText(datalist.get(position).getDescription());
        holder.goalprogress.setMax(datalist.get(position).getGoal());
        holder.goalprogress.setProgress(datalist.get(position).getDonated());
        /*holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.t1.getContext(),detailsActivity.class);
                intent.putExtra("uname",datalist.get(position).getName());
                intent.putExtra("uemail",datalist.get(position).getEmail());


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.t1.getContext().startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView t1,t2;
        ProgressBar goalprogress;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.txt1);
            t2 = itemView.findViewById((R.id.txt2));
            //cv = itemView.findViewById(R.id.click);
            goalprogress = (ProgressBar) itemView.findViewById(R.id.goalprogress);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Model product = datalist.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, detailsActivity.class);
            intent.putExtra("product", product);
            mCtx.startActivity(intent);
        }
    }
}
