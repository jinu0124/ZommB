<template>
  <div class="header fixed-top">
    <div class="d-flex align-items-center">
      <i 
        class="back-btn fi-rr-angle-small-left"
        type="button"
        @click="$router.go(-1)"
      ></i>
      <span class="header-logo">{{ this.title }}</span>
    </div>
  </div>
</template>

<script>

export default {
  name: 'SimpleHeader',
  props: {
    title: String
  }
}
</script>

<style scoped>
  .header {
    padding: 12px 20px;
    height: 60px;
  }
  .back-btn {
    font-size: 1.5rem;
  }
  .header-logo {
    font-weight: 700;
    font-size: 18px;
    margin: 0 auto;
  }
  
</style>