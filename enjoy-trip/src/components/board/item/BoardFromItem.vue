<script setup>
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { registArticle, getModifyArticle, modifyArticle } from '@/api/board';

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);

const article = ref({
  articleNo: 0,
  subject: '',
  content: '',
  userId: '',
  userName: '',
  hit: 0,
  registerTime: '',
});
if (props.type === 'modify') {
  let { articleno } = route.params;
  getModifyArticle(
    articleno,
    ({ data }) => {
      article.value = data.article;
    },
    () => {
      router.push({ name: 'error' });
    }
  );
  isUseId.value = true;
}

const subjectErrMsg = ref('');
const contentErrMsg = ref('');
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = '제목을 확인해 주세요!!!';
    } else subjectErrMsg.value = '';
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = '내용을 확인해 주세요!!!';
    } else contentErrMsg.value = '';
  },
  { immediate: true }
);

function onSubmit() {
  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === 'regist' ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  registArticle(
    article.value,
    () => {
      router.push({ name: 'article-list' });
    },
    () => {
      router.push({ name: 'error' });
    }
  );
}

function updateArticle() {
  modifyArticle(
    article.value,
    () => {
      router.push({ name: 'article-list' });
    },
    () => {
      router.push({ name: 'error' });
    }
  );
}

function moveList() {
  router.push({ name: 'article-list' });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input type="text" class="form-control" v-model="article.user_id" :disabled="isUseId" placeholder="작성자ID..." />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="article.subject" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea class="form-control" v-model="article.content" rows="10"></textarea>
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">글 작성</button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글 수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">글 목록</button>
    </div>
  </form>
</template>

<style scoped></style>
