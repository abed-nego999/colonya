// Generated code from Butter Fork. Do not modify!
package sngular.com.atmsmap.presentation.view.fragment;

import android.view.View;
import butterfork.ButterFork;
import butterfork.internal.DebouncingOnClickListener;
import java.lang.Object;
import java.lang.Override;
import sngular.com.atmsmap.R;

public class SearchFragment$$ViewBinder<T extends SearchFragment> implements ButterFork.ViewBinder<T> {
  @Override
  public void bind(final ButterFork.Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, R.id.search_radius_text, "field 'mRadiusText'");
    target.mRadiusText = finder.castView(view, R.id.search_radius_text, "field 'mRadiusText'");
    view = finder.findRequiredView(source, R.id.search_radius_bar, "field 'mRadiusBar'");
    target.mRadiusBar = finder.castView(view, R.id.search_radius_bar, "field 'mRadiusBar'");
    view = finder.findRequiredView(source, R.id.btn_search, "field 'mSearchButton' and method 'searchWithPlaceRadius'");
    target.mSearchButton = finder.castView(view, R.id.btn_search, "field 'mSearchButton'");
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.searchWithPlaceRadius();
      }
    });
  }

  @Override
  public void unbind(T target) {
    target.mRadiusText = null;
    target.mRadiusBar = null;
    target.mSearchButton = null;
  }
}
