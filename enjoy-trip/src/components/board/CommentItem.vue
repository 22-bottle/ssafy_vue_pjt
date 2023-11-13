<script setup>
import { useRouter } from 'vue-router';
import { deleteComment } from '@/api/board';

const props = defineProps({ comment: Object });
const router = useRouter();

const onCommentDelete = () => {
  deleteComment(
    props.comment,
    () => {
      router.go(0);
    },
    () => {
      router.push({ name: 'error' });
    }
  );
};
</script>

<template>
  <div class="col-lg-12 col-md-10 col-sm-12">
    <div class="row">
      <div class="row">
        <div class="clearfix align-content-center">
          <img
            class="avatar me-2 float-md-start bg-light p-2"
            src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
          /><span class="fw-bold d-flex justify-content-start">{{ comment.userId }}</span>
          <span class="text-secondary fw-light d-flex justify-content-end">{{ comment.registerTime }}</span>
        </div>
      </div>

      <div class="col-md-10">
        <div class="text-secondary mb-3 d-flex justify-content-start" style="font-size: 20px">
          {{ comment.commentContent }}
        </div>
      </div>

      <div class="col-md-2">
        <button
          type="button"
          id="comment-delete-${comment.commentNo}"
          name="comment-delete"
          article-no="${article.article_no}"
          comment-no="${comment.commentNo}"
          class="btn btn-outline-danger mb-1 ms-1"
          @click="onCommentDelete"
        >
          삭제
        </button>
      </div>

      <hr style="height: 1px; style: dotted; background-color: #fff" />
    </div>
  </div>
</template>

<style scoped></style>
