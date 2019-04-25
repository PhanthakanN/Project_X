package com.project.caloriehealthty7.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.caloriehealthty7.HistoryActivity;
import com.project.caloriehealthty7.MainActivity;
import com.project.caloriehealthty7.Model.Menu;
import com.project.caloriehealthty7.R;

import java.util.HashMap;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private Context mContext;
    private List<Menu> mData ;

    public MenuAdapter(Context mContext, List<Menu> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    FirebaseAuth mAuth;
    FirebaseUser curUser;
    DatabaseReference reference;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_menu, parent, false);
        return new MenuAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final Menu menu = mData.get(position);

        holder.name.setText(menu.getFoodname());
        holder.cal.setText(""+menu.getCal());

        holder.date.setText(menu.getDate());

        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();

        holder.rev1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference = FirebaseDatabase.getInstance().getReference("Menus").child(curUser.getUid()).push();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Menu menuu = new Menu(menu.getFoodname(), menu.getId(), menu.getCal(), menu.getDate(), menu.getTime());
                        addMenu(menuu);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent intent = new Intent(mContext, HistoryActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("date", menu.getDate());
                mContext.startActivity(intent);
            }
        });

    }

    private void addMenu(Menu menu) {

        // get post unique ID and upadte post key
        String key = reference.getKey();
        menu.setPostkey(key);


        // add post data to firebase database

        reference.setValue(menu).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rev1;
        TextView name;
        TextView cal;
        TextView date;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            cal = itemView.findViewById(R.id.cal);
            date = itemView.findViewById(R.id.date);

            rev1 = itemView.findViewById(R.id.rev1);

        }
    }

}
