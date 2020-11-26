package com.example.drivingschool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ItemViewHolder> {
    Context context;
    ArrayList<dataUser> dataUserArrayList;
    Locale id = new Locale("in", "ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY", id);

    public AdapterItem(Context context, ArrayList<dataUser> dataUserArrayList) {
        this.context = context;
        this.dataUserArrayList = dataUserArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.viewBind(dataUserArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView C_name, T_name, Car_name, datee, timee;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            C_name = itemView.findViewById(R.id.C_name);
            T_name = itemView.findViewById(R.id.T_name);
            Car_name = itemView.findViewById(R.id.Car_name);
            datee = itemView.findViewById(R.id.datee);
            timee = itemView.findViewById(R.id.timee);
        }

        public void viewBind(dataUser dataUser) {
            C_name.setText(dataUser.getC_name());
            T_name.setText(dataUser.getTrr_name());
            Car_name.setText(dataUser.getCarr_name());
            datee.setText(simpleDateFormat.format(dataUser.getDatee()));
            timee.setText(dataUser.getTime());
        }
    }
}
