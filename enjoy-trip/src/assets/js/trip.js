// trip.js

function loadMap(sido, gugun, type) {
  // 주소-좌표 변환 객체를 생성합니다
  var geocoder = new kakao.maps.services.Geocoder();

  // sendRequest('detail', regcode, area);

  // 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
  var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

  // 장소 검색 객체를 생성합니다
  var ps = new kakao.maps.services.Places();

  /*
  // 미사용
  // 키워드로 장소를 검색합니다
  const regText = sido + ' ' + gugun;
  var keyword = document.getElementById('info').value;
  ps.keywordSearch(regText + ' ' + keyword, placesSearchCB);

  // 키워드 검색 완료 시 호출되는 콜백함수 입니다
  function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
      // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
      // LatLngBounds 객체에 좌표를 추가합니다
      var bounds = new kakao.maps.LatLngBounds();

      for (var i = 0; i < data.length; i++) {
        displayMarker(data[i]);
        bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
      }

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
      map.setBounds(bounds);
    }
  }
  */
}

export { loadMap };
