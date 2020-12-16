package com.example.drivingschool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class myRateadapter extends FirebaseRecyclerAdapter<UserHelperClassRate, myRateadapter.myviewholder> {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    FirebaseUser firebaseUser;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public myRateadapter(@NonNull FirebaseRecyclerOptions<UserHelperClassRate> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myRateadapter.myviewholder holder, int position, @NonNull UserHelperClassRate model) {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("feedback");

        holder.rname.setText("Name: " + model.getName());
        holder.rmail.setText("Mail: " + model.getMail());
        holder.rmessage.setText("Message: " + model.getMessage());
        holder.rtype.setText("Type: " + model.getType());
        holder.rlevel.setText("Level: " + model.getLevel());

    }

    @NonNull
    @Override
    public myRateadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_rate,parent,false);
        return new myRateadapter.myviewholder(view);
    }

    public void delTr(int position){
        FirebaseDatabase.getInstance().getReference().child("feedback").child(getRef(position).getKey()).removeValue();
    }

    static class myviewholder extends RecyclerView.ViewHolder{
        TextView rname, rmail, rmessage, rtype, rlevel;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            rname = (TextView) itemView.findViewById(R.id.r_name);
            rmail = (TextView) itemView.findViewById(R.id.r_mail);
            rmessage = (TextView) itemView.findViewById(R.id.r_message);
            rtype = (TextView) itemView.findViewById(R.id.r_type);
            rlevel = (TextView) itemView.findViewById(R.id.r_level);
        }
    }

}

