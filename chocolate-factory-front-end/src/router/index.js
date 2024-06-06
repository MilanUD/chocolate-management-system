import { createRouter, createWebHashHistory } from 'vue-router';
import AllFactoriesView from '../views/AllFactoriesView';
import SpecificFactoryView from '../views/SpecificFactoryView.vue';

const routes = [
  {
    path: "/",
    name: "allFactoriesView",
    component: AllFactoriesView,
  },
  {
    path: "/FactoryDetails/:id",
    name: "FactoryDetails",
    component: SpecificFactoryView,
    props: route => ({ factory: route.params.factory })
  },
  
];

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router