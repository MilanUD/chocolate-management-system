import { createStore } from 'vuex';
import axios from 'axios';

const getUserFromLocalStorage = () => {
  const user = localStorage.getItem('user');
  try {
    return user ? JSON.parse(user) : null;
  } catch (e) {
    console.error("Error parsing user from localStorage", e);
    return null;
  }
};

const store = createStore({
  state: {
    isLoggedIn: !!localStorage.getItem('token'),
    user: getUserFromLocalStorage(),
    token: localStorage.getItem('token') || ''
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
      state.user = null;
      state.token = '';
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      console.log('Mutation: User logged out');
    },
    setUser(state, user) {
      state.user = user;
      console.log('Mutation: User set:', state.user);
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
    token: state => state.token
  }
});

export default store;