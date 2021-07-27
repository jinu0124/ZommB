<template>
  <div class="like-list">
    <div class="like-list-item">
      <img
        class="user-profile"
        type="button"
        id="UserProfileImage"
        aria-expanded="false"
        src="@/assets/image/test/profileTest.jpg"
        alt=""
      >
      <span class="nickname">Nickname</span>
      <div class="btn">
        <button
          class="btn-1 btn-yellow"
          @click="Follow"
        >팔로우</button>
        <button
          class="btn-1 btn-grey mt-1"
          @click="unFollow"
        >팔로잉</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';

export default {
  name: 'LikeListItem',
  data() {
    return {};
  },
  props: {
    like: Object,
  },
  methods: {
    ...mapActions(['selectUser']),
    
  }
}
</script>

<style>
.btn-1 {
  border: none;
  width: 16rem;
  height: 2.5rem;
  border-radius: 1.25rem;
  outline: none;
  font-size: 1rem;
  font-weight: 700;
}
.btn-yellow {
  background-color: #FFDC7C;
  color: #585858;
}
.btn-grey {
  background-color: #A4A4A4;
  color: #fff;
}
</style>