package com.devmasterteam.meusconvidados.viewholder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.devmasterteam.meusconvidados.R;
import com.devmasterteam.meusconvidados.entities.GuestEntity;
import com.devmasterteam.meusconvidados.listener.OnGuestListenerInteractionListener;

public class GuestViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextName;
    private Context mContext;

    public GuestViewHolder(View itemView, Context context) {
        super(itemView);

        this.mTextName = itemView.findViewById(R.id.text_name);
        this.mContext = context;
    }

    public void bindData(final GuestEntity guestEntity, final OnGuestListenerInteractionListener listener) {
        this.mTextName.setText(guestEntity.getName());

        this.mTextName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onListClick(guestEntity.getId());
            }
        });

        this.mTextName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.remocao_de_convidado)
                        .setMessage(R.string.deseja_remover_o_convidado)
                        .setIcon(R.drawable.ic_menu_camera)
                        .setPositiveButton(R.string.resposta_sim, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listener.onDeleteClick(guestEntity.getId());
                            }
                        })
                        .setNeutralButton(R.string.resposta_nao, null)
                        .show();

                return true;
            }
        });
    }
}
