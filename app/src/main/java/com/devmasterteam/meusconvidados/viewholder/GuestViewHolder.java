package com.devmasterteam.meusconvidados.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.devmasterteam.meusconvidados.R;

public class GuestViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextName;

    public GuestViewHolder(View itemView) {
        super(itemView);

        this.mTextName = itemView.findViewById(R.id.text_name);
    }
}