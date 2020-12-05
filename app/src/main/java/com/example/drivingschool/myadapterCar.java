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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapterCar extends FirebaseRecyclerAdapter<UserHelperClassCar, myadapterCar.myviewholder> {
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
    public myadapterCar(@NonNull FirebaseRecyclerOptions<UserHelperClassCar> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull UserHelperClassCar model) {
        storageReference = FirebaseStorage.getInstance().getReference().child("profile_images");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("car").child(firebaseUser.getUid());

        holder.nametext.setText(model.getName());
        holder.modeltext.setText(model.getModel());
        holder.companytext.setText(model.getCompany());
        holder.colortext.setText(model.getColor());
        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    if (snapshot.hasChild("image")) {
                        String image = snapshot.child("image").getValue().toString();
                        Glide.with(holder.img5.getContext()).load(image).into(holder.img5);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void delCr(int position){
        FirebaseDatabase.getInstance().getReference().child("car").child(getRef(position).getKey()).removeValue();
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowcar,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        CircleImageView img5;
        TextView nametext, modeltext, companytext, colortext;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img5 = (CircleImageView) itemView.findViewById(R.id.img5);
            nametext = (TextView) itemView.findViewById(R.id.nametext5);
            modeltext = (TextView) itemView.findViewById(R.id.modeltext5);
            companytext = (TextView) itemView.findViewById(R.id.companytext5);
            colortext = (TextView) itemView.findViewById(R.id.colortext5);
        }
    }

}
