<template>
    <div>
      <h2>My Orders</h2>
      <div class="row">
        <form @submit.prevent="searchOrders">
    <fieldset class="border p-4">
      <legend class="w-auto px-2">Search</legend>
      <div class="row align-items-center">
        <div class="col">
          <div class="form-group">
            <label for="fabricName">Fabric name:</label>
            <input type="text" class="form-control" id="fabricName" name="fabricName" v-model="ordersForSearch.factoryName" placeholder="Enter factory name">
          </div>
        </div>
        <div class="col">
          <div class="form-group">
            <label for="priceFrom">Price from:</label>
            <input step="0.01" type="number" class="form-control" id="priceFrom" name="priceFrom" v-model="ordersForSearch.priceFrom" placeholder="Enter price from">
          </div>
        </div>
        <div class="col">
          <div class="form-group">
            <label for="priceTo">Price to:</label>
            <input type="number" step="0.01" class="form-control" id="priceTo" name="priceTo" v-model="ordersForSearch.priceTo" placeholder="Enter price to">
          </div>
        </div>
        <div class="col">
          <div class="form-group">
            <label for="dateFrom">Date from:</label>
            <input type="date" class="form-control" id="dateFrom" name="dateFrom" v-model="ordersForSearch.dateFrom" placeholder="Enter date from">
          </div>
        </div>
        <div class="col">
          <div class="form-group">
            <label for="rating">Date to:</label>
            <input type="date" class="form-control" id="rating" name="rating" v-model="ordersForSearch.dateTo" placeholder="Enter date to">
          </div>
        </div>
        <div class="col-auto mt-4">
          <button type="submit" class="btn btn-primary w-100">
            <i class="bi bi-search"></i>
          </button>
        </div>
      </div>
    </fieldset>
  </form>
      </div>
      <div class="row">
        <div class="col-9"></div>
        <div class="col-3 d-flex flex-column">
          <div class="form-group">
            <label for="sort">Sort by: </label>
            <select class="form-control" id="sort" name="sort" @change="sortOrders($event)">
              <option value="Default">Default</option>
              <option value="Sort by factory name asc">Factory name asc</option>
              <option value="Sort by factory name desc">Factory name desc</option>
              <option value="Sort by price asc">Price asc</option>
              <option value="Sort by price desc">Price desc</option>
              <option value="Sort by date asc">Date asc</option>
              <option value="Sort by date desc">Date desc</option>
            </select>
          </div>
        </div>
      </div>
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
    const originalUserPurchases = ref([]);
    const store = useStore();
    const user = computed(() => store.getters.user);
    const isShowAllChocosOpen = ref({});
    onMounted(() => {
      getUserOrders();
    });
    const ordersForSearch = ref({
      factoryName: '',
      priceFrom: null,
      priceTo: null,
      dateFrom: null,
      dateTo: null
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
        originalUserPurchases.value = response.data;
        userPurchases.value.sort((a, b) =>{
          if(a.status === 'InProgress' && b.status !== 'InProgress') return -1;
          if(a.status !== 'InProgress' && b.status === 'InProgress') return 1;
          return 0;
        });
        isShowAllChocosOpen.value = initializeMap();
        //userPurchases.value.sort((a, b) => )
      })
    }

    const formatDateTime = (date) => {
      return format(date, 'EEEE, dd MMMM yyyy, HH:mm:ss', { locale: sr });
    };

    function cancelOrder(order){
      order.status = "Cancelled";
      let trackedCancellation = null;
      axios.patch(`http://localhost:8080/WebShopAppREST/rest/purchases/${order.id}`, order);
      axios.patch('http://localhost:8080/WebShopAppREST/rest/chocolates/', order);
      axios.patch('http://localhost:8080/WebShopAppREST/rest/customerTypes/', order)
      axios.post('http://localhost:8080/WebShopAppREST/rest/purchaseCancellationHistory/', user.value).then(response => {
        trackedCancellation = response.data;
        if(trackedCancellation.cancellationCounter === 5){
          axios.patch(`http://localhost:8080/WebShopAppREST/rest/users/${user.value.id}`);
        }
      })
    }

    function searchOrders(){
      userPurchases.value = originalUserPurchases.value.filter(purchase => {
        const matchingFactoryName = ordersForSearch.value.factoryName ? purchase.factory.name.toLowerCase().includes(ordersForSearch.value.factoryName.toLowerCase()) : true;
        const matchingPriceFrom = (ordersForSearch.value.priceFrom !== null && ordersForSearch.value.priceFrom !== undefined && ordersForSearch.value.priceFrom !== '')  ? ordersForSearch.value.priceFrom <= purchase.price : true;
        const matchingPriceTo = (ordersForSearch.value.priceTo !== null && ordersForSearch.value.priceTo !== undefined && ordersForSearch.value.priceTo !== '') ? ordersForSearch.value.priceTo >= purchase.price : true;
        const matchingDateFrom =  ordersForSearch.value.dateFrom ? new Date(purchase.date).toISOString().split('T')[0] >= new Date(ordersForSearch.value.dateFrom).toISOString().split('T')[0] : true;
        const matchingDateTo =  ordersForSearch.value.dateTo ? new Date(purchase.date).toISOString().split('T')[0] <= new Date(ordersForSearch.value.dateTo).toISOString().split('T')[0] : true;
        return matchingFactoryName && matchingPriceFrom && matchingPriceTo && matchingDateFrom && matchingDateTo;
      })
    }

    function sortOrders(event){
      const selectedOption = event.target.value;
      switch(selectedOption){
      case "Sort by factory name asc":
        userPurchases.value.sort((a, b) =>{
          if(a.factory.name.toLowerCase() < b.factory.name.toLowerCase()) return -1;
          if(a.factory.name.toLowerCase() > b.factory.name.toLowerCase()) return 1;
          return 0;
        });
        break;
        case "Sort by factory name desc":
        userPurchases.value.sort((a, b) =>{
          if(a.factory.name.toLowerCase() > b.factory.name.toLowerCase()) return -1;
          if(a.factory.name.toLowerCase() < b.factory.name.toLowerCase()) return 1;
          return 0;
        });
        break;
        case "Sort by price asc":
          userPurchases.value.sort((a, b) => a.price - b.price);
          break;
        case "Sort by price desc":
          userPurchases.value.sort((a, b) => b.price - a.price);
          break;
        case "Sort by date asc":
          userPurchases.value.sort((a, b) => new Date(a.date) - new Date(b.date));
          break;
        case "Sort by date desc":
          userPurchases.value.sort((a, b) => new Date(b.date) - new Date(a.date));
          break;
        default:
        userPurchases.value.sort((a, b) =>{
          if(a.status === 'InProgress' && b.status !== 'InProgress') return -1;
          if(a.status !== 'InProgress' && b.status === 'InProgress') return 1;
          return 0;
        });
      }

    }


  </script>