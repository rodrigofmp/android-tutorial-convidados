package com.devmasterteam.meusconvidados.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devmasterteam.meusconvidados.R;
import com.devmasterteam.meusconvidados.adapter.GuestListAdapter;
import com.devmasterteam.meusconvidados.business.GuestBusiness;
import com.devmasterteam.meusconvidados.constants.GuestConstants;
import com.devmasterteam.meusconvidados.entities.GuestEntity;
import com.devmasterteam.meusconvidados.listener.OnGuestListenerInteractionListener;

import java.util.List;


public class PresentFragment extends Fragment {

    private ViewHolder mViewHolder = new ViewHolder();
    private GuestBusiness mGuestBusiness;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_present, container, false);

        Context context = view.getContext();

        OnGuestListenerInteractionListener listener = new OnGuestListenerInteractionListener() {
            @Override
            public void onListClick(int id) {
                // Abrir activity do formulário
                Bundle bundle = new Bundle();
                bundle.putInt(GuestConstants.BUNDLE_CONSTANTS.GUEST_ID, id);

                Intent intent = new Intent(getContext(), GuestFormActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int id) {

            }
        };

        this.mGuestBusiness = new GuestBusiness(context);
        List<GuestEntity> guestEntityList = this.mGuestBusiness.getPresents();

        // Obter um recycler
        this.mViewHolder.mRecyclerPresent = view.findViewById(R.id.recycler_present);

        // Definir um adapter
        GuestListAdapter guestListAdapter = new GuestListAdapter(guestEntityList, listener);
        this.mViewHolder.mRecyclerPresent.setAdapter(guestListAdapter);

        // Definir um layout
        this.mViewHolder.mRecyclerPresent.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

    private static class ViewHolder {
        RecyclerView mRecyclerPresent;
    }
}
