package sngular.com.atmsmap.presentation.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterfork.Bind;
import butterfork.ButterFork;
import butterfork.OnClick;
import sngular.com.atmsmap.B;
import sngular.com.atmsmap.R;
import sngular.com.atmsmap.app.constant.AppConstants;

/**
 * Created by alberto.hernandez on 28/04/2016.
 */
public class DialogHelpFragment extends DialogFragment {
    @Bind(B.id.same_entity_modal_text)
    TextView mSameEntityText;
    @Bind(B.id.same_office_modal_text)
    TextView mSameOfficeText;
    @Bind(B.id.group_entity_modal_text)
    TextView mGroupEntityText;

    public static DialogHelpFragment newInstance() {
        return new DialogHelpFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_dialog_help, container, false);
        ButterFork.bind(this, view);
        configure();
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_transparent_help)));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }

    private void configure() {
        final SharedPreferences preferences = getContext().getSharedPreferences(AppConstants.APP_PREFS, Context.MODE_PRIVATE);
        String entityHelpTitle = preferences.getString(AppConstants.ENTITY_HELP_TITLE_INTENT, "");
        mSameEntityText.setText(getString(R.string.atms) + " " + entityHelpTitle);
        mSameOfficeText.setText(getString(R.string.offices) + " " + entityHelpTitle);
        boolean showGroups = preferences.getBoolean(AppConstants.ENTITY_HELP_GROUPS_INTENT, true);
        if (showGroups) {
            mGroupEntityText.setVisibility(View.VISIBLE);
        } else {
            mGroupEntityText.setVisibility(View.GONE);
        }

    }

    @OnClick(B.id.dialog_help_close)
    public void onDialogAccept() {
        dismiss();
    }
}
