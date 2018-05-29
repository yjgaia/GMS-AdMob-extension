# GMS-AdMob-extension
GameMaker: Studio 용 AdMob 익스텐션

일단 그냥 실행해봅니다.
그러면 다음과 같은 메시지가 Logcat에 나타날 것입니다.
```
I/Ads: Use AdRequest.Builder.addTestDevice("33BE2250B43518CCDA7DE426D04EE231")
to get test ads on this device."
```
여기서 `33BE2250B43518CCDA7DE426D04EE231`이 부분은 기기마다 다릅니다. 이 부분을 복사하여 `admob_set_test_device_id` 함수로 등록합니다.

```gml
admob_set_test_device_id('33BE2250B43518CCDA7DE426D04EE231');
```

```gml
if (ds_map_find_value(async_load, 'type') == 'admob_rewarded') {
    show_message('광고 시청 완료!');
}
```