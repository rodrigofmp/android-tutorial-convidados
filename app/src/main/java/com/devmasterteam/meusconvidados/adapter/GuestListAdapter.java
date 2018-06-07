package com.devmasterteam.meusconvidados.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devmasterteam.meusconvidados.R;
import com.devmasterteam.meusconvidados.viewholder.GuestViewHolder;

public class GuestListAdapter extends RecyclerView.Adapter<GuestViewHolder>{

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View carView = layoutInflater.inflate(R.layout.row_guest_list, parent, false);

        return new GuestViewHolder(carView);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
