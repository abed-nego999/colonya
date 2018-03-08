//
//  AGMPlace.h
//  AwesomeGoogleMap
//
//  Created by Pablo Rueda on 7/4/16.
//  Copyright © 2016 sngular. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface AGMPlace : NSObject<NSCopying>

@property (nonatomic, copy) NSString *name;
@property (nonatomic, assign) float latitude;
@property (nonatomic, assign) float longitude;
@property (nonatomic, copy) NSString *identifier;
@property (nonatomic, strong) UIImage *icon;
@property (nonatomic, strong) UIImage *selectedIcon;

@end
