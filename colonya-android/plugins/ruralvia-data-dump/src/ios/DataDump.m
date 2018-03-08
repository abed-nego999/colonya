#import "DataDump.h"

@interface DataDump()

@property (nonatomic) NSUserDefaults *userDefaults;
@property (nonatomic) NSDictionary *userListDic;

@end

@implementation DataDump

- (NSDictionary *)volcado:(CDVInvokedUrlCommand*) command {
    
    self.userDefaults = [NSUserDefaults standardUserDefaults];
    self.userListDic = [self.userDefaults valueForKey:@"userlist"];
    
    if ([self.userDefaults arrayForKey:@"userlist"] != nil) {
        NSMutableArray *userListArray = [[NSMutableArray alloc] init];
        NSMutableArray *userList = [[NSMutableArray alloc] init];
        [userListArray addObjectsFromArray:[self.userDefaults arrayForKey:@"userlist"]];
        
        for (id user in userListArray)  {
            NSDictionary *userDict = @{
                                       @"CR": @1,
                                       @"Entidad": user[@"entity"],
                                       @"campoCifrado": user[@"token"],
                                       @"pushActivo": user[@"notifications"],
                                       @"usuario": user[@"userId"]
                                      };
            [userList addObject:userDict];
        }
        
        NSDictionary *data = @{
                              @"list_users": userList,
                              @"active_user": [self getUserActive]
                             };
        
        NSLog(@"DataDump -> volcado() -> data: %@", data);
        return data;
    } else {
        return nil;
    }
}


- (NSObject *)getUserActive {
    
    for(id user in self.userListDic)  {
        if ([[user valueForKey:@"active"]  isEqual: @1])    {
            NSLog(@"DataDump -> getUserActive() -> user active: %@", user);
            NSDictionary *userDict = @{
                                       @"CR": @1,
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