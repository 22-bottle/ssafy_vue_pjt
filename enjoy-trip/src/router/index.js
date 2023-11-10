import { createRouter, createWebHistory } from 'vue-router';
import TheMainView from '@/components/views/TheMainView.vue';
import TheTripView from '@/components/views/TheTripView.vue';
import TheBoardView from '@/components/views/TheBoardView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: TheMainView,
    },
    {
      path: '/trip',
      name: 'trip',
      component: TheTripView,
    },
    {
      path: '/board',
      name: 'board',
      component: TheBoardView,
    },
  ],
});

export default router;
