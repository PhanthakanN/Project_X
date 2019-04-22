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

import com.project.caloriehealthty7.HistoryActivity;
import com.project.caloriehealthty7.MainActivity;
import com.project.caloriehealthty7.Model.Menu;
import com.project.caloriehealthty7.R;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyViewHolder> {

    private Context mContext;
    private List<Menu> mData ;

    public DateAdapter(Context mContext, List<Menu> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_date, parent, false);
        return new DateAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final Menu menu = mData.get(position);

        holder.name.setText(menu.getFoodname());
        holder.cal.setText(""+menu.getCal());
        holder.time.setText(menu.getTime());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView cal;
        TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            cal = itemView.findViewById(R.id.cal);
            time = itemView.findViewById(R.id.time);

        }
    }

}
