package com.project.caloriehealthty7.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.caloriehealthty7.Model.Cal;
import com.project.caloriehealthty7.Model.Menu;
import com.project.caloriehealthty7.R;

import java.util.List;

public class CalAdapter extends RecyclerView.Adapter<CalAdapter.MyViewHolder> {

    private Context mContext;
    private List<Cal> mData ;

    public CalAdapter(Context mContext, List<Cal> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_cal, parent, false);
        return new CalAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CalAdapter.MyViewHolder holder, final int position) {

        final Cal cal = mData.get(position);

        Glide.with(mContext).load(cal.getPhoto()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);

        }
    }
}
