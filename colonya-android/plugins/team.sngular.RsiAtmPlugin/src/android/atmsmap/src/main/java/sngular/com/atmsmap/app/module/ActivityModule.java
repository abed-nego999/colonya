package sngular.com.atmsmap.app.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sngular.com.atmsmap.app.annotation.ForActivity;
import sngular.com.atmsmap.presentation.view.activity.BaseActivity;
import sngular.com.atmsmap.presentation.view.activity.MapActivity;
import sngular.com.atmsmap.presentation.view.fragment.ATMListDialog;
import sngular.com.atmsmap.presentation.view.fragment.MapFragment;
import sngular.com.atmsmap.presentation.view.fragment.OfficeListDialog;


@Module(
        injects = {
                // Activities
                MapActivity.class,
                // Fragments
                MapFragment.class,
                ATMListDialog.class,
                OfficeListDialog.class
        },
        addsTo = AndroidModule.class,
        library = true
)
public class ActivityModule {
    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * Allow the activity context to be injected but require that it be annotated with
     * {@link ForActivity @ForActivity} to explicitly differentiate it from application context.
     */
    @Provides
    @Singleton
    @ForActivity
    Context provideActivityContext() {
        return activity;
    }

}
