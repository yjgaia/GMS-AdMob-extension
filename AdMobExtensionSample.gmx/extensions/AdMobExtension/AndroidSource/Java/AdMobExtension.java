package ${YYAndroidPackageName};

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.reward.RewardItem;

import android.util.Log;

import com.yoyogames.runner.RunnerJNILib;

public class AdMobExtension implements RewardedVideoAdListener {
	
	private static final int EVENT_OTHER_SOCIAL = 70;

	private String testDeviceId;
	private InterstitialAd interstitialAd;
	private String rewardedVideoAdId;
	private RewardedVideoAd rewardedVideoAd;

	public double admob_init(String app_id) {
		MobileAds.initialize(RunnerActivity.CurrentActivity, app_id);
		return -1;
	}

	public double admob_set_test_device_id(String test_device_id) {
		testDeviceId = test_device_id;
		return -1;
	}

	public double admob_init_interstitial_ad(final String ad_id) {
		RunnerActivity.ViewHandler.post(new Runnable() {
			public void run() {
				interstitialAd = new InterstitialAd(RunnerActivity.CurrentActivity);
				interstitialAd.setAdUnitId(ad_id);
				if (testDeviceId != null) {
					interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(testDeviceId).build());
				} else {
					interstitialAd.loadAd(new AdRequest.Builder().build());
				}
				interstitialAd.setAdListener(new AdListener() {
					@Override
					public void onAdClosed() {
						if (testDeviceId != null) {
							interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(testDeviceId).build());
						} else {
							interstitialAd.loadAd(new AdRequest.Builder().build());
						}
					}
				});
			}
		});
		return -1;
	}

	public double admob_show_interstitial_ad() {
		RunnerActivity.ViewHandler.post(new Runnable() {
			public void run() {
				if (interstitialAd.isLoaded() == true) {
					interstitialAd.show();
				}
			}
		});
		return -1;
	}

	public double admob_init_rewarded_video_ad(final String ad_id) {
		rewardedVideoAdId = ad_id;
		final RewardedVideoAdListener that = this;
		RunnerActivity.ViewHandler.post(new Runnable() {
			public void run() {
				rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(RunnerActivity.CurrentActivity);
				rewardedVideoAd.setRewardedVideoAdListener(that);
				if (testDeviceId != null) {
					rewardedVideoAd.loadAd(rewardedVideoAdId, new AdRequest.Builder().addTestDevice(testDeviceId).build());
				} else {
					rewardedVideoAd.loadAd(rewardedVideoAdId, new AdRequest.Builder().build());
				}
			}
		});
		return -1;
	}

	public double admob_show_rewarded_video_ad() {
		RunnerActivity.ViewHandler.post(new Runnable() {
			public void run() {
				if (rewardedVideoAd.isLoaded() == true) {
					rewardedVideoAd.show();
				}
			}
		});
		return -1;
	}

	@Override
	public void onRewarded(RewardItem reward) {
		int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
		RunnerJNILib.DsMapAddString(dsMapIndex, "type", "admob_rewarded");
		RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex, EVENT_OTHER_SOCIAL);
	}

	@Override
	public void onRewardedVideoAdLeftApplication() {}

	@Override
	public void onRewardedVideoAdClosed() {
		if (testDeviceId != null) {
			rewardedVideoAd.loadAd(rewardedVideoAdId, new AdRequest.Builder().addTestDevice(testDeviceId).build());
		} else {
			rewardedVideoAd.loadAd(rewardedVideoAdId, new AdRequest.Builder().build());
		}
	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int errorCode) {}

	@Override
	public void onRewardedVideoAdLoaded() {}

	@Override
	public void onRewardedVideoAdOpened() {}

	@Override
	public void onRewardedVideoStarted() {}

	public void onRewardedVideoCompleted() {}
}
