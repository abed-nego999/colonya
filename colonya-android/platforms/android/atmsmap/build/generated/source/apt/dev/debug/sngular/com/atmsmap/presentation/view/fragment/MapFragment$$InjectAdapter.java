// Code generated by dagger-compiler.  Do not edit.
package sngular.com.atmsmap.presentation.view.fragment;

import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

/**
 * A {@code Binding<MapFragment>} implementation which satisfies
 * Dagger's infrastructure requirements including:
 *
 * Owning the dependency links between {@code MapFragment} and its
 * dependencies.
 *
 * Being a {@code Provider<MapFragment>} and handling creation and
 * preparation of object instances.
 *
 * Being a {@code MembersInjector<MapFragment>} and handling injection
 * of annotated fields.
 */
public final class MapFragment$$InjectAdapter extends Binding<MapFragment>
    implements Provider<MapFragment>, MembersInjector<MapFragment> {
  private Binding<sngular.com.atmsmap.domain.location.LocationService> mLocationManagerWrapper;
  private Binding<sngular.com.atmsmap.presentation.map.MapService> mMapManager;
  private Binding<sngular.com.atmsmap.presentation.view.fragment.base.BaseSupportMapFragment> supertype;

  public MapFragment$$InjectAdapter() {
    super("sngular.com.atmsmap.presentation.view.fragment.MapFragment", "members/sngular.com.atmsmap.presentation.view.fragment.MapFragment", NOT_SINGLETON, MapFragment.class);
  }

  /**
   * Used internally to link bindings/providers together at run time
   * according to their dependency graph.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void attach(Linker linker) {
    mLocationManagerWrapper = (Binding<sngular.com.atmsmap.domain.location.LocationService>) linker.requestBinding("sngular.com.atmsmap.domain.location.LocationService", MapFragment.class, getClass().getClassLoader());
    mMapManager = (Binding<sngular.com.atmsmap.presentation.map.MapService>) linker.requestBinding("sngular.com.atmsmap.presentation.map.MapService", MapFragment.class, getClass().getClassLoader());
    supertype = (Binding<sngular.com.atmsmap.presentation.view.fragment.base.BaseSupportMapFragment>) linker.requestBinding("members/sngular.com.atmsmap.presentation.view.fragment.base.BaseSupportMapFragment", MapFragment.class, getClass().getClassLoader(), false, true);
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getDependencies(Set<Binding<?>> getBindings, Set<Binding<?>> injectMembersBindings) {
    injectMembersBindings.add(mLocationManagerWrapper);
    injectMembersBindings.add(mMapManager);
    injectMembersBindings.add(supertype);
  }

  /**
   * Returns the fully provisioned instance satisfying the contract for
   * {@code Provider<MapFragment>}.
   */
  @Override
  public MapFragment get() {
    MapFragment result = new MapFragment();
    injectMembers(result);
    return result;
  }

  /**
   * Injects any {@code @Inject} annotated fields in the given instance,
   * satisfying the contract for {@code Provider<MapFragment>}.
   */
  @Override
  public void injectMembers(MapFragment object) {
    object.mLocationManagerWrapper = mLocationManagerWrapper.get();
    object.mMapManager = mMapManager.get();
    supertype.injectMembers(object);
  }

}
