package com.example.diabetesnote.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabetesnote.DataModel.Information;
import com.example.diabetesnote.R;
import com.example.diabetesnote.UI.Edit;

import java.util.List;

public class ShowRecyclerAdapter extends RecyclerView.Adapter<ShowRecyclerAdapter.MyViewHolder> {

    private List<Information> listItems;
    private Context context;



    public ShowRecyclerAdapter(List<Information> listItems , Context context) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ShowRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout_show , parent , false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowRecyclerAdapter.MyViewHolder holder, int position) {
        final Information currentInfo = listItems.get(position);
        String amount = String.valueOf(currentInfo.getAmount());
        String date =currentInfo.getDate().substring(2);
        holder.amountText.setText(amount);
        holder.dateText.setText(date);
        holder.dayText.setText(currentInfo.getDayOfWeek());
        holder.conditionText.setText(currentInfo.getCondition());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , Edit.class);
                intent.putExtra("Id" , currentInfo.getId());
                context.startActivity(intent);
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView amountText;
        public TextView dayText;
        public TextView conditionText;
        public TextView dateText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            amountText = itemView.findViewById(R.id.recycler_show_amount);
            dateText = itemView.findViewById(R.id.recycler_show_date);
            dayText = itemView.findViewById(R.id.recycler_show_day);
            conditionText = itemView.findViewById(R.id.recycler_show_condition);
        }
    }

}
