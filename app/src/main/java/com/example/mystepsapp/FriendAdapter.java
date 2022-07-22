package com.example.mystepsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.mystepsapp.Singleton.addFriend;
import static com.example.mystepsapp.Singleton.displayfriendt;
import static com.example.mystepsapp.Singleton.setCurrFriend;
import static com.example.mystepsapp.Singleton.unaddFriend;
import static com.example.mystepsapp.Singleton.yourRef;

public class FriendAdapter  extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    ArrayList<FriendModel> mlist;
    Context context;

    public FriendAdapter(Context context, ArrayList<FriendModel> mlist){
        this.context=context;
        this.mlist=mlist;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_card,parent,false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        FriendModel model = mlist.get(position);
        holder.username.setText(model.getUsername());
        holder.model = model;

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        FriendModel model;
        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.friend_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrFriend(model.getUsername());
                    displayfriendt();
                    //create boolean and set to true
                    //startActivity(new Intent(getApplicationContext(),LeagueTable.class));
                   // finish();

                }
            });

            itemView.findViewById(R.id.removefriendbtn).setOnClickListener(new View.OnClickListener() {
                Context c;
                @Override
                public void onClick(View v) {
                    //       Toast.makeText(c.getApplicationContext(),model.getUsername() + " added as friend", Toast.LENGTH_SHORT).show();
                    unaddFriend(model.getUsername());
                    // getNewFriend(model.getUsername());
                    //notifyAddFriend_t();
                }
            });
        }
    }
}
