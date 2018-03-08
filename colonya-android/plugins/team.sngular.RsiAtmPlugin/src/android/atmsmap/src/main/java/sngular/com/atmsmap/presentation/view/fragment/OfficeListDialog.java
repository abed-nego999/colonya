package sngular.com.atmsmap.presentation.view.fragment;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import javax.inject.Inject;

import butterfork.Bind;
import butterfork.ButterFork;
import butterfork.OnClick;
import sngular.com.atmsmap.B;
import sngular.com.atmsmap.R;
import sngular.com.atmsmap.app.constant.AppConstants;
import sngular.com.atmsmap.presentation.adapter.OfficePresentationAdapter;
import sngular.com.atmsmap.presentation.map.MapService;
import sngular.com.atmsmap.presentation.map.PoiManager;
import sngular.com.atmsmap.presentation.model.PresentationATM;
import sngular.com.atmsmap.presentation.model.PresentationOffice;
import sngular.com.atmsmap.presentation.util.SimpleDividerItemDecoration;
import sngular.com.atmsmap.presentation.view.fragment.base.BaseDialogFragment;
import sngular.com.atmsmap.presentation.view.listener.OnRecyclerItemSelectedListener;
import sngular.com.awesomemap.model.PoiItem;

/**
 * Created by luissedano on 20/4/16.
 */
public class OfficeListDialog extends BaseDialogFragment implements OnRecyclerItemSelectedListener {

    @Bind(B.id.office_list)
    RecyclerView mOfficeRecyclerView;
    @Bind(B.id.list_office_empty)
    LinearLayout listEmpty;

    @Inject
    MapService mMapManager;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_office_list, container, false);
        ButterFork.bind(this, view);
        configureOfficeRecyclerView();
        return view;
    }

    private void configureOfficeRecyclerView() {
        List<PresentationOffice> mOfficeList = mMapManager.getPresentationOffices();
        if (mOfficeList != null) {
            if (mOfficeList.isEmpty()) {
                listEmpty.setVisibility(View.VISIBLE);
                mOfficeRecyclerView.setVisibility(View.GONE);
            } else {
                listEmpty.setVisibility(View.GONE);
                mOfficeRecyclerView.setVisibility(View.VISIBLE);
                mOfficeRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mOfficeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
                mOfficeRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity().getBaseContext()));
                mOfficeRecyclerView.setAdapter(new OfficePresentationAdapter(mOfficeList, this));
            }
        } else {
            listEmpty.setVisibility(View.VISIBLE);
            mOfficeRecyclerView.setVisibility(View.GONE);
        }
    }

    @OnClick(B.id.button_office_close_dialog)
    public void closeDialog() {
        OfficeListDialog.this.dismiss();
    }


    @Override
    public void onRecyclerATMItemSelected(PresentationATM presentationATM) {
        //Empty Callback
    }

    @Override
    public void onRecyclerOfficeItemSelected(PresentationOffice presentationOffice) {
        MapFragment mapFragment = (MapFragment) getActivity().getSupportFragmentManager()
                .findFragmentByTag(AppConstants.FRAGMENT_MAP);
        if (mapFragment != null) {
            PoiItem poiItem = PoiManager.createOfficePoi(presentationOffice);
            mapFragment.centerViewInPoi(poiItem);
        }
        OfficeListDialog.this.dismiss();
    }
}
