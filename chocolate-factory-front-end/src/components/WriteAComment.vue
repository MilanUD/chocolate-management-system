<template>
  <div>
    <h2>Write a comment</h2>
    <form @submit.prevent="postAComment">
      <div class="form-group">
        <label for="commentText">Write a comment</label>
        <div class="row">
          <div class="col-3"></div>
          <div class="col-6 d-flex justify-content-center">
            <textarea
              type="text"
              rows="4"
              placeholder="Enter a comment"
              id="commentText"
              v-model="comment.commentText"
              class="form-control"
              style="height: 100px"
            >
            </textarea>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label for="commentText">Rate a factory: </label>
        <div class="row">
          <div class="col-3"></div>
          <div class="col-6 d-flex justify-content-center">
            <select class="form-control" v-model="comment.rating">
              <option v-for="score in scores" :key="score">{{ score }}</option>
            </select>
          </div>
        </div>
      </div>
      <button type="submit" class="btn btn-primary">Post a comment</button>
      <p v-if="!areAllFieldsFilled" class="text-danger">
        All fields are required!
      </p>
    </form>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref, computed } from "vue";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

const store = useStore();
const user = computed(() => store.getters.user);
const route = useRoute();
const router = useRouter();
const scores = ref([1, 2, 3, 4, 5]);
const areAllFieldsFilled = computed(
  () => comment.value.commentText && comment.value.rating
);

const comment = ref({
  userId: user.value.id,
  factoryId: route.params.id,
  commentText: "",
  rating: 0,
  status: "Pending",
});

function postAComment() {
  if (comment.value.commentText && comment.value.rating) {
    const factoryId = route.params.id;
    axios
      .post(
        `http://localhost:8080/WebShopAppREST/rest/comments/`,
        comment.value
      )
      .then(() => {
        router.push({ name: "FactoryDetails", params: { id: factoryId } });
      });
  }
  areAllFieldsFilled.value = false;
}
</script>
