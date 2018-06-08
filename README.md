# GMS-AdMob-extension
GameMaker: Studio 용 AdMob 광고 익스텐션 소스 코드 및 샘플입니다. 전면 광고와 보상형 영상 광고를 사용할 수 있습니다. Android와 iOS 환경에서 실행 가능합니다.

***GMS1, GMS2 둘 다 사용 가능합니다. 단, 샘플 프로젝트는 GMS1로 개발되어 있습니다.***

## 설치 방법
Extensions에서 `AdMobExtension.gmez`를 Import 합니다.

## iOS 설정
https://developers.google.com/admob/ios/quick-start 페이지에 접속하여 Import the Mobile Ads SDK 항목을 따라 합니다.

이후 프레임워크 목록 중 `libPod-프로젝트 명.a`을 삭제합니다.

## Android 빌드 시 오류가 나는 경우
GameMaker: Studio의 GooglePlayServicesExtension과 Google Play Service 라이브러리 버전 오류로 인해 빌드 시 오류가 날 수 있습니다. 이런 경우에는 AdMobExtension의 Android 탭의 Inject to Gradle dependencies 항목에서 `compile 'com.google.android.gms:play-services-ads:15.0.0'`를 삭제하고 다시 빌드하면 됩니다.

## 함수
* `admob_init(app_id)` 익스텐션을 초기화합니다.
* `admob_set_test_device_id(test_device_id)` 테스트 기기 ID를 추가합니다. 운영시에는 이 함수 호출을 제거합니다.
* `admob_init_interstitial_ad(ad_id)` 전면 광고를 초기화합니다.
* `admob_show_interstitial_ad()` 전명 광고를 출력합니다.
* `admob_init_rewarded_video_ad(ad_id)` 보상형 영상 광고를 초기화합니다.
* `admob_show_rewarded_video_ad()` 보상형 영상 광고를 출력합니다.

## 테스트 기기 등록
일단 익스텐션이 적용된 게임을 실행합니다. 그러면 다음과 같은 메시지가 Logcat에 나타날 것입니다.
```
I/Ads: Use AdRequest.Builder.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
to get test ads on this device."
```
여기서 `33BE2250B43518CCDA7DE426D04EE231`이 부분은 기기마다 다릅니다. 이 부분을 복사하여 `admob_set_test_device_id` 함수로 등록합니다.

```gml
admob_set_test_device_id('33BE2250B43518CCDA7DE426D04EE231');
```

이후 테스트 광고가 잘 뜨는 것을 확인할 수 있습니다.
```gml
if (ds_map_find_value(async_load, 'type') == 'admob_rewarded') {
    show_message('광고 시청 완료!');
}
```

## 라이센스
[MIT](LICENSE)

## 작성자
[Young Jae Sim](https://github.com/Hanul)
