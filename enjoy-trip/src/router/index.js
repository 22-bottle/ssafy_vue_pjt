import { createRouter, createWebHistory } from 'vue-router';
import TheMainView from '@/components/views/TheMainView.vue';
import TheTripView from '@/components/views/TheTripView.vue';
// import TheBoardView from '@/components/views/TheBoardView.vue';
import TheErrorView from '@/views/TheErrorView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: TheMainView,
    },
    {
      path: '/error',
      name: 'error',
      component: TheErrorView,
    },
    {
      path: '/trip',
      name: 'trip',
      component: TheTripView,
    },
    {
      path: '/board',
      name: 'board',
      component: () => import('@/components/views/TheBoardView.vue'),
      redirect: { name: 'article-list' },
      children: [
        {
          path: 'list',
          name: 'article-list',
          component: () => import('@/components/board/TheBoardList.vue'),
        },
        {
          path: 'view/:articleno',
          name: 'article-view',
          component: () => import('@/components/board/TheBoardDetail.vue'),
        },
        {
          path: 'write',
          name: 'article-write',
          component: () => import('@/components/board/TheBoardWrite.vue'),
        },
        {
          path: 'modify/:articleno',
          name: 'article-modify',
          component: () => import('@/components/board/TheBoardModify.vue'),
        },
      ],
    },
  ],
});

export default router;
