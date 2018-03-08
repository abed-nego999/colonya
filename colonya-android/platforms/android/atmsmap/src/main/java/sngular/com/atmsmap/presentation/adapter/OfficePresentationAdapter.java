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
import sngular.com.atmsmap.presentation.model.PresentationOffice;
import sngular.com.atmsmap.presentation.view.listener.OnRecyclerItemSelectedListener;

/**
 * Created by luissedano on 20/4/16.
 */
public class OfficePresentationAdapter extends RecyclerView.Adapter<OfficePresentationAdapter.OfficePresentationViewHolder> {

    private List<PresentationOffice> mOfficeList;
    private OnRecyclerItemSelectedListener mListener;

    public OfficePresentationAdapter(List<PresentationOffice> list, OnRecyclerItemSelectedListener listener) {
        mOfficeList = list;
        mListener = listener;
    }


    @Override
    public OfficePresentationAdapter.OfficePresentationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_poi, parent, false);
        return new OfficePresentationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfficePresentationAdapter.OfficePresentationViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRecyclerOfficeItemSelected(mOfficeList.get(position));
            }
        });

        holder.bindATM(mOfficeList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mOfficeList != null) {
            return mOfficeList.size();
        } else {
            return 0;
        }
    }

    protected class OfficePresentationViewHolder extends RecyclerView.ViewHolder {
        @Bind(B.id.linear_comission_view)
        LinearLayout mComisionView;
        @Bind(B.id.entity_text)
        TextView mEntityName;
        @Bind(B.id.address_first_text)
        TextView mAddress;
        @Bind(B.id.address_second_text)
        TextView mZipAndCity;
        @Bind(B.id.distance_text)
        TextView mDistanceText;

        public OfficePresentationViewHolder(View itemView) {
            super(itemView);
            ButterFork.bind(this, itemView);
        }

        public void bindATM(PresentationOffice presentationOffice) {
            mComisionView.setVisibility(View.GONE);

            mEntityName.setText(presentationOffice.getNombreEntidad());
            mAddress.setText(presentationOffice.getDireccion());

            String zipCity = presentationOffice.getCodigoPostal() + " " + presentationOffice.getNombreLocalidad();
            mZipAndCity.setText(zipCity);

            mDistanceText.setText(presentationOffice.getDistancia());

        }
    }
}
