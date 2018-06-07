package com.devmasterteam.meusconvidados.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devmasterteam.meusconvidados.R;
import com.devmasterteam.meusconvidados.entities.GuestEntity;
import com.devmasterteam.meusconvidados.viewholder.GuestViewHolder;

import java.util.List;

public class GuestListAdapter extends RecyclerView.Adapter<GuestViewHolder>{

    private List<GuestEntity> mGuestEntityList;

    public GuestListAdapter(List<GuestEntity> guestEntityList) {
        this.mGuestEntityList = guestEntityList;
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View carView = layoutInflater.inflate(R.layout.row_guest_list, parent, false);

        return new GuestViewHolder(carView);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        GuestEntity guestEntity = this.mGuestEntityList.get(position);
        holder.bindData(guestEntity);
    }

    @Override
    public int getItemCount() {
        return this.mGuestEntityList.size();
    }
}
