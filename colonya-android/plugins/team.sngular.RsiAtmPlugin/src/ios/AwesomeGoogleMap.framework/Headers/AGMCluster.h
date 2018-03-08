//
//  AGMCluster.h
//  AwesomeGoogleMap
//
//  Created by Pablo Rueda on 19/4/16.
//  Copyright © 2016 sngular. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <GoogleMaps/GoogleMaps.h>
#import "AGMPlace.h"

@interface AGMCluster : AGMPlace

@property (nonatomic, strong) NSMutableArray *places;
@property (nonatomic, strong) GMSCoordinateBounds *bounds;

- (void)addPlace:(AGMPlace *)place;

@end
