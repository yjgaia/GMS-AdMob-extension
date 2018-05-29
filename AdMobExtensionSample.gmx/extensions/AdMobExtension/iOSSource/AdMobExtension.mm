#import "AdMobExtension.h"

@implementation AdMobExtension

const int EVENT_OTHER_SOCIAL = 70;
extern int CreateDsMap( int _num, ... );
extern void CreateAsynEventWithDSMap(int dsmapindex, int event_index);

- (double) admob_init:(char *)app_id
{
    [GADMobileAds configureWithApplicationID:[NSString stringWithUTF8String:app_id]];
    return (double)-1;
}
- (double) admob_set_test_device_id:(char *)test_device_id
{
    self.test_device_id = [NSString stringWithUTF8String:test_device_id];
    return (double)-1;
}

- (double) admob_init_interstitial_ad:(char *)ad_id
{
    self.interstitial_ad_id = [NSString stringWithUTF8String:ad_id];
    self.interstitial = [[GADInterstitial alloc] initWithAdUnitID:self.interstitial_ad_id];
    self.interstitial.delegate = self;
    GADRequest *request = [GADRequest request];
    if (self.test_device_id != nil) {
        request.testDevices = @[ self.test_device_id ];
    }
    [self.interstitial loadRequest:request];
    return (double)-1;
}

- (double) admob_show_interstitial_ad
{
    if (self.interstitial.isReady) {
        [self.interstitial presentFromRootViewController:[UIApplication sharedApplication].keyWindow.rootViewController];
    }
    return (double)-1;
}

- (void)interstitialDidDismissScreen:(GADInterstitial *)interstitial {
    self.interstitial = [[GADInterstitial alloc] initWithAdUnitID:self.interstitial_ad_id];
    self.interstitial.delegate = self;
    GADRequest *request = [GADRequest request];
    if (self.test_device_id != nil) {
        request.testDevices = @[ self.test_device_id ];
    }
    [self.interstitial loadRequest:request];
}

- (double) admob_init_rewarded_video_ad:(char *)ad_id
{
    self.rewarded_video_ad_id = [NSString stringWithUTF8String:ad_id];
    [GADRewardBasedVideoAd sharedInstance].delegate = self;
    GADRequest *request = [GADRequest request];
    if (self.test_device_id != nil) {
        request.testDevices = @[ self.test_device_id ];
    }
    [[GADRewardBasedVideoAd sharedInstance] loadRequest:request withAdUnitID:self.rewarded_video_ad_id];
    return (double)-1;
}

- (double) admob_show_rewarded_video_ad
{
    if ([[GADRewardBasedVideoAd sharedInstance] isReady]) {
        [[GADRewardBasedVideoAd sharedInstance] presentFromRootViewController:[UIApplication sharedApplication].keyWindow.rootViewController];
    }
    return (double)-1;
}

- (void)rewardBasedVideoAd:(GADRewardBasedVideoAd *)rewardBasedVideoAd didRewardUserWithReward:(GADAdReward *)reward {
    CreateAsynEventWithDSMap(CreateDsMap(1,
    "type", 0.0, "admob_rewarded", (void *)NULL
    ), EVENT_OTHER_SOCIAL);
}

- (void)rewardBasedVideoAdDidClose:(GADRewardBasedVideoAd *)rewardBasedVideoAd {
    GADRequest *request = [GADRequest request];
    if (self.test_device_id != nil) {
        request.testDevices = @[ self.test_device_id ];
    }
    [[GADRewardBasedVideoAd sharedInstance] loadRequest:request withAdUnitID:self.rewarded_video_ad_id];
}

@end