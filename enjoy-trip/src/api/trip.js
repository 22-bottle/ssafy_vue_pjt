import { serverAxios } from '@/util/http-commons';

const server = serverAxios();

function getSiGungu(param) {
  return server.get(`/trip/option?${param}`);
}

export { getSiGungu };
