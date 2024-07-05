<template>
    <div>
        <div class="card" v-for="chocolate in chocolates" :key="chocolate.id">
            <div class="card-body">
                <div class="row mb-10">
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
                            <div class="input-group">
                                <button class="btn btn-outline-secondary" type="button" @click="decreaseQuantity(chocolate)">-</button>
                                <input @input="validateQuantity(chocolate)" :max="chocolate.stockQuantity" type="number" class="form-control" id="quantity-input" v-model="chocolateCounts[chocolate.id]">
                                <button class="btn btn-outline-secondary" type="button" @click="increaseQuantity(chocolate)">+</button>
                            </div>
                        </div>
                        
                    </div>
                    <div class="row mt-10">
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
                            <button @click="removeFromCart(chocolate)" class="btn btn-danger mt-10">Remove from cart</button>
                        </div>
                    </div>
            </div>
        </div>
        <p>Total price: {{ cart.price }}</p>
        <button :disabled="isCartEmpty" @click="makePurchase" class="btn btn-primary">Make a purchase</button>
    </div>
</template>

<script setup>
    import axios from 'axios';
    import { computed, ref } from 'vue';
    import { useStore } from 'vuex';
    import { useRouter } from "vue-router";

    const router = useRouter()
    const store = useStore();
    const cart = computed(() => store.getters.cart);
    const chocolates = computed(() => {
  const uniqueChocolatesMap = new Map();
  cart.value.chocolatesInCart.forEach(chocolate => {
    if (uniqueChocolatesMap.has(chocolate.id)) {
      uniqueChocolatesMap.get(chocolate.id).quantity += chocolate.quantity;
    } else {
      uniqueChocolatesMap.set(chocolate.id, { ...chocolate });
    }
  });
  return Array.from(uniqueChocolatesMap.values());
});    
    const chocolateCounts = ref(countChocolates(cart.value.chocolatesInCart));
    const isCartEmpty = computed(() => chocolates.value.length === 0)

    function countChocolates(chocolates) {
        const countMap = {};
        chocolates.forEach(chocolate => {
            if (countMap[chocolate.id]) {
                countMap[chocolate.id]++;
            } else {
                countMap[chocolate.id] = 1;
            }
        });
        return countMap;
    }

    function decreaseQuantity(chocolate){
        //const chocolateCounts = ref(countChocolates(cart.value.chocolatesInCart));
        console.log('cHOCOLATE COUNTS', chocolateCounts.value[chocolate.id]);
        if(chocolateCounts.value[chocolate.id] > 1){
            console.log('Increasing quantity of:', chocolate);
            store.commit('decreaseQuantity', chocolate);
            chocolateCounts.value = countChocolates(cart.value.chocolatesInCart)
        }
    }

    function increaseQuantity(chocolate){
        //const chocolateCounts = ref(countChocolates(cart.value.chocolatesInCart));
        if(chocolateCounts.value[chocolate.id] < chocolate.stockQuantity){
            store.commit('addToCart', chocolate);
            chocolateCounts.value = countChocolates(cart.value.chocolatesInCart)
        }
    }

    function validateQuantity(chocolate){
        if(chocolateCounts.value[chocolate.id] > chocolate.stockQuantity){
            chocolateCounts.value[chocolate.id] = chocolate.stockQuantity;
        }else if(chocolateCounts.value[chocolate.id] <= 0){
            chocolateCounts.value[chocolate.id] = 1;
        }
        store.commit('removeSpecificChocolateFromCart', chocolate);
        for(let i = 0; i < chocolateCounts.value[chocolate.id]; i++){
            store.commit('addToCart', chocolate);
        }
    }

    function removeFromCart(chocolate){
        const count = chocolateCounts.value[chocolate.id];
        console.log('cHOCOLATE COUNTS', chocolateCounts.value[chocolate.id]);
        store.commit('removeFromCart', {chocolate, quantity: count});
        chocolateCounts.value = countChocolates(cart.value.chocolatesInCart);
    }

    function makePurchase(){
        axios.post('http://localhost:8080/WebShopAppREST/rest/purchases/', cart.value).then(() =>{
            store.commit('clearCart');
        })
        axios.post('http://localhost:8080/WebShopAppREST/rest/chocolates/updateQuantity', cart.value).then(() =>{
            router.push({name: 'allFactoriesView'});
        })
    }

</script>