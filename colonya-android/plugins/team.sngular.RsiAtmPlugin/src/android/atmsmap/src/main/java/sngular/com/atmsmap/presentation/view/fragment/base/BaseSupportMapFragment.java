package sngular.com.atmsmap.presentation.view.fragment.base;

import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;

import sngular.com.atmsmap.presentation.view.activity.BaseActivity;


public class BaseSupportMapFragment extends SupportMapFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity) getActivity()).inject(this);
    }

}
