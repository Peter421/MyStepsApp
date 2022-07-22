package com.example.mystepsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.example.mystepsapp.Singleton.addFriend;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements Filterable {


    ArrayList<UserModel> mlist;
    ArrayList<UserModel> mlistFull;
    Context context;

    public UserAdapter(Context context, ArrayList<UserModel> mlist){
        this.context=context;
        this.mlist=mlist;
       mlistFull = new ArrayList<>();
       // mlistFull.addAll(mlist);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
       return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {

        UserModel model = mlist.get(position);
        mlistFull.add(model);
        holder.username.setText(model.getUsername());
        holder.model = model;
    }
    public static <Model> ArrayList<Model> clearDuplicates(ArrayList<Model> list) {
        Set<Model> temp = new LinkedHashSet<>();
        temp.addAll(list);
        list.clear();
        list.addAll(temp);
        return list;
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    @Override
    public Filter getFilter() {
        return FilterUser;
    }
    private Filter FilterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText=constraint.toString().toLowerCase();
            List<UserModel> temp = new ArrayList<>();
            if(searchText.length()==0 || searchText.isEmpty()){
                ArrayList<UserModel> newList = clearDuplicates(mlistFull);
                temp.addAll(newList);
                //temp.add(new Model("null"));
            }else{
                for(UserModel item:mlist){
                    if(item.getUsername().toLowerCase().contains(searchText)){
                        temp.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = temp;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mlist.clear();
            mlist.addAll((Collection<? extends UserModel>) results.values);
            notifyDataSetChanged();

        }
    };

    public static class UserViewHolder extends RecyclerView.ViewHolder{
TextView username;
Button  addfriend;
UserModel model;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_text);



            itemView.findViewById(R.id.addfriendbtn).setOnClickListener(new View.OnClickListener() {
                Context c;
                @Override
                public void onClick(View v) {
             //       Toast.makeText(c.getApplicationContext(),model.getUsername() + " added as friend", Toast.LENGTH_SHORT).show();
                    addFriend(model.getUsername());
                   // getNewFriend(model.getUsername());
                    //notifyAddFriend_t();
                }
            });

        }
    }
}


