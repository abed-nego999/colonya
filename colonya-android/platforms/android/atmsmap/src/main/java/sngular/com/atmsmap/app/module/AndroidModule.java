package sngular.com.atmsmap.app.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sngular.com.atmsmap.app.RSIApplication;
import sngular.com.atmsmap.app.annotation.ForApplication;
import sngular.com.atmsmap.data.rest.GetATMs;
import sngular.com.atmsmap.data.rest.GetATMsService;
import sngular.com.atmsmap.data.rest.GetOffices;
import sngular.com.atmsmap.data.rest.GetOfficesService;
import sngular.com.atmsmap.domain.location.LocationManagerWrapper;
import sngular.com.atmsmap.domain.location.LocationService;
import sngular.com.atmsmap.domain.usercase.UserCase;
import sngular.com.atmsmap.domain.usercase.UserCaseService;
import sngular.com.atmsmap.presentation.info.InfoRSI;
import sngular.com.atmsmap.presentation.info.InfoRSIService;
import sngular.com.atmsmap.presentation.map.MapManager;
import sngular.com.atmsmap.presentation.map.MapService;


@Module(library = true)
public class AndroidModule {

    private final RSIApplication application;

    public AndroidModule(RSIApplication application) {
        this.application = application;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link ForApplication @ForApplication} to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    LocationService provideLocationService() {
        return new LocationManagerWrapper(application);
    }

    @Provides
    @Singleton
    MapService provideMapService() {
        return new MapManager(application, provideUserCaseService());
    }

    @Provides
    @Singleton
    UserCaseService provideUserCaseService() {
        return new UserCase(provideGetATMsService(), provideGetOfficesService());
    }

    @Provides
    @Singleton
    GetATMsService provideGetATMsService() {
        return new GetATMs(application);
    }

    @Provides
    @Singleton
    GetOfficesService provideGetOfficesService() {
        return new GetOffices(application);
    }

    @Provides
    @Singleton
    InfoRSIService provideBankEntityService() {
        return new InfoRSI(application);
    }
}