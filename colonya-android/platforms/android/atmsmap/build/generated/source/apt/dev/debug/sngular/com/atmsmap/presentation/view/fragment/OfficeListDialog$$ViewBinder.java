// Generated code from Butter Fork. Do not modify!
package sngular.com.atmsmap.presentation.view.fragment;

import android.view.View;
import butterfork.ButterFork;
import butterfork.internal.DebouncingOnClickListener;
import java.lang.Object;
import java.lang.Override;
import sngular.com.atmsmap.R;

public class OfficeListDialog$$ViewBinder<T extends OfficeListDialog> implements ButterFork.ViewBinder<T> {
  @Override
  public void bind(final ButterFork.Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, R.id.office_list, "field 'mOfficeRecyclerView'");
    target.mOfficeRecyclerView = finder.castView(view, R.id.office_list, "field 'mOfficeRecyclerView'");
    view = finder.findRequiredView(source, R.id.list_office_empty, "field 'listEmpty'");
    target.listEmpty = finder.castView(view, R.id.list_office_empty, "field 'listEmpty'");
    view = finder.findRequiredView(source, R.id.button_office_close_dialog, "method 'closeDialog'");
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.closeDialog();
      }
    });
  }

  @Override
  public void unbind(T target) {
    target.mOfficeRecyclerView = null;
    target.listEmpty = null;
  }
}
