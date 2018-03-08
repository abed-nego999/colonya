package sngular.com.atmsmap.presentation.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterfork.Bind;
import butterfork.ButterFork;
import butterfork.OnCheckedChanged;
import butterfork.OnClick;
import de.greenrobot.event.EventBus;
import sngular.com.atmsmap.B;
import sngular.com.atmsmap.R;
import sngular.com.atmsmap.app.constant.AppConstants;
import sngular.com.atmsmap.data.util.RestUtil;
import sngular.com.atmsmap.domain.event.LocationProviderDisableEvent;
import sngular.com.atmsmap.domain.event.LocationProviderEnableEvent;
import sngular.com.atmsmap.domain.event.NetworkEnableEvent;
import sngular.com.atmsmap.domain.location.LocationListenerAndroid;
import sngular.com.atmsmap.presentation.adapter.ClusterListAdapter;
import sngular.com.atmsmap.presentation.exception.NotLocationException;
import sngular.com.atmsmap.presentation.exception.NotNetworkException;
import sngular.com.atmsmap.presentation.info.InfoRSIService;
import sngular.com.atmsmap.presentation.model.Presentation;
import sngular.com.atmsmap.presentation.model.PresentationATM;
import sngular.com.atmsmap.presentation.model.PresentationOffice;
import sngular.com.atmsmap.presentation.model.SearchResult;
import sngular.com.atmsmap.presentation.navigation.IntentNavigation;
import sngular.com.atmsmap.presentation.util.SwitchTrackTextDrawable;
import sngular.com.atmsmap.presentation.util.ViewUtils;
import sngular.com.atmsmap.presentation.view.fragment.ATMListDialog;
import sngular.com.atmsmap.presentation.view.fragment.DialogErrorFragment;
import sngular.com.atmsmap.presentation.view.fragment.DialogHelpFragment;
import sngular.com.atmsmap.presentation.view.fragment.MapFragment;
import sngular.com.atmsmap.presentation.view.fragment.OfficeListDialog;
import sngular.com.atmsmap.presentation.view.listener.OnMapEventsListener;
import sngular.com.awesomemap.net.model.LatLon;

/**
 * Created by alberto.hernandez on 04/04/2016.
 */

public class MapActivity extends BaseActivity implements OnMapEventsListener, Animation.AnimationListener {
    private static final String TAG = MapActivity.class.getSimpleName();

    //region variables
    @Bind(B.id.activity_map_toolbar)
    Toolbar toolbar;
    @Bind(B.id.iv_logo)
    ImageView ivLogo;
    @Bind(B.id.activity_map_detail_container)
    RelativeLayout mMakerDetailContainerView;
    @Bind(B.id.activity_map_closest_container)
    RelativeLayout mClosestContainerView;
    @Bind(B.id.activity_map_detail_name)
    TextView mMakerDetailNameTextView;
    @Bind(B.id.activity_map_detail_address)
    TextView mMakerDetailAddressTextView;
    @Bind(B.id.activity_map_detail_phone)
    TextView mMakerDetailPhone;
    @Bind(B.id.activity_map_actions_show_my_location)
    CheckBox mActionShowMyLocationCheckBox;
    @Bind(B.id.activity_map_actions_show_commissions)
    CheckBox mActionShowCommissions;
    @Bind(B.id.activity_map_actions_office_atm)
    Switch mActionBrachesAtm;
    @Bind(B.id.activity_map_actions_container)
    LinearLayout mActionsContainer;
    @Bind(B.id.view_network_error)
    LinearLayout mNetworkError;
    @Bind(B.id.btn_search)
    ImageButton mBtnSearch;
    @Bind(B.id.btn_list_pois)
    ImageButton mBtnList;
    @Bind(B.id.activity_map_closest_distance)
    TextView mClosestDistance;
    @Bind(B.id.ll_commssions)
    LinearLayout mLayoutCommission;
    @Bind(B.id.activity_map_closest_commission)
    TextView mClosestCommission;
    @Bind(B.id.activity_map_closest_commission_desc)
    TextView mClosestCommissionDesc;
    @Bind(B.id.detail_closest_distance)
    TextView mDetailClosestDistance;
    @Bind(B.id.rl_detail_commssions)
    RelativeLayout mLayoutDetailCommission;
    @Bind(B.id.detail_closest_commission)
    TextView mDetailClosestCommission;
    @Bind(B.id.detail_closest_commission_desc)
    TextView mDetailClosestCommissionDesc;
    @Bind(B.id.activity_map_detail_show_route)
    ImageButton mShowRouteImageButton;
    @Bind(B.id.activity_map_detail_open_navigation)
    ImageButton mOpenNavigationImageButton;
    @Bind(B.id.best_practices_atm)
    TextView mBestPracticesATMText;
    @Bind(B.id.best_practices_office)
    TextView mBestPracticesOfficeText;
    @Bind(B.id.activity_map_cluster_list_container)
    RelativeLayout mClusterListLayout;
    @Bind(B.id.cluster_list)
    ListView mClusterRecyclerView;

    @Inject
    InfoRSIService mInfoRSI;

    private static final String FRAGMENT_DIALOG_ERROR = "fragment_dialog_error";
    private static final String FRAGMENT_DIALOG_HELP = "fragment_dialog_help";
    private static final String FRAGMENT_ATM_LIST = "fragment_atm_list";
    private static final String FRAGMENT_OFFICE_LIST = "fragment_office_list";
    private static final String SEARCH_RESULT = "searchResult";
    private static final int ANIMATION_SHOW_MY_LOCATION_DURATION = 3000;
    final long SHOW_HELP_MILLISECONDS = 1000;

    private Activity mMapActivity;
    private LocationListenerAndroid mLocationListenerAndroid;
    private LocationManager mLocationManager;
    private LatLon mCurrentSelectLocation;
    private LatLon mCurrentSearchLocation;
    private PresentationATM mClosestATM;
    private PresentationOffice mClosestOffice;
    private List<Presentation> mClusterList;
    private boolean mIsModeListActive;
    //endregion

    //region Ciclo de vida
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInfoRSI.setEntity(getIntent().getStringExtra(AppConstants.ENTITY_INTENT));
        mInfoRSI.setEntityHelpTitle(getIntent().getStringExtra(AppConstants.ENTITY_HELP_TITLE_INTENT));
        mInfoRSI.setEntityHelpGroups(getIntent().getBooleanExtra(AppConstants.ENTITY_HELP_GROUPS_INTENT, true));
        mInfoRSI.setGoogleMapsKeys(getIntent().getStringExtra(AppConstants.MAP_WEB_KEY_INTENT),
                getIntent().getStringExtra(AppConstants.MAP_KEY_INTENT), getBaseContext());
        RestUtil.saveURLs(getIntent().getStringExtra(AppConstants.ATMS_INTENT), getIntent().getStringExtra(AppConstants.OFFICES_INTENT), getApplicationContext());

        setContentView(R.layout.activity_map);

        EventBus.getDefault().register(this);
        ButterFork.bind(this);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMapActivity = this;
        mLocationListenerAndroid = new LocationListenerAndroid();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.map, new MapFragment(), AppConstants.FRAGMENT_MAP)
                    .commit();
        }

        mMakerDetailContainerView.setOnClickListener(null);

        mActionBrachesAtm.setTrackDrawable(new SwitchTrackTextDrawable(this,
                R.string.atms, R.string.offices));

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mActionBrachesAtm.setSwitchMinWidth(0);
            mActionBrachesAtm.setTrackResource(R.drawable.switch_custom_track);
            mActionBrachesAtm.setThumbResource(R.drawable.switch_custom_thumb);
            mBestPracticesOfficeText.setVisibility(View.VISIBLE);
        }

        //Se muestra la ayuda (con delay)
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showHelp();
            }
        }, SHOW_HELP_MILLISECONDS);

        //Ocultar logo
        if (!getIntent().getBooleanExtra(AppConstants.ENTITY_HELP_GROUPS_INTENT, true)) {
            ivLogo.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        mLocationManager.requestLocationUpdates(mLocationManager.getBestProvider(criteria, false),
                200, 1, mLocationListenerAndroid);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(mLocationListenerAndroid);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.CODE_SEARCH && resultCode == RESULT_OK) {
            removeDetail();
            SearchResult searchResult = data.getParcelableExtra(SEARCH_RESULT);
            Log.i(TAG, "Place details received: " + searchResult.getName() +
                    "Latitude: " + searchResult.getLatitude() + "  " +
                    "Longitude: " + searchResult.getLongitude());
            mCurrentSearchLocation = new LatLon(searchResult.getLatitude(), searchResult.getLongitude());
            MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(AppConstants.FRAGMENT_MAP);
            if (mapFragment != null) {
                mapFragment.centerViewInAddress(searchResult);
            }
        }
    }
    //endregion

    //region Callbacks de Map Fragment
    @Override
    public void onMarkerTapATM(PresentationATM atm) {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(AppConstants.FRAGMENT_MAP);
        if (mapFragment != null) {
            mapFragment.showRoute(false);
        }
        String address = atm.getDireccion() + "\n" + atm.getCodigoPostal() + " " + atm.getNombreLocalidad();
        updateDetail(atm.getNombreEntidad(), address, null, atm.getDistancia(), String.valueOf(atm.getCommission()),
                atm.getIndicadorComision());
        mCurrentSelectLocation = new LatLon(atm.getLatitude(), atm.getLongitude());
    }

    @Override
    public void onMarkerTapOffice(PresentationOffice office) {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(AppConstants.FRAGMENT_MAP);
        if (mapFragment != null) {
            mapFragment.showRoute(false);
        }
        String address = office.getDireccion() + "\n" + office.getCodigoPostal() + " " + office.getNombreLocalidad();
        updateDetail(office.getNombreOficina(), address, office.getTelefono(), office.getDistancia(), null, null);
        mCurrentSelectLocation = new LatLon(office.getLatitude(), office.getLongitude());
    }

    @Override
    public void onMapClick() {
        removeDetail();
        closeClusterDialog();
    }

    @Override
    public void onError(Throwable ex) {
        if (ex instanceof NotNetworkException) {
            Log.e(TAG, "NotNetworkException");
            mNetworkError.setVisibility(View.VISIBLE);
            mBtnSearch.setVisibility(View.GONE);
            mBtnList.setVisibility(View.GONE);
        } else if (ex instanceof NotLocationException) {
            Log.e(TAG, "NotLocationException");
            setError(getResources().getString(R.string.dialog_error_title),
                    getResources().getString(R.string.activity_map_location_error_message));
        }
    }

    @Override
    public void onErrorStrings(String title, String subtitle) {
        setError(title, subtitle);
    }

    @Override
    public void showHelp() {
        DialogHelpFragment dialogError = (DialogHelpFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_DIALOG_HELP);
        if (dialogError == null) {
            dialogError = DialogHelpFragment.newInstance();
            dialogError.setCancelable(false);
            dialogError.show(getSupportFragmentManager(), FRAGMENT_DIALOG_HELP);
        }
    }

    @Override
    public void onATMDistanceCommission(PresentationATM lastATM) {
        mClosestATM = lastATM;
        updateATMDistance(lastATM);
    }

    @Override
    public void onOfficeDistanceCommission(PresentationOffice lastOffice) {
        mClosestOffice = lastOffice;
        updateOfficeDistance(lastOffice);
    }

    @Override
    public void onClusterList(List<Presentation> presentationList) {
        if (presentationList != null) {
            removeDetail();
            mClusterList = presentationList;
            mClusterRecyclerView.setAdapter(new ClusterListAdapter(this, presentationList));
            animateList(true);
            mIsModeListActive = true;
            mClusterRecyclerView.setOnItemClickListener(mClusterListClickListener);
        }
    }

    //endregion

    //region Eventos
    public void onEvent(NetworkEnableEvent event) {
        Log.d(TAG, "NetworkEnableEvent");
        mNetworkError.setVisibility(View.GONE);
        mBtnSearch.setVisibility(View.VISIBLE);
        mBtnList.setVisibility(View.VISIBLE);
    }

    public void onEvent(LocationProviderEnableEvent event) {
        Log.d(TAG, "LocationProviderEnableEvent");
        DialogErrorFragment mDialogError = (DialogErrorFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_DIALOG_ERROR);
        if (mDialogError != null) {
            mDialogError.dismiss();
        }

        enableLocationDependantViews();

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(AppConstants.FRAGMENT_MAP);
        if (mapFragment != null) {
            mapFragment.centerViewInMyLocation();
        }
    }

    public void onEvent(LocationProviderDisableEvent event) {
        Log.d(TAG, "LocationProviderDisableEvent");
        setError(getResources().getString(R.string.dialog_error_title),
                getResources().getString(R.string.activity_map_location_error_message));

        disableLocationDependantViews();
    }

    //endregion

    //region Botones
    @OnCheckedChanged(B.id.activity_map_actions_show_my_location)
    public void onShowMyLocation() {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(AppConstants.FRAGMENT_MAP);
        if (mapFragment != null) {
            mapFragment.centerViewInMyLocation();
        }
        mCurrentSearchLocation = null;
        ViewUtils.cancelCheckedDelay(mActionShowMyLocationCheckBox, ANIMATION_SHOW_MY_LOCATION_DURATION);
    }

    @OnCheckedChanged(B.id.activity_map_actions_show_commissions)
    public void onShowCommissions() {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(AppConstants.FRAGMENT_MAP);
        if (mapFragment != null) {
            mapFragment.changeCommissionsFilter(mActionShowCommissions.isChecked());
        }
    }

    @OnCheckedChanged(B.id.activity_map_actions_office_atm)
    public void onSwitchOfficeAtm() {
        closeClusterDialog();
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(AppConstants.FRAGMENT_MAP);
        if (mapFragment != null) {
            mapFragment.changeFilter(mActionBrachesAtm.isChecked());
        }
        if (mActionBrachesAtm.isChecked()) {
            mActionShowCommissions.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                mBestPracticesATMText.setVisibility(View.VISIBLE);
                mBestPracticesOfficeText.setVisibility(View.INVISIBLE);
            }
        } else {
            mActionShowCommissions.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                mBestPracticesATMText.setVisibility(View.INVISIBLE);
                mBestPracticesOfficeText.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(B.id.activity_map_detail_open_navigation)
    public void openNavigation() {
        if (mCurrentSelectLocation != null) {
            IntentNavigation.openNavigation(getApplicationContext(), mCurrentSelectLocation, mCurrentSearchLocation);
        }
    }

    @OnClick(B.id.activity_map_detail_show_route)
    public void showRoute() {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(AppConstants.FRAGMENT_MAP);
        if (mapFragment != null) {
            mapFragment.showRoute(true);
        }
    }

    @OnClick(B.id.activity_map_detail_phone)
    public void phoneCall() {
        String phoneNumber = mMakerDetailPhone.getText().toString();
        IntentNavigation.phoneCall(this, phoneNumber);
    }

    @OnClick(B.id.btn_search)
    public void onSearchActivity() {
        closeClusterDialog();
        IntentNavigation.openSearchActivity(mMapActivity);
    }

    @OnClick(B.id.btn_list_pois)
    public void onListPois() {
        closeClusterDialog();
        if (mActionBrachesAtm.isChecked()) {
            OfficeListDialog dialog = new OfficeListDialog();
            dialog.show(getSupportFragmentManager(), FRAGMENT_OFFICE_LIST);
        } else {
            ATMListDialog dialog = new ATMListDialog();
            dialog.show(getSupportFragmentManager(), FRAGMENT_ATM_LIST);
        }
    }

    @OnClick(B.id.activity_map_closest_container)
    public void onClickClosest() {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(AppConstants.FRAGMENT_MAP);
        if (mActionBrachesAtm.isChecked() && mClosestOffice != null && mapFragment != null) {
            mapFragment.onOfficeSelected(mClosestOffice);
        } else if (mClosestATM != null && mapFragment != null) {
            mapFragment.onATMSelected(mClosestATM);
        }
    }

    @OnClick(B.id.btn_help)
    public void onClickHelp() {
        showHelp();
    }

    //endregion

    //region Interfaz
    private void updateDetail(String name, String address, String phone, String distance,
                              String commission, String indicadorComision) {
        closeClusterDialog();
        mActionBrachesAtm.setVisibility(View.GONE);
        mActionsContainer.setVisibility(View.GONE);
        mClosestContainerView.setVisibility(View.GONE);
        mMakerDetailContainerView.setVisibility(View.VISIBLE);
        mMakerDetailNameTextView.setText(name);
        mMakerDetailAddressTextView.setText(address);
        if (phone != null) {
            mMakerDetailPhone.setVisibility(View.VISIBLE);
            mMakerDetailPhone.setText(phone);
        } else {
            mMakerDetailPhone.setVisibility(View.GONE);
        }
        if (distance != null) {
            mDetailClosestDistance.setText("A " + distance);
        }
        if (commission != null && indicadorComision != null) {
            mLayoutDetailCommission.setVisibility(View.VISIBLE);
            mDetailClosestCommission.setText(commission + "€");
            if ("S".equalsIgnoreCase(indicadorComision)) {
                mDetailClosestCommissionDesc.setText(getString(R.string.commission));
            } else if ("N".equalsIgnoreCase(indicadorComision)) {
                mDetailClosestCommissionDesc.setText(getString(R.string.max_commission));
            }
        } else {
            mLayoutDetailCommission.setVisibility(View.GONE);
        }
    }

    private void updateATMDistance(PresentationATM atm) {
        mLayoutCommission.setVisibility(View.VISIBLE);
        if (atm != null) {
            if (atm.getDistancia() != null) {
                mClosestContainerView.setVisibility(View.VISIBLE);
                mClosestDistance.setText(getString(R.string.atm_closest) + " " + atm.getDistancia());
            } else {
                mClosestContainerView.setVisibility(View.GONE);
            }
            mClosestCommission.setText(atm.getCommission() + "€");
            if ("S".equalsIgnoreCase(atm.getIndicadorComision())) {
                mClosestCommissionDesc.setText(getString(R.string.commission));
            } else if ("N".equalsIgnoreCase(atm.getIndicadorComision())) {
                mClosestCommissionDesc.setText(getString(R.string.max_commission));
            }
        } else {
            mClosestContainerView.setVisibility(View.GONE);
        }
    }

    private void updateOfficeDistance(PresentationOffice office) {
        mLayoutCommission.setVisibility(View.GONE);
        if (office != null) {
            if (office.getDistancia() != null) {
                mClosestContainerView.setVisibility(View.VISIBLE);
                mClosestDistance.setText(getString(R.string.office_closest) + " " + office.getDistancia());
            } else {
                mClosestContainerView.setVisibility(View.GONE);
            }
        } else {
            mClosestContainerView.setVisibility(View.GONE);
        }
    }

    private void removeDetail() {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(AppConstants.FRAGMENT_MAP);
        if (mapFragment != null) {
            mapFragment.showRoute(false);
            mapFragment.deselectPoi();
            mMakerDetailContainerView.setVisibility(View.GONE);
            mActionBrachesAtm.setVisibility(View.VISIBLE);
            mActionsContainer.setVisibility(View.VISIBLE);
            if (!mClosestDistance.getText().toString().isEmpty()) {
                mClosestContainerView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void enableLocationDependantViews() {
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.selector_show_my_location);
        mActionShowMyLocationCheckBox.setBackground(drawable);
        mShowRouteImageButton.setEnabled(true);
        mShowRouteImageButton.setAlpha(1f);
        mOpenNavigationImageButton.setEnabled(true);
        mOpenNavigationImageButton.setAlpha(1f);
    }

    private void disableLocationDependantViews() {
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.selector_location_disabled);
        mActionShowMyLocationCheckBox.setBackground(drawable);
        mShowRouteImageButton.setEnabled(false);
        mShowRouteImageButton.setAlpha(0.5f);
        mOpenNavigationImageButton.setEnabled(false);
        mOpenNavigationImageButton.setAlpha(0.5f);
    }

    private void setError(String titleId, String subtitleId) {
        DialogErrorFragment mDialogError = (DialogErrorFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_DIALOG_ERROR);
        if (mDialogError == null) {
            mDialogError = DialogErrorFragment.newInstance(titleId, subtitleId);
            mDialogError.show(getSupportFragmentManager(), FRAGMENT_DIALOG_ERROR);
        }
    }

    private void animateList(boolean openList) {
        float dimension = getResources().getDimension(R.dimen.item_cluster_height);
        float maxDimension = getResources().getDimension(R.dimen.item_cluster_max_height);
        float delta = mClusterList.size() * dimension;

        TranslateAnimation anim;
        if (openList) {
            anim = new TranslateAnimation(0, 0, delta, 0);
        } else {
            anim = new TranslateAnimation(0, 0, 0, delta);
        }

        anim.setFillAfter(false);
        anim.setDuration(1000);
        anim.setAnimationListener(this);
        mClusterListLayout.setVisibility(View.VISIBLE);
        mClusterListLayout.setAnimation(anim);
        mClusterListLayout.startAnimation(anim);
    }

    @OnClick(B.id.button_cluster_close_dialog)
    public void closeClusterDialog() {
        if (mIsModeListActive) {
            animateList(false);
            mIsModeListActive = false;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (!mIsModeListActive) {
            mClusterListLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    AdapterView.OnItemClickListener mClusterListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            closeClusterDialog();
            Presentation poiCluster = (Presentation) adapterView.getItemAtPosition(i);
            MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(AppConstants.FRAGMENT_MAP);
            if (poiCluster instanceof PresentationATM && mapFragment != null) {
                PresentationATM poiATM = (PresentationATM) poiCluster;
                mapFragment.onATMSelected(poiATM);
            } else if (poiCluster instanceof PresentationOffice && mapFragment != null) {
                PresentationOffice poiOffice = (PresentationOffice) poiCluster;
                mapFragment.onOfficeSelected(poiOffice);
            }
        }
    };
    //endregion
}
