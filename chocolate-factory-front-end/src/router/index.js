import { createRouter, createWebHashHistory } from 'vue-router';
import AllFactoriesView from '../views/AllFactoriesView';

const routes = [
  {
    path: "/",
    name: "allFactoriesView",
    component: AllFactoriesView,
  },
  
];

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router