<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { detailArticle, deleteArticle } from '@/api/board';

const route = useRoute();
const router = useRouter();

const { articleno } = route.params;
const article = ref({});

onMounted(() => {
  getArticle();
});
const getArticle = () => {
  detailArticle(
    articleno,
    ({ data }) => {
      article.value = data.article;
    },
    () => {
      router.push({ name: 'error' });
    }
  );
};

function moveList() {
  router.push({ name: 'article-list' });
}

function moveModify() {
  router.push({ name: 'article-modify', params: { articleno } });
}

function onDeleteArticle() {
  deleteArticle(
    articleno,
    () => {
      router.push({ name: 'article-list' });
    },
    () => {
      router.push({ name: 'error' });
    }
  );
}
</script>

<template>
  <div class="container">
    <div class="col-lg-12 col-md-10 col-sm-12">
      <div class="row my-2">
        <h2 class="text-primary">{{ article.article_no }}. {{ article.subject }}</h2>
      </div>
      <div class="row">
        <div class="col-md-8">
          <div class="clearfix align-content-center">
            <img
              class="avatar me-2 float-md-start bg-light p-2"
              src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
            />
            <p>
              <span class="fw-bold">{{ article.user_id }}</span> <br />
              <span class="text-secondary fw-light">
                {{ article.register_time }} | 조회 : {{ article.hit }} | 댓글 : {{ article.comment_cnt }}</span
              >
            </p>
          </div>
        </div>
        <div class="divider mb-3"></div>
        <div class="text-secondary" style="font-size: 18px">{{ article.content }}</div>
        <div class="divider mt-3 mb-3"></div>
        <div class="d-flex justify-content-end">
          <button type="button" id="btn-list" class="btn btn-outline-primary mb-3" @click="moveList">글목록</button>
          <button type="button" id="btn-mv-modify" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">
            글수정
          </button>
          <button type="button" id="btn-delete" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteArticle">
            글삭제
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
