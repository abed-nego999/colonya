// Code generated by dagger-compiler.  Do not edit.
package sngular.com.atmsmap.presentation.view.activity;

import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

/**
 * A {@code Binding<MapActivity>} implementation which satisfies
 * Dagger's infrastructure requirements including:
 *
 * Owning the dependency links between {@code MapActivity} and its
 * dependencies.
 *
 * Being a {@code Provider<MapActivity>} and handling creation and
 * preparation of object instances.
 *
 * Being a {@code MembersInjector<MapActivity>} and handling injection
 * of annotated fields.
 */
public final class MapActivity$$InjectAdapter extends Binding<MapActivity>
    implements Provider<MapActivity>, MembersInjector<MapActivity> {
  private Binding<sngular.com.atmsmap.presentation.info.InfoRSIService> mInfoRSI;
  private Binding<BaseActivity> supertype;

  public MapActivity$$InjectAdapter() {
    super("sngular.com.atmsmap.presentation.view.activity.MapActivity", "members/sngular.com.atmsmap.presentation.view.activity.MapActivity", NOT_SINGLETON, MapActivity.class);
  }

  /**
   * Used internally to link bindings/providers together at run time
   * according to their dependency graph.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void attach(Linker linker) {
    mInfoRSI = (Binding<sngular.com.atmsmap.presentation.info.InfoRSIService>) linker.requestBinding("sngular.com.atmsmap.presentation.info.InfoRSIService", MapActivity.class, getClass().getClassLoader());
    supertype = (Binding<BaseActivity>) linker.requestBinding("members/sngular.com.atmsmap.presentation.view.activity.BaseActivity", MapActivity.class, getClass().getClassLoader(), false, true);
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getDependencies(Set<Binding<?>> getBindings, Set<Binding<?>> injectMembersBindings) {
    injectMembersBindings.add(mInfoRSI);
    injectMembersBindings.add(supertype);
  }

  /**
   * Returns the fully provisioned instance satisfying the contract for
   * {@code Provider<MapActivity>}.
   */
  @Override
  public MapActivity get() {
    MapActivity result = new MapActivity();
    injectMembers(result);
    return result;
  }

  /**
   * Injects any {@code @Inject} annotated fields in the given instance,
   * satisfying the contract for {@code Provider<MapActivity>}.
   */
  @Override
  public void injectMembers(MapActivity object) {
    object.mInfoRSI = mInfoRSI.get();
    supertype.injectMembers(object);
  }

}
