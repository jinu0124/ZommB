<template>
  <div class="like-header">
    <div class="header-detail">
      <img
        alt=""
        class="back"
        type="button"
        id="GoBack"
        src="https://static.overlay-tech.com/assets/f9e1c55d-4837-4f21-9c2b-755be2ed722b.svg"
      />
      <p class="like">좋아요</p>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
.like-header {
  background-color: rgba(123, 96, 241, 1);
  padding: 16px 0 0;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.like {
  height: 21px;
  width: 64px;
  font-family: "Noto Sans KR";
  font-size: 18px;
  font-weight: 700;
  line-height: normal;
  color: rgba(255, 255, 255, 1);
  display: flex;
  text-align: center;
}
</style>