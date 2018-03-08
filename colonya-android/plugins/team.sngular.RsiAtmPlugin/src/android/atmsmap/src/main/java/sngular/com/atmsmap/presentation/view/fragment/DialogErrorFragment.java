package sngular.com.atmsmap.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import butterfork.Bind;
import butterfork.ButterFork;
import butterfork.OnClick;
import sngular.com.atmsmap.B;
import sngular.com.atmsmap.R;

/**
 * Created by alberto.hernandez on 28/04/2016.
 */
public class DialogErrorFragment extends DialogFragment {
    @Bind(B.id.dialog_error_title)
    TextView dialog_title;
    @Bind(B.id.dialog_error_subtitle)
    TextView dialog_subtitle;
    @Bind(B.id.dialog_error_accept)
    TextView dialog_accept;

    private static final String DIALOG_ERROR_TITLE = "dialog_error_title";
    private static final String DIALOG_ERROR_SUBTITLE = "dialog_error_subtitle";

    public static DialogErrorFragment newInstance(String title, String subtitle) {
        DialogErrorFragment fragment = new DialogErrorFragment();
        Bundle args = new Bundle();
        args.putString(DIALOG_ERROR_TITLE, title);
        args.putString(DIALOG_ERROR_SUBTITLE, subtitle);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_dialog_error, container, false);
        ButterFork.bind(this, view);
        configure(getArguments());
        return view;

    }

    private void configure(Bundle arguments) {
        dialog_title.setText(arguments.getString(DIALOG_ERROR_TITLE));
        dialog_subtitle.setText(arguments.getString(DIALOG_ERROR_SUBTITLE));
    }

    @OnClick(B.id.dialog_error_accept)
    public void onDialogAccept() {
        dismiss();
    }
}
