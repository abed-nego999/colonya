#import "DataDump.h"
#import <Cordova/CDVPlugin.h>

@interface DataDump()

@property (nonatomic) NSUserDefaults *userDefaults;
@property (nonatomic) NSDictionary *userListDic;

@end

@implementation DataDump

- (void)volcado:(CDVInvokedUrlCommand*) command {
    
    CDVPluginResult* pluginResult = nil;
    self.userDefaults = [NSUserDefaults standardUserDefaults];
    self.userListDic = [self.userDefaults valueForKey:@"userlist"];

    if ([self.userDefaults boolForKey:@"volcadoPrimeraVez"] == YES || ![self.userDefaults objectForKey:@"volcadoPrimeraVez"]) {
        if ([self.userDefaults arrayForKey:@"userlist"] != nil) {
            NSMutableArray *userListArray = [[NSMutableArray alloc] init];
            NSMutableArray *userList = [[NSMutableArray alloc] init];
            [userListArray addObjectsFromArray:[self.userDefaults arrayForKey:@"userlist"]];
            
            for (id user in userListArray)  {
                NSDictionary *userDict = @{
                                           @"CR": @"1",
                                           @"Entidad": user[@"entity"],
                                           @"campoCifrado": user[@"token"],
                                           @"pushActivo": user[@"notifications"],
                                           @"usuario": user[@"userId"]
                                           };
                [userList addObject:userDict];
            }
            
            NSDictionary *data = @{
                                   @"resultado": @"ok",
                                   @"list_users": userList,
                                   @"active_user": [self getUserActive]
                                   };            
            
            [[NSUserDefaults standardUserDefaults] setBool:NO forKey:@"volcadoPrimeraVez"];
            
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:data];
            
        } else {
            //return nil;
            NSDictionary *dataResultado = @{
                                            @"resultado": @"fail"
                                            };
            [[NSUserDefaults standardUserDefaults] setBool:NO forKey:@"volcadoPrimeraVez"];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dataResultado];
        }
        
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } else {
        CDVPluginResult* pluginResult = nil;
        NSDictionary *dataResultado = @{
                                        @"resultado": @"fail"
                                        };
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dataResultado];
        
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}


- (NSObject *)getUserActive {
    
    for(id user in self.userListDic)  {
        if ([[user valueForKey:@"active"]  isEqual: @1])    {
            NSLog(@"DataDump -> getUserActive() -> user active: %@", user);
            NSDictionary *userDict = @{
                                       @"CR": @"1",
                                       @"Entidad": user[@"entity"],
                                       @"campoCifrado": user[@"token"],
                                       @"pushActivo": user[@"notifications"],
                                       @"usuario": user[@"userId"]
                                       };
            return userDict;
        }
    }
    
    return nil;
}

@end
