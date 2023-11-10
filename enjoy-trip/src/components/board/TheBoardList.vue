<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { listArticle } from '@/api/board';

import VSelect from '../common/VSelect.vue';
import BoardListItem from '../board/item/BoardListItem.vue';
import PageNavigation from '../common/PageNavigation.vue';

const router = useRouter();

const selectOption = ref([
  { text: '검색 조건', value: '' },
  { text: '글 번호', value: 'article_no' },
  { text: '제목', value: 'subject' },
  { text: '작성자', value: 'user_id' },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: '',
  word: '',
});

onMounted(() => {
  getArticleList();
});

const getArticleList = () => {
  listArticle(
    param.value,
    ({ data }) => {
      articles.value = data.articles;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    () => {
      router.push({ name: 'error' });
    }
  );
};

const changeKey = (val) => {
  param.value.key = val;
};

const onPageChange = (val) => {
  currentPage.value = val;
  param.value.pgno = val;
  getArticleList();
};

const moveWrite = () => {
  router.push({ name: 'article-write' });
};
</script>

<template>
  <div class="container">
    <div class="col-lg-12 col-md-10 col-sm-12 d-flex flex-column min-vh-50">
      <div class="row mb-2">
        <div class="col-md-7 d-flex justify-content-start">
          <form class="d-flex" id="form-search" action="">
            <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
            <div class="input-group input-group-sm">
              <input
                type="text"
                name="word"
                id="word"
                class="form-control"
                v-model="param.word"
                placeholder="검색어를 입력하세요..."
              />
              <button id="btn-search" class="btn btn-dark" type="button" @click="getArticleList">검색</button>
            </div>
          </form>
        </div>
        <div class="col-md-4 d-flex justify-content-end">
          <button type="button" id="btn-mv-register" class="btn btn-outline-primary btn-sm" @click="moveWrite">
            글쓰기
          </button>
        </div>
      </div>
      <table class="table table-hover">
        <thead>
          <tr class="text-center">
            <th scope="col">글번호</th>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">조회수</th>
            <th scope="col">댓글수</th>
            <th scope="col">작성일</th>
          </tr>
        </thead>
        <tbody>
          <BoardListItem
            v-for="(article, index) in articles"
            :key="article.articleNo"
            :article="article"
          ></BoardListItem>
        </tbody>
      </table>
    </div>
    <PageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></PageNavigation>
  </div>
</template>

<style scoped></style>
