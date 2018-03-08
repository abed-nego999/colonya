#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>
#import <Cordova/CDVPlugin.h>

@interface DataDump : CDVPlugin

-(void)volcado:(CDVInvokedUrlCommand*)command;

@end