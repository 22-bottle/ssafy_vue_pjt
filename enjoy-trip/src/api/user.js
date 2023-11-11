import { serverAxios } from '@/util/http-commons';

const server = serverAxios();

function login(param) {
  return server.post("/user/login",param);
}

function modify(param) {
  return server.patch("/user/update",param);
}

function discard(param) {
  return server.delete(`/user/delete?userId=${param}`);
}

function regist(param) {
  return server.post("/user/regist",param);
}

export { login, modify, discard, regist };
