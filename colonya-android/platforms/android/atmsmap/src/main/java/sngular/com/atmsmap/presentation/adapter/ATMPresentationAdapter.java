package sngular.com.atmsmap.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterfork.Bind;
import butterfork.ButterFork;
import sngular.com.atmsmap.B;
import sngular.com.atmsmap.R;
import sngular.com.atmsmap.presentation.model.PresentationATM;
import sngular.com.atmsmap.presentation.view.listener.OnRecyclerItemSelectedListener;

/**
 * Created by luissedano on 20/4/16.
 */
public class ATMPresentationAdapter extends RecyclerView.Adapter<ATMPresentationAdapter.ATMPresentationViewHolder> {

    private List<PresentationATM> mATMList;
    private OnRecyclerItemSelectedListener mListener;

    public ATMPresentationAdapter(List<PresentationATM> list, OnRecyclerItemSelectedListener listener) {
        mATMList = list;
        mListener = listener;
    }


    @Override
    public ATMPresentationAdapter.ATMPresentationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_poi, parent, false);
        return new ATMPresentationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ATMPresentationAdapter.ATMPresentationViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRecyclerATMItemSelected(mATMList.get(position));
            }
        });

        holder.bindATM(mATMList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mATMList != null) {
            return mATMList.size();
        } else {
            return 0;
        }
    }

    protected class ATMPresentationViewHolder extends RecyclerView.ViewHolder {
        @Bind(B.id.linear_comission_view)
        LinearLayout mComisionView;
        @Bind(B.id.comission_number_text)
        TextView mComissionNumber;
        @Bind(B.id.comission_description_text)
        TextView mComissionDesc;
        @Bind(B.id.entity_text)
        TextView mEntityName;
        @Bind(B.id.address_first_text)
        TextView mAddress;
        @Bind(B.id.address_second_text)
        TextView mZipAndCity;
        @Bind(B.id.distance_text)
        TextView mDistanceText;

        public ATMPresentationViewHolder(View itemView) {
            super(itemView);
            ButterFork.bind(this, itemView);
        }

        public void bindATM(PresentationATM presentationATM) {
            mComisionView.setVisibility(View.VISIBLE);
            float commission = presentationATM.getCommission();
            if (commission > 0.0f) {
                mComissionNumber.setText(String.valueOf(commission));
            } else {
                mComissionNumber.setText(String.valueOf((int) commission));
            }

            String comissionDescString = presentationATM.getIndicadorComision();
            if ("S".equalsIgnoreCase(comissionDescString)) {
                mComissionDesc.setText("de comisión");
            } else if ("N".equalsIgnoreCase(comissionDescString)) {
                mComissionDesc.setText("de comisión \n máxima");
            }

            mEntityName.setText(presentationATM.getNombreEntidad());
            mAddress.setText(presentationATM.getDireccion());

            String zipCity = presentationATM.getCodigoPostal() + " " + presentationATM.getNombreLocalidad();
            mZipAndCity.setText(zipCity);

            mDistanceText.setText(presentationATM.getDistancia());
        }
    }
}
