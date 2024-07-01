import { createRouter, createWebHashHistory } from 'vue-router';
import AllFactoriesView from '../views/AllFactoriesView';
import SpecificFactoryView from '../views/SpecificFactoryView.vue';
import AddChocolateToFactoryView from '../views/AddChocolateToFactoryView.vue';
import EditChocolate from '../views/EditChocolateView.vue';
import CustomerRegistration from '../views/CustomerRegistrationView.vue';
import LogIn from '../views/LogInView.vue'
import Cart from '../views/CartView.vue';
import UserProfile from '@/views/UserProfile.vue';
import UserOrders from '@/components/UserOrders.vue';
import EditUserInfo from '@/components/EditUserInfo.vue';
import ManagerOrders from '@/components/OrdersInFacory.vue';
import WriteACommentView from '@/views/WriteACommentView.vue'

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
  },
  {
    path: "/addChocolate/:id",
    name: "addChocolate",
    component: AddChocolateToFactoryView,
  },
  {
    path: "/editChocolate/:id",
    name: "editChocolate",
    component: EditChocolate,
  },
  {
    path: "/logIn",
    name: "logIn",
    component: LogIn,
  },
  {
    path: "/customerRegistration",
    name: "customerRegistration",
    component: CustomerRegistration,
  },
  {
    path: "/cart",
    name: "cart",
    component: Cart,
  },
  {
    path: '/profile',
    name: 'UserProfile',
    component: UserProfile,
    children: [
      {
        path: 'info',
        name: 'EditUserInfo',
        component: EditUserInfo
      },
      {
        path: 'orders',
        name: 'UserOrders',
        component: UserOrders
      },
      {
        path: 'managerOrders',
        name: 'ManagerOrders',
        component: ManagerOrders
      }
    ]
  },
  {
    path: "/comment/:id",
    name: "writeAComment",
    component: WriteACommentView,
  }
  
];

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router