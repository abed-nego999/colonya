#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>

@interface DataDump : CDVPlugin

-(NSMutableArray *)volcado:(CDVInvokedUrlCommand*)command;

@end