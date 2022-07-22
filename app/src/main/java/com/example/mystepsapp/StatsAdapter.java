package com.example.mystepsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.mystepsapp.Singleton.setCurrFriend;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.StatsViewHolder>{

    ArrayList<StatsModel> mlist;
    Context context;

    public StatsAdapter(Context context, ArrayList<StatsModel> mlist){
        this.context=context;
        this.mlist=mlist;
    }

    @NonNull
    @Override
    public StatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fstats_item,parent,false);
        return new StatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsViewHolder holder, int position) {
        StatsModel model = mlist.get(position);
        holder.statistic.setText(model.getStat());

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class StatsViewHolder extends RecyclerView.ViewHolder  {
        TextView statistic;


        public StatsViewHolder(@NonNull View itemView) {
            super(itemView);

            statistic = itemView.findViewById(R.id.stat_text);

        }
    }
}
