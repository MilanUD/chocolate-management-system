<template>
    <div>
      <h2>My Orders</h2>
       <div class="card" v-for="userPurchase in userPurchases" :key="userPurchase.id">
        <div class="card-body">
          <div class="row">
            <div class="row">
              <div class="col-3">
                <p>{{ userPurchase.id }}</p>
              </div>
              <div class="col-3 d-flex flex-column">
                <i class="bi bi-person"></i>
                <p>{{ userPurchase.customer }}</p>
              </div>
              <div class="col-3 d-flex flex-column">
                <i class="bi bi-calendar"></i>
                <p>{{ formatDateTime(userPurchase.date) }}</p>
              </div>
              <div class="col-3 d-flex flex-column">
                <button :disabled="userPurchase.status === 'InProgress' ? false : true" @click.prevent="cancelOrder(userPurchase)" class="btn btn-danger mt-10">
                  <i class="bi bi-slash-circle"></i>
                  Cancel order
                </button>
              </div>
            </div>
            <div class="row">
              <div class="col-3 d-flex flex-column">
                <i class="bi bi-building"></i>
                <p><strong>{{ userPurchase.factory.name }}</strong></p>
              </div>
              <div class="col-3 d-flex flex-column">
                <p><strong>Status: </strong></p>
                <div v-if="userPurchase.status === 'InProgress'">
                  <i class="bi bi-hourglass-split"></i>
                  <p>In progress</p>
                </div>
                <div v-if="userPurchase.status === 'Declined'">
                  <i class="bi bi-x-circle"></i>
                  <p>Declined</p>
                </div>
                <div v-if="userPurchase.status === 'Cancelled'">
                  <i class="bi bi-slash-circle"></i>
                  <p>Cancelled</p>
                </div>
                <div v-if="userPurchase.status === 'Accepted'">
                  <i class="bi bi-check-circle"></i>
                  <p>Accepted</p>
                </div>
              </div>
              <div class="col-3 d-flex flex-column">
                <i class="bi bi-currency-dollar"></i>
                <p>{{ userPurchase.price }}</p>
              </div>
              <div class="col-3">
                <button @click.prevent="showAllChocos(userPurchase.id)" class="btn btn-primary buttonStyle">Show all chocolates in order</button>
              </div>
            </div>
            <div class="row" v-if="isShowAllChocosOpen && isShowAllChocosOpen[userPurchase.id]">
              <div class="card" v-for="chocolate in userPurchase.chocolates" :key="chocolate.id">
                <div class="card-body">
                  <div class="row">
                        <div class="col-md-2">
                            <h4><strong>{{ chocolate.name }}</strong></h4>
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                            <i class="bi bi-currency-euro"></i>
                            <p>{{ chocolate.price }}</p>                         
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                            <font-awesome-icon :icon="['fas', 'weight-hanging']" style="font-size: 2rem; margin-right: 5px;" />
                            <p> {{ chocolate.grams }} grams</p>
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                            <p class="pClass">Flavor:</p>
                            <p>{{ chocolate.flavor }}</p>
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                            <p class="pClass">Quantity:</p>
                            <p>{{ calculateQunatity(chocolate, userPurchase.chocolateIds) }} </p>
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                           
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">
                            <img :src="chocolate.picture" height="100" width="150">
                        </div>
                        <div class="col-md-4 d-flex flex-column">
                           <p>{{ chocolate.description }}</p>
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                            <p class="pClass">Type:</p>
                            <p>{{ chocolate.type }}</p>
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                            <p class="pClass">Stock:</p>
                            <p> {{ chocolate.isInStock ? 'In stock' : 'Out of stock' }}</p>
                        </div>
                        
                    </div>
                </div>
              </div>
            </div>
          </div>
        </div>
       </div>
    </div>
</template>
  
  <script setup>
    import axios from 'axios';
    import { onMounted, ref, computed } from 'vue';
    import { useStore } from 'vuex';
    import { format } from 'date-fns';
    import { sr } from 'date-fns/locale';


    const userPurchases = ref([]);
    const store = useStore();
    const user = computed(() => store.getters.user);
    const isShowAllChocosOpen = ref({});
    onMounted(() => {
      getUserOrders();
    })

    function initializeMap(){
      const map = {}
      userPurchases.value.forEach(u => {
        map[u.id] = false;
      })
      return map;
    }

    function showAllChocos(id) {
      console.log('vrednost je: ', isShowAllChocosOpen.value[id])
      for (const key in isShowAllChocosOpen.value) {
        isShowAllChocosOpen.value[key] = (key === id);
      }
    }

    function calculateQunatity(chocolate, chocolates){
      return chocolates.filter(c => c === chocolate.id).length;
    }



    function getUserOrders(){
      axios.get(`http://localhost:8080/WebShopAppREST/rest/purchases/${user.value.id}`).then(response =>{
        userPurchases.value = response.data;
        isShowAllChocosOpen.value = initializeMap();
        //userPurchases.value.sort((a, b) => )
      })
    }

    const formatDateTime = (date) => {
      return format(date, 'EEEE, dd MMMM yyyy, HH:mm:ss', { locale: sr });
    };

    function cancelOrder(order){
      order.status = "Cancelled";
      axios.patch(`http://localhost:8080/WebShopAppREST/rest/purchases/${order.id}`, order);
      axios.patch('http://localhost:8080/WebShopAppREST/rest/chocolates/', order);
      axios.patch('http://localhost:8080/WebShopAppREST/rest/customerTypes/', order).then(() =>{
        getUserOrders();
      })
    }


  </script>