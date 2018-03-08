// Generated code from Butter Fork. Do not modify!
package sngular.com.atmsmap.presentation.view.fragment;

import android.view.View;
import butterfork.ButterFork;
import butterfork.internal.DebouncingOnClickListener;
import java.lang.Object;
import java.lang.Override;
import sngular.com.atmsmap.R;

public class DialogHelpFragment$$ViewBinder<T extends DialogHelpFragment> implements ButterFork.ViewBinder<T> {
  @Override
  public void bind(final ButterFork.Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, R.id.same_entity_modal_text, "field 'mSameEntityText'");
    target.mSameEntityText = finder.castView(view, R.id.same_entity_modal_text, "field 'mSameEntityText'");
    view = finder.findRequiredView(source, R.id.same_office_modal_text, "field 'mSameOfficeText'");
    target.mSameOfficeText = finder.castView(view, R.id.same_office_modal_text, "field 'mSameOfficeText'");
    view = finder.findRequiredView(source, R.id.group_entity_modal_text, "field 'mGroupEntityText'");
    target.mGroupEntityText = finder.castView(view, R.id.group_entity_modal_text, "field 'mGroupEntityText'");
    view = finder.findRequiredView(source, R.id.dialog_help_close, "method 'onDialogAccept'");
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDialogAccept();
      }
    });
  }

  @Override
  public void unbind(T target) {
    target.mSameEntityText = null;
    target.mSameOfficeText = null;
    target.mGroupEntityText = null;
  }
}
