//
//  AwesomeGoogleMapViewController.h
//  AwesomeGoogleMap
//
//  Created by Pablo Rueda on 6/4/16.
//  Copyright © 2016 sngular. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <GoogleMaps/GoogleMaps.h>
#import "AGMPlace.h"
#import "AGMCluster.h"

//El delegado de AGM debe implementar este protocolo
@protocol AwesomeGoogleMapViewControllerDelegate<NSObject>

- (void)currentLocationUpdated:(CLLocation *)currentLocation;

- (void)didChangeCameraPosition:(CLLocationCoordinate2D)position;

- (void)didSelectPlace:(AGMPlace *)place;

- (void)didDeselectPlace:(AGMPlace *)place;

@optional

- (void)calculateRouteFromOrigin:(CLLocationCoordinate2D)origin destination:(CLLocationCoordinate2D)destination;

- (UIImage *)imageForCluster:(AGMCluster *)cluster;

- (void)didSelectCluster:(AGMCluster *)cluster;

- (void)didTapOutside;

- (void)cameraAnimationDidStop;

@end

//Protocolo con el que se usará la clase. La clase cliente debe usar este protocolo, y no la instancia.
@protocol AwesomeGoogleMapViewController <NSObject>

@property (nonatomic, weak) id<AwesomeGoogleMapViewControllerDelegate> mapDelegate;

/** Establece la posición inicial del mapa. El zoom es el valor de google que va entre 0 (cercano) a 23 (alejado) */
- (void)startMapWithDefaultLatitude:(float)latitude longitude:(float)longitude zoom:(NSInteger)zoom;

/** Agrega al mapa un array de lugares de tipo AGMPlace para que se vean como marcadores */
- (void)drawPlaces:(NSArray *)places;

/** Agrega al mapa una ruta definida como un array de tipos CLLocation */
- (void)drawRoute:(NSArray *)route;

/** Radio del mapa visible en el dispositivo */
- (NSInteger)visibleRadius;

/** Cantidad proporcional respecto al radio que debe incrementarse el zoom para que sea relevante el cambio de cámara */
@property (nonatomic, assign) float minRadiusChangeForCameraNotifications;

/** Cantidad proporcional respecto al radio que nos debemos mover para que sea relevante el cambio de cámara */
@property (nonatomic, assign) float minDistanceChangeForCameraNotifications;

/** Color de la ruta */
@property (nonatomic, strong) UIColor *routeColor;

/** Grosor de la ruta */
@property (nonatomic, assign) float routeStrokeWidth;

/** Grosor de la ruta */
@property (nonatomic, assign) UIEdgeInsets routeEdgeInset;

/** Localización actual de la cámara */
@property (nonatomic, readonly) CLLocationCoordinate2D currentCameraPosition;

/** Localización actual del usuario */
@property (nonatomic, readonly) CLLocation *currentLocation;

/** Zoom actual del mapa */
@property (nonatomic, readonly) float currentZoom;

/** Centra el mapa en la localización actual del usuario */
- (void)showCurrentLocation;

/** Centra la cámara en la posición y radio en metros pasados */
- (void)showPosition:(CLLocationCoordinate2D)position radius:(NSInteger)radius;

/** Selecciona un lugar en el mapa, creando el marcador apropiado si no existía el lugar */
- (void)selectPlace:(AGMPlace *)place;

- (void)selectPlace:(AGMPlace *)place fromCluster:(AGMCluster *)cluster;

/** Deselecciona el lugar del mapa seleccionado */
- (void)deselectPlace;

/** Agrega un marcador permanente al mapa que no se elimina al dibujar nuevos a través del drawPlaces */
- (void)addPermanentPlace:(AGMPlace *)place;

/** Elimina un marcador permanente del mapa */
- (void)removePermanentPlace:(AGMPlace *)place;

/** Indica si queremos agrupación de puntos en el mapa */
- (void)setUseClusters:(BOOL)useClusters;

/** Hace zoom in sobre el lugar o cluster pasado */
- (void)zoomInPlace:(AGMPlace *)place;

@end

//Implementación del protocolo AwesomeGoogleMapViewController
@interface AwesomeGoogleMapViewController : UIViewController<AwesomeGoogleMapViewController, GMSMapViewDelegate> {
    BOOL _firstLocationUpdate;
    UIColor *_routeColor;
    float _routeStrokeWidth;
    UIEdgeInsets _routeEdgeInset;
}

@property (nonatomic, weak) IBOutlet GMSMapView *mapView;
@property (nonatomic, weak) id<AwesomeGoogleMapViewControllerDelegate> mapDelegate;
@property (nonatomic, assign) NSInteger initialZoom;
@property (nonatomic, strong) NSMutableDictionary *places;
@property (nonatomic, strong) NSMutableDictionary *permanentPlaces;
@property (nonatomic, strong) GMSMarker *selectedMarker;
@property (nonatomic, strong) GMSPolyline *currentRoute;
@property (nonatomic, strong) CLLocation *lastRelevantLocation;
@property (nonatomic, assign) NSInteger lastRelevantRadius;
@property (nonatomic, assign) float minRadiusChangeForCameraNotifications;
@property (nonatomic, assign) float minDistanceChangeForCameraNotifications;
@property (nonatomic, strong) UIColor *routeColor;
@property (nonatomic, assign) float routeStrokeWidth;
@property (nonatomic, assign) UIEdgeInsets routeEdgeInset;
@property (nonatomic, assign) BOOL useClusters;

- (GMSMarker *)addPlace:(AGMPlace *)place;

@end
