// Generated code from Butter Fork. Do not modify!
package sngular.com.atmsmap.presentation.view.activity;

import android.view.View;
import android.widget.CompoundButton;
import butterfork.ButterFork;
import butterfork.internal.DebouncingOnClickListener;
import java.lang.Object;
import java.lang.Override;
import sngular.com.atmsmap.R;

public class MapActivity$$ViewBinder<T extends MapActivity> implements ButterFork.ViewBinder<T> {
  @Override
  public void bind(final ButterFork.Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, R.id.activity_map_toolbar, "field 'toolbar'");
    target.toolbar = finder.castView(view, R.id.activity_map_toolbar, "field 'toolbar'");
    view = finder.findRequiredView(source, R.id.iv_logo, "field 'ivLogo'");
    target.ivLogo = finder.castView(view, R.id.iv_logo, "field 'ivLogo'");
    view = finder.findRequiredView(source, R.id.activity_map_detail_container, "field 'mMakerDetailContainerView'");
    target.mMakerDetailContainerView = finder.castView(view, R.id.activity_map_detail_container, "field 'mMakerDetailContainerView'");
    view = finder.findRequiredView(source, R.id.activity_map_closest_container, "field 'mClosestContainerView' and method 'onClickClosest'");
    target.mClosestContainerView = finder.castView(view, R.id.activity_map_closest_container, "field 'mClosestContainerView'");
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickClosest();
      }
    });
    view = finder.findRequiredView(source, R.id.activity_map_detail_name, "field 'mMakerDetailNameTextView'");
    target.mMakerDetailNameTextView = finder.castView(view, R.id.activity_map_detail_name, "field 'mMakerDetailNameTextView'");
    view = finder.findRequiredView(source, R.id.activity_map_detail_address, "field 'mMakerDetailAddressTextView'");
    target.mMakerDetailAddressTextView = finder.castView(view, R.id.activity_map_detail_address, "field 'mMakerDetailAddressTextView'");
    view = finder.findRequiredView(source, R.id.activity_map_detail_phone, "field 'mMakerDetailPhone' and method 'phoneCall'");
    target.mMakerDetailPhone = finder.castView(view, R.id.activity_map_detail_phone, "field 'mMakerDetailPhone'");
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.phoneCall();
      }
    });
    view = finder.findRequiredView(source, R.id.activity_map_actions_show_my_location, "field 'mActionShowMyLocationCheckBox' and method 'onShowMyLocation'");
    target.mActionShowMyLocationCheckBox = finder.castView(view, R.id.activity_map_actions_show_my_location, "field 'mActionShowMyLocationCheckBox'");
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onShowMyLocation();
      }
    });
    view = finder.findRequiredView(source, R.id.activity_map_actions_show_commissions, "field 'mActionShowCommissions' and method 'onShowCommissions'");
    target.mActionShowCommissions = finder.castView(view, R.id.activity_map_actions_show_commissions, "field 'mActionShowCommissions'");
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onShowCommissions();
      }
    });
    view = finder.findRequiredView(source, R.id.activity_map_actions_office_atm, "field 'mActionBrachesAtm' and method 'onSwitchOfficeAtm'");
    target.mActionBrachesAtm = finder.castView(view, R.id.activity_map_actions_office_atm, "field 'mActionBrachesAtm'");
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onSwitchOfficeAtm();
      }
    });
    view = finder.findRequiredView(source, R.id.activity_map_actions_container, "field 'mActionsContainer'");
    target.mActionsContainer = finder.castView(view, R.id.activity_map_actions_container, "field 'mActionsContainer'");
    view = finder.findRequiredView(source, R.id.view_network_error, "field 'mNetworkError'");
    target.mNetworkError = finder.castView(view, R.id.view_network_error, "field 'mNetworkError'");
    view = finder.findRequiredView(source, R.id.btn_search, "field 'mBtnSearch' and method 'onSearchActivity'");
    target.mBtnSearch = finder.castView(view, R.id.btn_search, "field 'mBtnSearch'");
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSearchActivity();
      }
    });
    view = finder.findRequiredView(source, R.id.btn_list_pois, "field 'mBtnList' and method 'onListPois'");
    target.mBtnList = finder.castView(view, R.id.btn_list_pois, "field 'mBtnList'");
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onListPois();
      }
    });
    view = finder.findRequiredView(source, R.id.activity_map_closest_distance, "field 'mClosestDistance'");
    target.mClosestDistance = finder.castView(view, R.id.activity_map_closest_distance, "field 'mClosestDistance'");
    view = finder.findRequiredView(source, R.id.ll_commssions, "field 'mLayoutCommission'");
    target.mLayoutCommission = finder.castView(view, R.id.ll_commssions, "field 'mLayoutCommission'");
    view = finder.findRequiredView(source, R.id.activity_map_closest_commission, "field 'mClosestCommission'");
    target.mClosestCommission = finder.castView(view, R.id.activity_map_closest_commission, "field 'mClosestCommission'");
    view = finder.findRequiredView(source, R.id.activity_map_closest_commission_desc, "field 'mClosestCommissionDesc'");
    target.mClosestCommissionDesc = finder.castView(view, R.id.activity_map_closest_commission_desc, "field 'mClosestCommissionDesc'");
    view = finder.findRequiredView(source, R.id.detail_closest_distance, "field 'mDetailClosestDistance'");
    target.mDetailClosestDistance = finder.castView(view, R.id.detail_closest_distance, "field 'mDetailClosestDistance'");
    view = finder.findRequiredView(source, R.id.rl_detail_commssions, "field 'mLayoutDetailCommission'");
    target.mLayoutDetailCommission = finder.castView(view, R.id.rl_detail_commssions, "field 'mLayoutDetailCommission'");
    view = finder.findRequiredView(source, R.id.detail_closest_commission, "field 'mDetailClosestCommission'");
    target.mDetailClosestCommission = finder.castView(view, R.id.detail_closest_commission, "field 'mDetailClosestCommission'");
    view = finder.findRequiredView(source, R.id.detail_closest_commission_desc, "field 'mDetailClosestCommissionDesc'");
    target.mDetailClosestCommissionDesc = finder.castView(view, R.id.detail_closest_commission_desc, "field 'mDetailClosestCommissionDesc'");
    view = finder.findRequiredView(source, R.id.activity_map_detail_show_route, "field 'mShowRouteImageButton' and method 'showRoute'");
    target.mShowRouteImageButton = finder.castView(view, R.id.activity_map_detail_show_route, "field 'mShowRouteImageButton'");
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showRoute();
      }
    });
    view = finder.findRequiredView(source, R.id.activity_map_detail_open_navigation, "field 'mOpenNavigationImageButton' and method 'openNavigation'");
    target.mOpenNavigationImageButton = finder.castView(view, R.id.activity_map_detail_open_navigation, "field 'mOpenNavigationImageButton'");
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openNavigation();
      }
    });
    view = finder.findRequiredView(source, R.id.best_practices_atm, "field 'mBestPracticesATMText'");
    target.mBestPracticesATMText = finder.castView(view, R.id.best_practices_atm, "field 'mBestPracticesATMText'");
    view = finder.findRequiredView(source, R.id.best_practices_office, "field 'mBestPracticesOfficeText'");
    target.mBestPracticesOfficeText = finder.castView(view, R.id.best_practices_office, "field 'mBestPracticesOfficeText'");
    view = finder.findRequiredView(source, R.id.activity_map_cluster_list_container, "field 'mClusterListLayout'");
    target.mClusterListLayout = finder.castView(view, R.id.activity_map_cluster_list_container, "field 'mClusterListLayout'");
    view = finder.findRequiredView(source, R.id.cluster_list, "field 'mClusterRecyclerView'");
    target.mClusterRecyclerView = finder.castView(view, R.id.cluster_list, "field 'mClusterRecyclerView'");
    view = finder.findRequiredView(source, R.id.btn_help, "method 'onClickHelp'");
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickHelp();
      }
    });
    view = finder.findRequiredView(source, R.id.button_cluster_close_dialog, "method 'closeClusterDialog'");
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.closeClusterDialog();
      }
    });
  }

  @Override
  public void unbind(T target) {
    target.toolbar = null;
    target.ivLogo = null;
    target.mMakerDetailContainerView = null;
    target.mClosestContainerView = null;
    target.mMakerDetailNameTextView = null;
    target.mMakerDetailAddressTextView = null;
    target.mMakerDetailPhone = null;
    target.mActionShowMyLocationCheckBox = null;
    target.mActionShowCommissions = null;
    target.mActionBrachesAtm = null;
    target.mActionsContainer = null;
    target.mNetworkError = null;
    target.mBtnSearch = null;
    target.mBtnList = null;
    target.mClosestDistance = null;
    target.mLayoutCommission = null;
    target.mClosestCommission = null;
    target.mClosestCommissionDesc = null;
    target.mDetailClosestDistance = null;
    target.mLayoutDetailCommission = null;
    target.mDetailClosestCommission = null;
    target.mDetailClosestCommissionDesc = null;
    target.mShowRouteImageButton = null;
    target.mOpenNavigationImageButton = null;
    target.mBestPracticesATMText = null;
    target.mBestPracticesOfficeText = null;
    target.mClusterListLayout = null;
    target.mClusterRecyclerView = null;
  }
}
