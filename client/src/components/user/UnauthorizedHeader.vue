<template>
  <div class="header fixed-top">
    <div class="d-flex align-items-center">
      <i 
        class="back-btn fi-rr-angle-small-left"
        type="button"
        @click="$router.push({ name: 'Index' })"
      ></i>
      <span
        class="header-logo"
        type="button"
        @click="$router.push({ name: 'Index' })"
      >CommB</span>
    </div>
  </div>
</template>

<script>

export default {
  name: 'UnauthorizedHeader',
}
</script>

<style scoped>
  .header {
    background: #7B60F1;
    padding: 12px 20px;
    height: 60px;
  }
  .back-btn {
    font-size: 1.5rem;
    color: #fff;
  }
  .header-logo {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 1.5rem;
    margin: 0 auto;
    color: #fff;
  }
  
</style>