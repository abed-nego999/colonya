// Generated code from Butter Fork. Do not modify!
package sngular.com.atmsmap.presentation.adapter;

import android.view.View;
import butterfork.ButterFork;
import java.lang.Object;
import java.lang.Override;
import sngular.com.atmsmap.R;

public class OfficePresentationAdapter$OfficePresentationViewHolder$$ViewBinder<T extends OfficePresentationAdapter.OfficePresentationViewHolder> implements ButterFork.ViewBinder<T> {
  @Override
  public void bind(final ButterFork.Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, R.id.linear_comission_view, "field 'mComisionView'");
    target.mComisionView = finder.castView(view, R.id.linear_comission_view, "field 'mComisionView'");
    view = finder.findRequiredView(source, R.id.entity_text, "field 'mEntityName'");
    target.mEntityName = finder.castView(view, R.id.entity_text, "field 'mEntityName'");
    view = finder.findRequiredView(source, R.id.address_first_text, "field 'mAddress'");
    target.mAddress = finder.castView(view, R.id.address_first_text, "field 'mAddress'");
    view = finder.findRequiredView(source, R.id.address_second_text, "field 'mZipAndCity'");
    target.mZipAndCity = finder.castView(view, R.id.address_second_text, "field 'mZipAndCity'");
    view = finder.findRequiredView(source, R.id.distance_text, "field 'mDistanceText'");
    target.mDistanceText = finder.castView(view, R.id.distance_text, "field 'mDistanceText'");
  }

  @Override
  public void unbind(T target) {
    target.mComisionView = null;
    target.mEntityName = null;
    target.mAddress = null;
    target.mZipAndCity = null;
    target.mDistanceText = null;
  }
}
