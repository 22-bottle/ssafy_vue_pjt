<script setup>
import axios from 'axios';
import '@/assets/js/trip.js';
import { onMounted, ref, computed } from 'vue';

const mapContainer = ref(null);
// 선택된 값을 추적하기 위한 ref 선언
const selecteSido = ref(null);
const selecteGugun = ref(null);
const selectedSidoCode = ref('');
const selectedGugunCode = ref('');
const selectedTypeCode = ref('0'); // 기본값으로 '0'을 설정
const tripList = ref(null);
let regcode = ref('');
let area = ref('');

onMounted(() => {
  loadMap();
  sendRequest('sido', '', '');
});

function loadMap() {
  // 카카오 맵 스크립트를 동적으로 로드합니다.
  const script = document.createElement('script');
  script.onload = () => initializeMap(); // 스크립트 로드가 완료되면 지도를 초기화합니다.
  script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=b9279a4bc661b8f08442322e95e06d92&autoload=false&libraries=services`;
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
    new window.kakao.maps.Map(mapContainer.value, options);
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
  const url = 'http://localhost:5000/trip/option';
  let params = `regcode_pattern=${selid}&regcode=${regcode}&area=${area}`;

  const data = await axios.get(`${url}?${params}`);
  console.log(data);
  if (selid !== 'detail') {
    addOption(selid, JSON.parse(data.area));
  } else {
    displayTripInfo(data);
  }
  // fetch(`${url}?${params}`)
  //   .then((response) => response.json())
  //   .then((data) => {
  //     if (selid !== 'detail') {
  //       addOption(selid, JSON.parse(data.area));
  //     } else {
  //       displayTripInfo(data);
  //     }
  //   });
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
  data.regcodes.forEach((item) => {
    infoList += `
      <tr>
        <td><img src="${item.img}" width="100px"></td>
        <td>${item.title}</td>
        <td>${item.addr}</td>
      </tr>
    `;
  });
  tripList.value.innerHTML = infoList;
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
          ref="selecteSido"
          class="dropdown-toggle ms-3 me-3 form-select"
        ></select>
        <select
          v-model="selectedGugun"
          ref="selecteGugun"
          class="dropdown-toggle ms-3 me-3 form-select"
        >
          <option value="">구군선택</option>
        </select>
        <select
          v-model="selectedType"
          class="dropdown-toggle ms-3 me-3 form-select"
        >
          <option value="0" selected>관광지 유형</option>
          <option value="12">관광지</option>
          <option value="14">문화시설</option>
          <option value="15">축제공연행사</option>
          <option value="25">여행코스</option>
          <option value="28">레포츠</option>
          <option value="32">숙박</option>
          <option value="38">쇼핑</option>
          <option value="39">음식점</option>
        </select>
        <button class="col-1 btn btn-outline-success" @click="">검색</button>
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
