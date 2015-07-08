package com.frojas.francisco.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frojas.francisco.pojo.Informacion;

import java.util.Collections;
import java.util.List;

/**
 * Created by Francisco on 23/03/2015.
 */
public class RecyclerDrawerAdapter extends RecyclerView.Adapter<RecyclerDrawerAdapter.MyViewHolder> {

    List<Informacion> data= Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public RecyclerDrawerAdapter(Context context, List<Informacion> data){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.data=data;
    }

    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = inflater.inflate(com.frojas.francisco.activities.R.layout.custom_row, parent, false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Informacion current=data.get(position);
        holder.title.setText(current.getTitle());
        holder.icon.setImageResource(current.getIconId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(com.frojas.francisco.activities.R.id.listText);
            icon= (ImageView) itemView.findViewById(com.frojas.francisco.activities.R.id.listIcon);
        }

    }
}
