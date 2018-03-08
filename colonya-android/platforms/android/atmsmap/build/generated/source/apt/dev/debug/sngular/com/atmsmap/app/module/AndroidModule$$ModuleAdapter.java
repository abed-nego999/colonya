// Code generated by dagger-compiler.  Do not edit.
package sngular.com.atmsmap.app.module;

import dagger.internal.BindingsGroup;
import dagger.internal.ModuleAdapter;
import dagger.internal.ProvidesBinding;
import javax.inject.Provider;

/**
 * A manager of modules and provides adapters allowing for proper linking and
 * instance provision of types served by {@code @Provides} methods.
 */
public final class AndroidModule$$ModuleAdapter extends ModuleAdapter<AndroidModule> {
  private static final String[] INJECTS = { };
  private static final Class<?>[] STATIC_INJECTIONS = { };
  private static final Class<?>[] INCLUDES = { };

  public AndroidModule$$ModuleAdapter() {
    super(sngular.com.atmsmap.app.module.AndroidModule.class, INJECTS, STATIC_INJECTIONS, false /*overrides*/, INCLUDES, true /*complete*/, true /*library*/);
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getBindings(BindingsGroup bindings, AndroidModule module) {
    bindings.contributeProvidesBinding("@sngular.com.atmsmap.app.annotation.ForApplication()/android.content.Context", new ProvideApplicationContextProvidesAdapter(module));
    bindings.contributeProvidesBinding("sngular.com.atmsmap.domain.location.LocationService", new ProvideLocationServiceProvidesAdapter(module));
    bindings.contributeProvidesBinding("sngular.com.atmsmap.presentation.map.MapService", new ProvideMapServiceProvidesAdapter(module));
    bindings.contributeProvidesBinding("sngular.com.atmsmap.domain.usercase.UserCaseService", new ProvideUserCaseServiceProvidesAdapter(module));
    bindings.contributeProvidesBinding("sngular.com.atmsmap.data.rest.GetATMsService", new ProvideGetATMsServiceProvidesAdapter(module));
    bindings.contributeProvidesBinding("sngular.com.atmsmap.data.rest.GetOfficesService", new ProvideGetOfficesServiceProvidesAdapter(module));
    bindings.contributeProvidesBinding("sngular.com.atmsmap.presentation.info.InfoRSIService", new ProvideBankEntityServiceProvidesAdapter(module));
  }

  /**
   * A {@code Binding<android.content.Context>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<android.content.Context>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideApplicationContextProvidesAdapter extends ProvidesBinding<android.content.Context>
      implements Provider<android.content.Context> {
    private final AndroidModule module;

    public ProvideApplicationContextProvidesAdapter(AndroidModule module) {
      super("@sngular.com.atmsmap.app.annotation.ForApplication()/android.content.Context", IS_SINGLETON, "sngular.com.atmsmap.app.module.AndroidModule", "provideApplicationContext");
      this.module = module;
      setLibrary(true);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<android.content.Context>}.
     */
    @Override
    public android.content.Context get() {
      return module.provideApplicationContext();
    }
  }

  /**
   * A {@code Binding<sngular.com.atmsmap.domain.location.LocationService>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<sngular.com.atmsmap.domain.location.LocationService>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideLocationServiceProvidesAdapter extends ProvidesBinding<sngular.com.atmsmap.domain.location.LocationService>
      implements Provider<sngular.com.atmsmap.domain.location.LocationService> {
    private final AndroidModule module;

    public ProvideLocationServiceProvidesAdapter(AndroidModule module) {
      super("sngular.com.atmsmap.domain.location.LocationService", IS_SINGLETON, "sngular.com.atmsmap.app.module.AndroidModule", "provideLocationService");
      this.module = module;
      setLibrary(true);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<sngular.com.atmsmap.domain.location.LocationService>}.
     */
    @Override
    public sngular.com.atmsmap.domain.location.LocationService get() {
      return module.provideLocationService();
    }
  }

  /**
   * A {@code Binding<sngular.com.atmsmap.presentation.map.MapService>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<sngular.com.atmsmap.presentation.map.MapService>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideMapServiceProvidesAdapter extends ProvidesBinding<sngular.com.atmsmap.presentation.map.MapService>
      implements Provider<sngular.com.atmsmap.presentation.map.MapService> {
    private final AndroidModule module;

    public ProvideMapServiceProvidesAdapter(AndroidModule module) {
      super("sngular.com.atmsmap.presentation.map.MapService", IS_SINGLETON, "sngular.com.atmsmap.app.module.AndroidModule", "provideMapService");
      this.module = module;
      setLibrary(true);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<sngular.com.atmsmap.presentation.map.MapService>}.
     */
    @Override
    public sngular.com.atmsmap.presentation.map.MapService get() {
      return module.provideMapService();
    }
  }

  /**
   * A {@code Binding<sngular.com.atmsmap.domain.usercase.UserCaseService>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<sngular.com.atmsmap.domain.usercase.UserCaseService>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideUserCaseServiceProvidesAdapter extends ProvidesBinding<sngular.com.atmsmap.domain.usercase.UserCaseService>
      implements Provider<sngular.com.atmsmap.domain.usercase.UserCaseService> {
    private final AndroidModule module;

    public ProvideUserCaseServiceProvidesAdapter(AndroidModule module) {
      super("sngular.com.atmsmap.domain.usercase.UserCaseService", IS_SINGLETON, "sngular.com.atmsmap.app.module.AndroidModule", "provideUserCaseService");
      this.module = module;
      setLibrary(true);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<sngular.com.atmsmap.domain.usercase.UserCaseService>}.
     */
    @Override
    public sngular.com.atmsmap.domain.usercase.UserCaseService get() {
      return module.provideUserCaseService();
    }
  }

  /**
   * A {@code Binding<sngular.com.atmsmap.data.rest.GetATMsService>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<sngular.com.atmsmap.data.rest.GetATMsService>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideGetATMsServiceProvidesAdapter extends ProvidesBinding<sngular.com.atmsmap.data.rest.GetATMsService>
      implements Provider<sngular.com.atmsmap.data.rest.GetATMsService> {
    private final AndroidModule module;

    public ProvideGetATMsServiceProvidesAdapter(AndroidModule module) {
      super("sngular.com.atmsmap.data.rest.GetATMsService", IS_SINGLETON, "sngular.com.atmsmap.app.module.AndroidModule", "provideGetATMsService");
      this.module = module;
      setLibrary(true);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<sngular.com.atmsmap.data.rest.GetATMsService>}.
     */
    @Override
    public sngular.com.atmsmap.data.rest.GetATMsService get() {
      return module.provideGetATMsService();
    }
  }

  /**
   * A {@code Binding<sngular.com.atmsmap.data.rest.GetOfficesService>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<sngular.com.atmsmap.data.rest.GetOfficesService>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideGetOfficesServiceProvidesAdapter extends ProvidesBinding<sngular.com.atmsmap.data.rest.GetOfficesService>
      implements Provider<sngular.com.atmsmap.data.rest.GetOfficesService> {
    private final AndroidModule module;

    public ProvideGetOfficesServiceProvidesAdapter(AndroidModule module) {
      super("sngular.com.atmsmap.data.rest.GetOfficesService", IS_SINGLETON, "sngular.com.atmsmap.app.module.AndroidModule", "provideGetOfficesService");
      this.module = module;
      setLibrary(true);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<sngular.com.atmsmap.data.rest.GetOfficesService>}.
     */
    @Override
    public sngular.com.atmsmap.data.rest.GetOfficesService get() {
      return module.provideGetOfficesService();
    }
  }

  /**
   * A {@code Binding<sngular.com.atmsmap.presentation.info.InfoRSIService>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<sngular.com.atmsmap.presentation.info.InfoRSIService>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideBankEntityServiceProvidesAdapter extends ProvidesBinding<sngular.com.atmsmap.presentation.info.InfoRSIService>
      implements Provider<sngular.com.atmsmap.presentation.info.InfoRSIService> {
    private final AndroidModule module;

    public ProvideBankEntityServiceProvidesAdapter(AndroidModule module) {
      super("sngular.com.atmsmap.presentation.info.InfoRSIService", IS_SINGLETON, "sngular.com.atmsmap.app.module.AndroidModule", "provideBankEntityService");
      this.module = module;
      setLibrary(true);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<sngular.com.atmsmap.presentation.info.InfoRSIService>}.
     */
    @Override
    public sngular.com.atmsmap.presentation.info.InfoRSIService get() {
      return module.provideBankEntityService();
    }
  }
}