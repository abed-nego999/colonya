package sngular.com.atmsmap.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterfork.Bind;
import butterfork.ButterFork;
import sngular.com.atmsmap.B;
import sngular.com.atmsmap.R;
import sngular.com.atmsmap.presentation.model.Presentation;
import sngular.com.atmsmap.presentation.model.PresentationATM;
import sngular.com.atmsmap.presentation.model.PresentationOffice;

/**
 * Created by alberto.hernandez on 17/05/2016.
 */
public class ClusterListAdapter extends ArrayAdapter<Presentation> {
    @Bind(B.id.entity_text)
    TextView mEntityName;
    @Bind(B.id.address_first_text)
    TextView mAddress;
    @Bind(B.id.address_second_text)
    TextView mZipAndCity;

    private List<Presentation> mClusterList;

    public ClusterListAdapter(Context context, List<Presentation> list) {
        super(context, 0, list);
        mClusterList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItemView = convertView;
        if (null == convertView) {
            listItemView = inflater.inflate(R.layout.item_list_cluster, parent, false);
            ButterFork.bind(this, listItemView);
        }

        Presentation poiCluster = mClusterList.get(position);
        if (poiCluster instanceof PresentationATM) {
            PresentationATM poiATM = (PresentationATM) poiCluster;
            mEntityName.setText(poiATM.getNombreEntidad());
            mAddress.setText(poiATM.getDireccion());
            String zipCity = poiATM.getCodigoPostal() + " " + poiATM.getNombreLocalidad();
            mZipAndCity.setText(zipCity);
        } else if (poiCluster instanceof PresentationOffice) {
            PresentationOffice poiOffice = (PresentationOffice) poiCluster;
            mEntityName.setText(poiOffice.getNombreEntidad());
            mAddress.setText(poiOffice.getDireccion());
            String zipCity = poiOffice.getCodigoPostal() + " " + poiOffice.getNombreLocalidad();
            mZipAndCity.setText(zipCity);
        }

        return listItemView;
    }

    @Override
    public int getCount() {
        return mClusterList.size();
    }

    @Override
    public Presentation getItem(int position) {
        return mClusterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
