<script setup>
import { loadMap } from '@/assets/js/trip.js';
import { onMounted, ref, computed } from 'vue';
import { getSiGungu } from '@/api/trip';

const mapContainer = ref(null);
// 선택된 값을 추적하기 위한 ref 선언
const selecteSido = ref(null);
const selecteGugun = ref(null);
const selectedSidoCode = ref('');
const selectedGugunCode = ref('');
const selectedTypeCode = ref('');
const selectedSidoName = ref('');
const selectedGugunName = ref('');
const selectedTypeName = ref('');
const tripList = ref(null);
let map = ref(null);
let regcode = '';
var markers = [];
var mLength = 0;
var loc = {
  y: 0,
  x: 0,
};

const key = import.meta.env.VITE_KAKAO_MAP_KEY;

onMounted(() => {
  setMap();
  sendRequest('sido', '', '');
});

function setMap() {
  // 카카오 맵 스크립트를 동적으로 로드합니다.
  const script = document.createElement('script');
  script.onload = () => initializeMap(); // 스크립트 로드가 완료되면 지도를 초기화합니다.
  script.src = key;
  document.head.appendChild(script);
}

function initializeMap() {
  // 카카오 맵 API가 로드된 후 호출될 함수입니다.
  window.kakao.maps.load(() => {
    const options = {
      center: new window.kakao.maps.LatLng(37.5665, 126.978), // 지도의 중심좌표
      level: 3, // 지도의 확대 레벨
    };
    // 지도를 생성합니다.
    map = new window.kakao.maps.Map(mapContainer.value, options);
  });
}

// 선택된 시도 코드를 추적하는 computed 속성
const selectedSido = computed({
  get: () => selectedSidoCode.value,
  set: (newValue) => {
    selectedSidoCode.value = newValue;
    sendRequest('gugun', newValue, '');
  },
});

// 선택된 구군 코드를 추적하는 computed 속성
const selectedGugun = computed({
  get: () => selectedGugunCode.value,
  set: (newValue) => {
    selectedGugunCode.value = newValue;
  },
});

// 선택된 유형 코드를 추적하는 computed 속성
const selectedType = computed({
  get: () => selectedTypeCode.value,
  set: (newValue) => {
    selectedTypeCode.value = newValue;
  },
});

async function sendRequest(selid, regcode, area) {
  let params = `regcode_pattern=${selid}&regcode=${regcode}&area=${area}`;

  const response = await getSiGungu(params);
  if (selid !== 'detail') {
    addOption(selid, JSON.parse(response.data.area));
  } else {
    displayTripInfo(response.data);
  }
}

function addOption(selid, data) {
  let options;
  let opt = ``;

  if (selid === 'sido') {
    options = selecteSido.value;
    opt += `<option value="">시도선택</option>`;
  } else if (selid === 'gugun') {
    options = selecteGugun.value;
    opt += `<option value="">구군선택</option>`;
  }

  data.regcodes.forEach((regcode) => {
    opt += `<option value="${regcode.code}">${regcode.name}</option>`;
  });

  options.innerHTML = opt;
}

function initOption(selectElement) {
  selectElement.length = 0;
}

function displayTripInfo(data) {
  let infoList = ``;
  // 기존 마커를 제거합니다.
  for (let i = 0; i < mLength; i++) {
    markers[i].setMap(null);
  }
  markers = [];
  mLength = 0;
  loc.y = 0;
  loc.x = 0;

  data.forEach((item) => {
    let place = {
      y: item.latitude,
      x: item.longitude,
      place_url: 'place_url',
      place_name: item.title,
      road_address_name: 'road_address_name',
    };
    displayMarker(place);
    infoList += `
      <tr>
        <td><img src="${item.firstImage}" width="100px"></td>
        <td>${item.title}</td>
        <td>${item.addr1}</td>
      </tr>
    `;
  });

  tripList.value.innerHTML = infoList;

  // 표시된 마커들의 중심좌표로 지도를 이동시킵니다.
  // 이동할 위도 경도 위치를 생성합니다
  if (mLength !== 0) {
    var moveLatLon = new kakao.maps.LatLng(loc.y / mLength, loc.x / mLength);
    // 지도 중심을 이동 시킵니다
    map.setCenter(moveLatLon);
  }
}

function selected(event, param) {
  if (param === 1) {
    selectedSidoName.value = event.target[event.target.value].text;
  } else if (param === 2) {
    selectedGugunName.value = event.target[event.target.value].text;
  } else {
    selectedTypeName.value = event.target.value;
  }
}

function search() {
  console.log(selectedSido.value, selectedType.value);
  regcode = `detail ${selectedSido.value} ${selectedType.value}`;
  sendRequest('detail', regcode, '');
  loadMap(
    selectedSidoName.value,
    selectedGugunName.value,
    selectedTypeName.value
  );
}

// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {
  let y = place.y;
  let x = place.x;

  // 마커를 생성하고 지도에 표시합니다
  let marker = new kakao.maps.Marker({
    map: map.value,
    position: new kakao.maps.LatLng(y, x),
  });

  // 마커가 지도 위에 표시되도록 설정합니다
  marker.setMap(map);
  markers[mLength++] = marker;
  loc.y += y;
  loc.x += x;

  // 마커에 클릭이벤트를 등록합니다
  kakao.maps.event.addListener(marker, 'click', function () {
    // 클릭시 상세정보 페이지로 이동합니다.
    var link = place.place_url;
    window.open(link, '_blank');
  });

  // 마커 호버
  var iwContent = `
    <div class="container" style="padding-top: 10px; padding-bottom: 20px; width: 300px; height: 200px">
    <div>관광지명: ${place.place_name}</div>
  `;
  if (place.road_address_name == '') iwContent += `</div>`;
  else {
    iwContent += `도로명 주소: ${place.road_address_name}
    </div>
    `;
  }
  // 인포윈도우를 생성합니다
  let infowindow = new kakao.maps.InfoWindow({
    content: iwContent,
  });

  // 마커에 마우스오버 이벤트를 등록합니다
  kakao.maps.event.addListener(marker, 'mouseover', function () {
    // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
    infowindow.open(map, marker);
  });

  // 마커에 마우스아웃 이벤트를 등록합니다
  kakao.maps.event.addListener(marker, 'mouseout', function () {
    // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
    infowindow.close();
  });
}
</script>

<template>
  <div class="container">
    <!-- 지역별 여행지 선택 -->
    <div style="margin-top: 100px">
      <h2 class="d-flex justify-content-center mt-5 custom-font">지역 선택</h2>
      <div class="dropdown d-flex justify-content-center mt-3 mb-3">
        <select
          v-model="selectedSido"
          @change="selected($event, 1)"
          ref="selecteSido"
          class="dropdown-toggle ms-3 me-3 form-select"
        ></select>
        <select
          v-model="selectedGugun"
          @change="selected($event, 2)"
          ref="selecteGugun"
          class="dropdown-toggle ms-3 me-3 form-select"
        >
          <option value="">구군선택</option>
        </select>
        <select
          v-model="selectedType"
          @change="selected($event, 3)"
          class="dropdown-toggle ms-3 me-3 form-select"
        >
          <option value="">관광지 유형</option>
          <option value="12">관광지</option>
          <option value="14">문화시설</option>
          <option value="15">축제공연행사</option>
          <option value="25">여행코스</option>
          <option value="28">레포츠</option>
          <option value="32">숙박</option>
          <option value="38">쇼핑</option>
          <option value="39">음식점</option>
        </select>
        <button class="col-1 btn btn-outline-success" @click="search()">
          검색
        </button>
      </div>
    </div>

    <!-- 카카오 맵 -->
    <div ref="mapContainer" style="width: 100%; height: 700px"></div>

    <!-- 여행지 상세정보 -->
    <div class="row">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>대표이미지</th>
            <th>관광지명</th>
            <th>주소</th>
          </tr>
        </thead>
        <tbody ref="tripList"></tbody>
      </table>
    </div>
  </div>
</template>

<style scoped></style>
