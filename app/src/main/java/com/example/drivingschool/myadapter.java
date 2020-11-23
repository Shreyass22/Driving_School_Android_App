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

public class myadapter extends FirebaseRecyclerAdapter<UserHelperClass, myadapter.myviewholder> {
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
    public myadapter(@NonNull FirebaseRecyclerOptions<UserHelperClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull UserHelperClass model) {
        storageReference = FirebaseStorage.getInstance().getReference().child("profile_images");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("trainer").child(firebaseUser.getUid());

        holder.nametext.setText(model.getName());
        holder.phonetext.setText(model.getPhone());
        holder.emailtext.setText(model.getEmail());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    for(DataSnapshot snap2 : snap.getChildren()){
                        if(snap2.getKey().equals(firebaseAuth.getCurrentUser().getUid())){
                            if (snap2.exists() && snap2.getChildrenCount() > 0) {
                                if (snap2.hasChild("image")) {
                                    String image = snap2.child("image").getValue().toString();
                                    Glide.with(holder.img1.getContext()).load(image).into(holder.img1);
                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        CircleImageView img1;
        TextView nametext, phonetext, emailtext;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img1 = (CircleImageView) itemView.findViewById(R.id.img1);
            nametext = (TextView) itemView.findViewById(R.id.nametext);
            phonetext = (TextView) itemView.findViewById(R.id.phonetext);
            emailtext = (TextView) itemView.findViewById(R.id.emailtext);
        }
    }

}
