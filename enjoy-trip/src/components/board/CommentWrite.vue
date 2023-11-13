<script setup>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { writeComment } from '@/api/board';

const props = defineProps({ articleno: String, userid: String });
const router = useRouter();

const comment = ref({
  articleNo: props.articleno,
  userId: props.userid,
  commentContent: '',
});

const contentErrMsg = ref('');
watch(
  () => comment.value.commentContent,
  (value) => {
    let len = value.length;
    if (len == 0) {
      contentErrMsg.value = '내용을 입력해 주세요';
    } else contentErrMsg.value = '';
  },
  { immediate: true }
);

function onSubmit() {
  if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else registerComment();
}

function registerComment() {
  writeComment(
    comment.value,
    () => {
      router.go(0);
    },
    () => {
      router.push({ name: 'error' });
    }
  );
}
</script>

<template>
  <div>
    <div class="col-lg-12 col-md-10 col-sm-12" style="background: #f5f5f5; border: 1px; margin: 10px 0">
      <form id="comment-regist" @submit.prevent="onSubmit">
        <div class="mb-3">
          <div class="container">
            <div class="row">
              <div class="col-md-10">
                <br />
                <textarea
                  class="comment-text form-control"
                  id="comment"
                  name="comment"
                  rows="2"
                  style="padding: 5px; width: 100%; resize: none"
                  placeholder="댓글을 작성해주세요..."
                  v-model="comment.commentContent"
                ></textarea>
                <br />
              </div>

              <div class="d-flex justify-content-end ml-auto col-md-2 h-100">
                <br />
                <button
                  type="submit"
                  id="comment-register"
                  name="comment-register"
                  class="btn btn-outline-primary mt-3 text-end mt-5"
                >
                  댓글 등록
                </button>
                <br />
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
