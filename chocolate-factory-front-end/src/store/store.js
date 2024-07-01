import { createStore } from 'vuex';
import axios from 'axios';

const defaultUser = {
  id: '',
  username: '',
  password: '',
  name: '',
  lastName: '',
  gender: '',
  birthDate: '',
  userType: '',
  factoryId: ''
};

const getUserFromLocalStorage = () => {
  const user = localStorage.getItem('user');
  try {
    return user ? JSON.parse(user) : defaultUser;
  } catch (e) {
    console.error("Error parsing user from localStorage", e);
    return null;
  }
};

const store = createStore({
  state: {
    isLoggedIn: !!localStorage.getItem('token'),
    user: getUserFromLocalStorage(),
    token: localStorage.getItem('token') || '',
    cart: {
      chocolatesInCart: [],
      User: null,
      price: 0
    }
  },
  mutations: {
    login(state, { token, user }) {
      state.isLoggedIn = true;
      state.token = token;
      state.user = user;
      localStorage.setItem('token', token);
      localStorage.setItem('user', JSON.stringify(user));
      console.log('Mutation: User logged in:', state.user);
    },
    logout(state) {
      state.isLoggedIn = false;
      state.user = defaultUser;
      state.token = '';
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      console.log('Mutation: User logged out');
    },
    setUser(state, user) {
      state.user = user;
    },
    addToCart(state, chocolate) {
      state.cart.chocolatesInCart.push(chocolate);
      state.cart.price +=chocolate.price;
      console.log('Increasing quantity of:', chocolate);
    },
    clearCart(state) {
      state.cart.chocolatesInCart = [];
      state.cart.price = 0;
      console.log('Mutation: Cart cleared');
    },
    decreaseQuantity(state, chocolate){ 
        const index = state.cart.chocolatesInCart.findIndex(choc => choc.id == chocolate.id);
        if(index !== -1){
          state.cart.chocolatesInCart.splice(index,1);
          state.cart.price -= chocolate.price;
        }
    },
    removeFromCart(state, {chocolate, quantity}){
      state.cart.chocolatesInCart = state.cart.chocolatesInCart.filter(choco => choco.id != chocolate.id);
      state.cart.price -= quantity*chocolate.price
    },
    updateCartUserId(state){
      state.cart.user = state.user;
    }
  },
  actions: {
    async login({ commit }, credentials) {
      try {
        const response = await axios.post('http://localhost:8080/WebShopAppREST/rest/users/login', credentials);
        const { token, user } = response.data;

        // Postavi token za sve buduće zahteve
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

        // Sačuvaj token i korisničke podatke u Vuex store
        commit('login', { token, user });
        console.log('Action: User logged in:', user);
      } catch (error) {
        console.error('Login failed:', error);
      }
    },
    async logout({ commit }) {
      commit('logout');
      console.log('Action: User logged out');
    }
  },
  getters: {
    isLoggedIn: state => state.isLoggedIn,
    user: state => state.user,
    token: state => state.token,
    cart: state => state.cart
  }
});

export default store;