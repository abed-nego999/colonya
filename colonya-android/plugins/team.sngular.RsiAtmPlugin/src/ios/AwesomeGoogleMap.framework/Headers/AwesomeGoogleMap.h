//
//  AwesomeGoogleMap.h
//  AwesomeGoogleMap
//
//  Created by Pablo Rueda on 6/4/16.
//  Copyright © 2016 sngular. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AwesomeGoogleMapViewController.h"
#import "AGMPlace.h"

//! Project version number for AwesomeGoogleMap.
FOUNDATION_EXPORT double AwesomeGoogleMapVersionNumber;

//! Project version string for AwesomeGoogleMap.
FOUNDATION_EXPORT const unsigned char AwesomeGoogleMapVersionString[];

// In this header, you should import all the public headers of your framework using statements like #import <AwesomeGoogleMap/PublicHeader.h>


@interface AwesomeGoogleMap : NSObject

+ (void)provideGoogleMapsAPIKey:(NSString *)googleAPIKey;

@end