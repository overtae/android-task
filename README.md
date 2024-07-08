<br><br>

<p align="center">
<img src="https://github.com/overtae/android-task/assets/51291185/2570f8c8-8927-425d-abbf-c19dc0753f2c" alt="chzzk-logo" />
</p>
<h3 align="center">치지직 앱 클론 코딩</h3>
<br><br>

## 소개

> 네이버에서 운영하는 인터넷 방송 플랫폼 ["치지직"](https://chzzk.naver.com/) 앱의 UI만 클론 

## 주요 화면

<div align=center width="100%">
<table width="100%">
  <tr align=center>
    <th width="33%">메인 화면</td>
    <th width="33%">검색 화면</td>
    <th width="33%">사진 업로드 화면</td>
  </tr>
  <tr align=center>
    <td><img src="https://github.com/overtae/android-task/assets/51291185/2a80557e-9d7c-49d9-aec5-6ed5f8bb4868" /></td>
    <td><img src="https://github.com/overtae/android-task/assets/51291185/a5543397-069c-41e9-af15-fb8b8ca6e7c1" /></td>
    <td><img src="https://github.com/overtae/android-task/assets/51291185/057c20a8-7833-4445-90bc-08a03de9ad3e" /></td>
  </tr>
</table>
</div>

## 사용 기술

* `TabLayout`, `BottomNavigationView`, `Menu`: 화면 상하단 메뉴 구현
* `RecyclerView`: 반복되는 뷰 관리
* `Intent`:
  - 명시적: 메인 → 검색 화면 넘어갈 때 사용
  - 암묵적: 기기의 카메라를 호출할 때 사용

## 트러블 슈팅

> ⚠️ 이미지 피커 사용시 권한 중에서 `gms.metadata.ModuleDependencies`를 찾을 수 없다는 에러 발생
>
> ⇒ ✅ `tools:ignore="MissingClass"` 추가. IDE에서는 에러 표시가 나지만 컴파일과 실행은 정상 작동

> ⚠️ 세로 `RecyclerView` 내부에 가로 `RecyclerView`를 어떻게 포함시키지?
> 
> ⇒ ✅ 가로 RecyclerView 레이아웃을 세로 RecyclerView가 렌더링하게 구현

