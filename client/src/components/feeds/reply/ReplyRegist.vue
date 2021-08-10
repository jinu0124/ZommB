<template>
  <div class="reply-regist">
    <img
      v-if="myInfo.userFileUrl"
      class="user-profile"
      type="button"
      id="UserProfile"
      :src="myInfo.userFileUrl"
      alt="user-profile"
    />
    <img
      v-else
      alt="디폴트 회원 이미지"
      class="default-user-image user-profile"
      src="@/assets/image/common/profileDefault.svg"
      type="button"
      id="UserProfile"
    />
    <input class="reply-input" type="text" placeholder="댓글을 작성하세요." />
    <button class="btn-5 reply-regist-btn" type="button">등록</button>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "ReplyRegist",
  computed: {
    ...mapState("user", ["myInfo"]),
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.user-profile {
  align-self: center;
}
.default-user-image,
.user-profile {
  width: 2rem;
  height: 2rem;
  border-radius: 100%;
}
.reply-regist {
  margin: 30px auto 0px;
  display: flex;
  align-content: center;
  width: 320px;
}
.my-image {
  width: 40px;
  height: 40px;
  background-color: rgba(196, 196, 196, 1);
  margin-right: 6px;
  border-radius: 50%;
}
.reply-regist-btn {
  color: rgba(117, 64, 238, 1);
  width: 60px;
  font-family: noto-sans-kr-12-bord;
  margin: auto 0;
}
.reply-input {
  margin: 0px 10px;
  width: 100%;
  height: 35px;
  background-color: #f1f1f1;
  color: #212121;
  border: none;
  outline: none;
  height: 35px;
  padding-left: 20px;
  font-size: 0.875rem;
  border-radius: 20px;
  transition: none;
}
</style>
