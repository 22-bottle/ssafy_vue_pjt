import { serverAxios } from '@/util/http-commons';

const server = serverAxios();

const url = '/board';

function listArticle(param, success, fail) {
  server.get(`${url}/list`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
  server.get(`${url}/view/${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  server.post(`${url}/write`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyArticle(articleno, success, fail) {
  server.get(`${url}/modify/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  server.put(`${url}/modify`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  server.delete(`${url}/delete/${articleno}`).then(success).catch(fail);
}

function writeComment(comment, success, fail) {
  server.post(`${url}/commentAdd`, JSON.stringify(comment)).then(success).catch(fail);
}

function deleteComment(comment, success, fail) {
  server.delete(`${url}/commentDelete/${comment.articleNo}/${comment.commentNo}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
  writeComment,
  deleteComment,
};
