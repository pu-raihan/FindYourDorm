package com.example.fyd;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class CAdapter extends FirebaseRecyclerAdapter<Model, CAdapter.myViewholder> {

    public CAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull myViewholder holder, int position, @NonNull Model model) {
        holder.place.setText(model.getPlace());
        holder.size.setText(model.getSize());
        holder.bedrooms.setText(model.getBedrooms());
        holder.rent.setText(model.getRent());
        Picasso.get().load(model.getImg()).into(holder.img);
        holder.status.setText(model.getStatus());
        final String profile=model.getProfile();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), PublicProfile.class);
                intent1.putExtra("pro",profile);
                v.getContext().startActivity(intent1);
            }
        });
    }


    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_single_cust,parent,false);
        return new myViewholder(view);
    }

    class myViewholder extends RecyclerView.ViewHolder{
        TextView place,size,bedrooms,rent,status;
        ImageView img;
        public myViewholder(@NonNull View itemView) {
            super(itemView);
            place= itemView.findViewById(R.id.place);
            size= itemView.findViewById(R.id.size);
            bedrooms= itemView.findViewById(R.id.bedrooms);
            rent= itemView.findViewById(R.id.rent);
            status= itemView.findViewById(R.id.status);
            img= itemView.findViewById(R.id.img);
        }
    }
}
