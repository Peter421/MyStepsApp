package com.example.mystepsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TableAdapter  extends RecyclerView.Adapter<TableAdapter.TableViewHolder>{

    ArrayList<PositionModel> mlist;
    Context context;

    public TableAdapter(Context context, ArrayList<PositionModel> mlist){
        this.context=context;
        this.mlist=mlist;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.position_card,parent,false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableAdapter.TableViewHolder holder, int position) {

        PositionModel model = mlist.get(position);
        holder.position.setText(model.getPosition());
        holder.username.setText(model.getUsername());
        holder.days.setText(model.getDays());
        holder.dailysteps.setText(model.getDailysteps());
        holder.totalsteps.setText(model.getTotalsteps());


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder{
        TextView position,username,days,dailysteps,totalsteps;


        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.position_text);
            username = itemView.findViewById(R.id.username2_text);
            days = itemView.findViewById(R.id.days_text);
            dailysteps = itemView.findViewById(R.id.dailystep_text);
            totalsteps = itemView.findViewById(R.id.totalstep_text);
        }
    }
}
