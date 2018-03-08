package sngular.com.atmsmap.presentation.view.fragment;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterfork.Bind;
import butterfork.ButterFork;
import butterfork.OnClick;
import sngular.com.atmsmap.B;
import sngular.com.atmsmap.R;
import sngular.com.atmsmap.app.constant.ATM_Type;
import sngular.com.atmsmap.app.constant.AppConstants;
import sngular.com.atmsmap.presentation.adapter.ATMPresentationAdapter;
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
public class ATMListDialog extends BaseDialogFragment implements OnRecyclerItemSelectedListener {

    @Bind(B.id.atm_list)
    RecyclerView mATMRecyclerView;
    @Bind(B.id.list_atm_empty)
    LinearLayout listEmpty;

    @Inject
    MapService mMapManager;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_atm_list, container, false);
        ButterFork.bind(this, view);
        configureATMRecyclerView();
        return view;
    }

    private void configureATMRecyclerView() {
        List<PresentationATM> mATMList = mMapManager.getPresentationAtms();
        boolean isNoCommission = mMapManager.isNoCommissions();
        if (mATMList != null) {
            List<PresentationATM> newATM = new ArrayList<>();
            for (PresentationATM presentationATM : mATMList) {
                if (isNoCommission) {
                    if (presentationATM.getType() == ATM_Type.ATM_SAME_ENTITY ||
                            presentationATM.getType() == ATM_Type.ATM_SAME_GROUP_WITHOUT_COMMISSION ||
                            presentationATM.getType() == ATM_Type.ATM_DIFFERENT_GROUP_WITHOUT_COMMISSION) {
                        newATM.add(presentationATM);
                    }
                } else {
                    newATM.add(presentationATM);
                }
            }
            if (newATM.isEmpty()) {
                listEmpty.setVisibility(View.VISIBLE);
                mATMRecyclerView.setVisibility(View.GONE);
            } else {
                listEmpty.setVisibility(View.GONE);
                mATMRecyclerView.setVisibility(View.VISIBLE);
                mATMRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mATMRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
                mATMRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity().getBaseContext()));
                mATMRecyclerView.setAdapter(new ATMPresentationAdapter(newATM, this));
            }
        } else {
            listEmpty.setVisibility(View.VISIBLE);
            mATMRecyclerView.setVisibility(View.GONE);
        }
    }

    @OnClick(B.id.button_atm_close_dialog)
    public void closeDialog() {
        ATMListDialog.this.dismiss();
    }


    @Override
    public void onRecyclerATMItemSelected(PresentationATM presentationATM) {
        MapFragment mapFragment = (MapFragment) getActivity().getSupportFragmentManager()
                .findFragmentByTag(AppConstants.FRAGMENT_MAP);
        if (mapFragment != null) {
            PoiItem poiItem = PoiManager.createATMPoi(presentationATM);
            mapFragment.centerViewInPoi(poiItem);
        }
        ATMListDialog.this.dismiss();
    }

    @Override
    public void onRecyclerOfficeItemSelected(PresentationOffice presentationOffice) {
        //Empty Callback
    }
}
