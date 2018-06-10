package com.devmasterteam.meusconvidados.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.devmasterteam.meusconvidados.R;
import com.devmasterteam.meusconvidados.business.GuestBusiness;
import com.devmasterteam.meusconvidados.constants.GuestConstants;
import com.devmasterteam.meusconvidados.entities.GuestEntity;
import com.devmasterteam.meusconvidados.repository.GuestRepository;

public class GuestFormActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private GuestBusiness mGuestBusiness;
    private int mGuestId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_form);

        this.mViewHolder.mEditName = findViewById(R.id.edit_name);
        this.mViewHolder.mRadioNotConfirmed = findViewById(R.id.radio_not_confirmed);
        this.mViewHolder.mRadioPresent = findViewById(R.id.radio_present);
        this.mViewHolder.mRadioAbsent = findViewById(R.id.radio_absent);
        this.mViewHolder.mButtonSave = findViewById(R.id.button_save);

        this.mGuestBusiness = new GuestBusiness(this);

        this.setListeners();

        this.loadDataFromActivity();
    }

    private void loadDataFromActivity() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.mGuestId = bundle.getInt(GuestConstants.BUNDLE_CONSTANTS.GUEST_ID);

            GuestEntity guestEntity = this.mGuestBusiness.load(this.mGuestId);
            this.mViewHolder.mEditName.setText(guestEntity.getName());
            if (guestEntity.getConfirmed() == GuestConstants.CONFIRMATION.PRESENT) {
                this.mViewHolder.mRadioPresent.setChecked(true);
            } else if (guestEntity.getConfirmed() == GuestConstants.CONFIRMATION.ABSENT) {
                this.mViewHolder.mRadioAbsent.setChecked(true);
            } else {
                this.mViewHolder.mRadioNotConfirmed.setChecked(true);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_save) {
            this.handleSave();
        }
    }

    private void setListeners() {
        this.mViewHolder.mButtonSave.setOnClickListener(this);
    }

    private void handleSave() {

        if (!this.validateSave()){
            return;
        }

        GuestEntity guestEntity = new GuestEntity();
        guestEntity.setName(this.mViewHolder.mEditName.getText().toString());
        if (this.mViewHolder.mRadioNotConfirmed.isChecked()) {
            guestEntity.setConfirmed(GuestConstants.CONFIRMATION.NOT_CONFIRMED);
        } else if (this.mViewHolder.mRadioPresent.isChecked()) {
            guestEntity.setConfirmed(GuestConstants.CONFIRMATION.PRESENT);
        } else {
            guestEntity.setConfirmed(GuestConstants.CONFIRMATION.ABSENT);
        }

        if (this.mGuestId == 0) {
            if (mGuestBusiness.insert(guestEntity)) {
                Toast.makeText(this, getString(R.string.salvo_com_sucesso), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.erro_ao_salvar, Toast.LENGTH_LONG).show();
            }
        }
        else {

            guestEntity.setId(this.mGuestId);

            if (mGuestBusiness.update(guestEntity)) {
                Toast.makeText(this, getString(R.string.salvo_com_sucesso), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.erro_ao_salvar, Toast.LENGTH_LONG).show();
            }
        }

        finish();
    }

    private boolean validateSave() {
        if (this.mViewHolder.mEditName.getText().toString().equals("")) {
            this.mViewHolder.mEditName.setError(getString(R.string.nome_obrigatorio));
            return false;
        }
        return true;
    }

    private static class ViewHolder {
        EditText mEditName;
        RadioButton mRadioNotConfirmed;
        RadioButton mRadioPresent;
        RadioButton mRadioAbsent;
        Button mButtonSave;
    }
}
