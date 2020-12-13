package com.example.drivingschool;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;

public class myadapterSchedule extends FirebaseRecyclerAdapter<UserHelperClassSchedule, myadapterSchedule.myviewholderSchedule> {

    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    double i;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public myadapterSchedule(@NonNull FirebaseRecyclerOptions<UserHelperClassSchedule> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myadapterSchedule.myviewholderSchedule holder, final int position, @NonNull UserHelperClassSchedule model) {
        rootNode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("schedule");

        String name = model.getName().toString();

//        Log.d("TAG111", "onclick:" + model.getC_name());
        holder.c_name0.setText(model.getName());
        holder.t_name0.setText(model.getTrainer());
        holder.car_name0.setText(model.getCar());
        holder.datee0.setText(model.getDate());
        holder.timee0.setText(model.getTime());
        holder.attended.setText(model.getTotalAttended());

        holder.present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                holder.attended.setText(String.valueOf(i));
                String userID = firebaseUser.getUid();  //
                databaseReference = rootNode.getReference("schedule"); //realtimedb
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("totalAttended", holder.attended.getText());
                databaseReference.child(getRef(position).getKey()).updateChildren(hashMap);

                if (i == 20) {
                    myadapterSchedule.this.delSch(position);
                }
            }
        });

    }

    @NonNull
    @Override
    public myadapterSchedule.myviewholderSchedule onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_layout,parent,false);
        return new myviewholderSchedule(view);
    }

    public void delSch(int position){
        FirebaseDatabase.getInstance().getReference().child("schedule").child(getRef(position).getKey()).removeValue();
    }

    public class myviewholderSchedule extends RecyclerView.ViewHolder {
        TextView c_name0, t_name0, car_name0, datee0, timee0, attended;
        ImageView present;

        public myviewholderSchedule(@NonNull View itemView) {
            super(itemView);
            c_name0 = (TextView) itemView.findViewById(R.id.c_name);
            t_name0 = (TextView) itemView.findViewById(R.id.t_name);
            car_name0 = (TextView) itemView.findViewById(R.id.car_name);
            datee0 = (TextView) itemView.findViewById(R.id.datee);
            timee0 = (TextView) itemView.findViewById(R.id.timee);
            attended = (TextView) itemView.findViewById(R.id.attended);
            present = itemView.findViewById(R.id.present);
        }
    }
}
