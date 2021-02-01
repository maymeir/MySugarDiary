package com.example.mysugardiary;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import static com.example.mysugardiary.R.color.colorPrimary;
import static com.example.mysugardiary.R.color.green;


public class SugarAdapter extends RecyclerView.Adapter<SugarAdapter.SugarViewHolder> {


    private List<Sugar> sugars;

    public SugarAdapter(List<Sugar> sugars) {
        this.sugars = sugars;
    }


    interface SugarsListener {
        void onSugarClicked(int position, View view);


    }

    SugarsListener listener;

    public void setListener(SugarsListener listener) {
        this.listener = listener;
    }

    public class SugarViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView dateTv;
        CardView cardView;
        TextView averageTv;


        public SugarViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.sugar_image);
            dateTv = itemView.findViewById(R.id.sugar_date);
            cardView = itemView.findViewById(R.id.card_v);
            averageTv=itemView.findViewById(R.id.sugar_average);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null)
                        listener.onSugarClicked(getAdapterPosition(), view);
                }
            });
        }
    }

    @Override
    public SugarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sugar_layout, parent, false);
        SugarViewHolder holder = new SugarViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SugarViewHolder holder, int position) {
        Sugar sugar = sugars.get(position);
        holder.imageView.setImageBitmap(sugar.getPhoto());
        holder.dateTv.setText(sugar.getDate());
         int sum=(int) ((sugar.getSugarN() + sugar.getSugarM())/2);
        holder.averageTv.setText(sum+"");
        if (sum < 200) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#D3FFD5"));
        }
        if (sum>= 200 && sum < 500) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FF9999"));
        }
        if (sum >= 500) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FD5050"));
        }


    }

    @Override
    public int getItemCount() {
        return sugars.size();
    }

    public void moveItem(int oldP,int newP) {

        Sugar sugar = sugars.get(oldP);
        sugars.remove(oldP);
        sugars.add(newP, sugar);
       notifyItemMoved(oldP, newP);
    }
}

