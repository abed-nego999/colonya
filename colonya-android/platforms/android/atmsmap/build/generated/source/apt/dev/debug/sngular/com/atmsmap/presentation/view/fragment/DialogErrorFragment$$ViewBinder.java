// Generated code from Butter Fork. Do not modify!
package sngular.com.atmsmap.presentation.view.fragment;

import android.view.View;
import butterfork.ButterFork;
import butterfork.internal.DebouncingOnClickListener;
import java.lang.Object;
import java.lang.Override;
import sngular.com.atmsmap.R;

public class DialogErrorFragment$$ViewBinder<T extends DialogErrorFragment> implements ButterFork.ViewBinder<T> {
  @Override
  public void bind(final ButterFork.Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, R.id.dialog_error_title, "field 'dialog_title'");
    target.dialog_title = finder.castView(view, R.id.dialog_error_title, "field 'dialog_title'");
    view = finder.findRequiredView(source, R.id.dialog_error_subtitle, "field 'dialog_subtitle'");
    target.dialog_subtitle = finder.castView(view, R.id.dialog_error_subtitle, "field 'dialog_subtitle'");
    view = finder.findRequiredView(source, R.id.dialog_error_accept, "field 'dialog_accept' and method 'onDialogAccept'");
    target.dialog_accept = finder.castView(view, R.id.dialog_error_accept, "field 'dialog_accept'");
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDialogAccept();
      }
    });
  }

  @Override
  public void unbind(T target) {
    target.dialog_title = null;
    target.dialog_subtitle = null;
    target.dialog_accept = null;
  }
}
