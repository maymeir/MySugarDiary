package com.example.mysugardiary;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class ShowSugarActivity extends Activity {
    ArrayList<Sugar> sugars;
    SugarAdapter adapter;
    RecyclerView recyclerView;
    SugarManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_sugar);

        recyclerView = findViewById(R.id.sugar_list);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         manager = SugarManager.getInstance(this);

        adapter = new SugarAdapter(manager.getSugars());
        sugars = manager.getSugars();
        recyclerView.setAdapter(adapter);

        ImageButton addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowSugarActivity.this, NewSugarActivity.class);
                startActivity(intent);
            }
        });

        adapter.setListener(new SugarAdapter.SugarsListener() {
            @Override
            public void onSugarClicked(int position, View view) {
                Intent intent = new Intent(ShowSugarActivity.this, SugarInfoActivity.class);
                intent.putExtra("sugar", position);
                startActivity(intent);

            }

        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createItemTouchHelper());
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

private ItemTouchHelper.Callback createItemTouchHelper(){
    ItemTouchHelper.Callback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            final int fromPosition = viewHolder.getAdapterPosition();
            final int toPosition = target.getAdapterPosition();

            Collections.swap(sugars,fromPosition,toPosition);
            adapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

            final AlertDialog.Builder builder=new AlertDialog.Builder(ShowSugarActivity.this);
            View dialogView=getLayoutInflater().inflate(R.layout.delete_dialog,null);
            builder.setView(dialogView).setTitle("are you sure?").setCancelable(true).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), sugars.size());
                    dialogInterface.cancel();

                }
            });
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    manager.removeSugar(viewHolder.getAdapterPosition());
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    adapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), sugars.size());
                    dialogInterface.cancel();


                }
            }).show();


        }


    };
    return simpleCallback;

}


}
