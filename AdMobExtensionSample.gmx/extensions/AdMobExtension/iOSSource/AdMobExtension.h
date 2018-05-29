#import <GoogleMobileAds/GoogleMobileAds.h>

@interface AdMobExtension : NSObject<GADInterstitialDelegate, GADRewardBasedVideoAdDelegate>
{
}

@property(nonatomic, strong) NSString *test_device_id;
@property(nonatomic, strong) NSString *interstitial_ad_id;
@property(nonatomic, strong) NSString *rewarded_video_ad_id;
@property(nonatomic, strong) GADInterstitial *interstitial;

- (double) admob_init:(char *)app_id;
- (double) admob_set_test_device_id:(char *)test_device_id;
- (double) admob_init_interstitial_ad:(char *)ad_id;
- (double) admob_show_interstitial_ad;
- (double) admob_init_rewarded_video_ad:(char *)ad_id;
- (double) admob_show_rewarded_video_ad;

@end