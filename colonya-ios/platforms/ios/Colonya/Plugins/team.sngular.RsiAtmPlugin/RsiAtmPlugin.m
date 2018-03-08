#import "RsiAtmPlugin.h"
#import <Cordova/CDV.h>
#import <RsiAtm/RsiAtm.h>

@implementation RsiAtmPlugin

- (void)init:(CDVInvokedUrlCommand*)command{
    NSLog(@"init! Rsi");

    NSMutableDictionary *options = [command.arguments objectAtIndex:0];
    NSString *iOSKey = [options objectForKey:@"googleMapsiOSKey"];
    NSString *webKey = [options objectForKey:@"googleMapsWebKey"];
    NSString *entity = [options objectForKey:@"entity"];
    NSString *atmsURL = [options objectForKey:@"atmsURL"];
    NSString *officesURL = [options objectForKey:@"officesURL"];
    NSString *entityPlaceHelpTitle = [options objectForKey:@"entityPlaceHelpTitle"];
    BOOL showGroupATMs = [[options objectForKey:@"showGroupATMs"] boolValue];

    [RsiAtm launchRsiAtmWithGoogleMapsWebKey:webKey
                            googleMapsiOSKey:iOSKey
                                      entity:entity
                                     atmsURL:atmsURL
                                  officesURL:officesURL
                        entityPlaceHelpTitle:entityPlaceHelpTitle
                               showGroupAtms:showGroupATMs];

    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"Initialization successful"];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


@end
