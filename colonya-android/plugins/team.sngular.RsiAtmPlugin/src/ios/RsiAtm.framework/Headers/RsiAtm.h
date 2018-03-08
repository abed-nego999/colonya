//
//  RsiAtm.h
//  RsiAtm
//
//  Created by Pablo Rueda on 5/4/16.
//  Copyright © 2016 sngular. All rights reserved.
//

#import <Foundation/Foundation.h>

//! Project version number for RsiAtm.
FOUNDATION_EXPORT double RsiAtmVersionNumber;

//! Project version string for RsiAtm.
FOUNDATION_EXPORT const unsigned char RsiAtmVersionString[];

// In this header, you should import all the public headers of your framework using statements like #import <RsiAtm/PublicHeader.h>

@interface RsiAtm : NSObject

+ (void)launchRsiAtmWithGoogleMapsWebKey:(NSString *)googleMapsWebKey googleMapsiOSKey:(NSString *)googleMapsiOSKey entity:(NSString *)entity
                                 atmsURL:(NSString *)atmsURL officesURL:(NSString *)officesURL entityPlaceHelpTitle:(NSString *)entityPlaceHelpTitle showGroupAtms:(BOOL)showGroupATMs;

@end
