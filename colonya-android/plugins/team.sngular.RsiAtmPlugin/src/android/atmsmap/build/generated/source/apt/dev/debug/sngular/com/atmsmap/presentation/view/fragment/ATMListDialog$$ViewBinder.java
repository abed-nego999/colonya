// Generated code from Butter Fork. Do not modify!
package sngular.com.atmsmap.presentation.view.fragment;

import android.view.View;
import butterfork.ButterFork;
import butterfork.internal.DebouncingOnClickListener;
import java.lang.Object;
import java.lang.Override;
import sngular.com.atmsmap.R;

public class ATMListDialog$$ViewBinder<T extends ATMListDialog> implements ButterFork.ViewBinder<T> {
  @Override
  public void bind(final ButterFork.Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, R.id.atm_list, "field 'mATMRecyclerView'");
    target.mATMRecyclerView = finder.castView(view, R.id.atm_list, "field 'mATMRecyclerView'");
    view = finder.findRequiredView(source, R.id.list_atm_empty, "field 'listEmpty'");
    target.listEmpty = finder.castView(view, R.id.list_atm_empty, "field 'listEmpty'");
    view = finder.findRequiredView(source, R.id.button_atm_close_dialog, "method 'closeDialog'");
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.closeDialog();
      }
    });
  }

  @Override
  public void unbind(T target) {
    target.mATMRecyclerView = null;
    target.listEmpty = null;
  }
}
