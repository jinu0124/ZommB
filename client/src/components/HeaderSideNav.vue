<template>
  <div class="offcanvas offcanvas-start" data-bs-scroll="true" tabindex="-1" id="offcanvasWithBothOptions" aria-labelledby="offcanvasWithBothOptionsLabel">
    <div class="offcanvas-header">
      <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <div class="offcanvas-body ms-4 d-flex flex-column gap-3">
      <div><router-link :to="{ name: 'Feed' }" class="menu-item" data-bs-dismiss="offcanvas">NewsFeed</router-link></div>
      <div><router-link :to="{ name: 'Challenge' }" class="menu-item" data-bs-dismiss="offcanvas">Challenge</router-link></div>
      <a class="menu-item">Search</a>
      <div><router-link :to="{ name: 'Profile' }" class="menu-item" data-bs-dismiss="offcanvas">MyProfile</router-link></div>
    </div>
    <img class="offcanvas-deco" src="@/assets/image/camel/camelRotate.svg" alt="">
    <div class="footer mx-4">
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Porttitor malesuada libero diam elit nibh.</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HeaderSideNav'
}
</script>

<style scoped>
  .offcanvas {
    background: rgba(117, 64, 238, 0.9);
    border: none;
  }
  .menu-item {
    font-size: 2rem;
    font-weight: 900;
    color: #fff;
    text-decoration: none;
  }
  .footer {
    color: #fff;
    font-size: 0.7rem;
    font-weight: 400;
  }
  .offcanvas-deco {
    position: absolute;
    width: 10rem;
    right: 0;
    bottom: 7rem;
  }
</style>