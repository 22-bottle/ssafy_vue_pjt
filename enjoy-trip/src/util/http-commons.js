import axios from 'axios';

const { VITE_SERVER_URL } = import.meta.env;

// axios instance
function serverAxios() {
  const instance = axios.create({
    baseURL: VITE_SERVER_URL,
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  return instance;
}

export { serverAxios };
