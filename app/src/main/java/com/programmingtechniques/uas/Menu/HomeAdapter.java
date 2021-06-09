package com.programmingtechniques.uas.Menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.programmingtechniques.uas.R;

public class HomeAdapter extends FirebaseRecyclerAdapter<HomeHelper, HomeAdapter.myViewHolder> {

    public HomeAdapter(@NonNull FirebaseRecyclerOptions<HomeHelper> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull HomeHelper homeHelper) {
        myViewHolder.tvCommands.setText(homeHelper.getCommands());
        myViewHolder.tvDescription.setText(homeHelper.getDescription());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView tvCommands, tvDescription;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCommands = itemView.findViewById(R.id.textPerintah);
            tvDescription = itemView.findViewById(R.id.textDeskripsi);
        }
    }
}
