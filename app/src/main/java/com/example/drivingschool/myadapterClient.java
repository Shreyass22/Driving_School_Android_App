package com.example.drivingschool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapterClient extends FirebaseRecyclerAdapter<UserHelperClass, myadapterClient.myviewholder> {
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
    public myadapterClient(@NonNull FirebaseRecyclerOptions<UserHelperClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myadapterClient.myviewholder holder, int position, @NonNull UserHelperClass model) {
        storageReference = FirebaseStorage.getInstance().getReference().child("profile_images");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());

        holder.nametext0.setText(model.getName());
        holder.phonetext0.setText(model.getPhone());
        holder.emailtext0.setText(model.getEmail());
        holder.typetext0.setText(model.getType());
        Glide.with(holder.img1.getContext()).load(model.getImage()).into(holder.img1);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
//                    if (snapshot.hasChild("image")) {
//                        String image = snapshot.child("image").getValue().toString();
//                        Glide.with(holder.img1.getContext()).load(image).into(holder.img1);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    public void delCl(int position){
        FirebaseDatabase.getInstance().getReference().child("users").child(getRef(position).getKey()).removeValue();
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowclient,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        CircleImageView img1;
        TextView nametext0, phonetext0, emailtext0, typetext0;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img1 = (CircleImageView) itemView.findViewById(R.id.img7);
            nametext0 = (TextView) itemView.findViewById(R.id.nametext7);
            phonetext0 = (TextView) itemView.findViewById(R.id.phonetext7);
            emailtext0 = (TextView) itemView.findViewById(R.id.emailtext7);
            typetext0 = (TextView) itemView.findViewById(R.id.typetext7);
        }
    }

}
