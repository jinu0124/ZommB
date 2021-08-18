<template>
  <div class="back fixed-bottom d-flex justify-content-center">
    <div class="reply-regist d-flex">
      <div>
        <img
          v-if="myInfo.userFileUrl"
          class="user-profile"
          :src="myInfo.userFileUrl"
          alt="user-profile"
        />
        <img
          v-else
          alt="디폴트 회원 이미지"
          class="default-user-image user-profile"
          src="@/assets/image/common/profileDefault.svg"
        />
      </div>
      <div class="input-box d-flex justify-content-between align-items-center">
        <textarea
          class="form-control reply-input"
          type="text"
          placeholder="댓글을 작성하세요."
          @input="insertContent"
          :value="content"
        />
        <span
          class="btn-submit me-3"
          @click="onWrite"
        >등록</span>

      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

export default {
  name: "ReplyRegist",
  data() {
    return {
      content: "",
    };
  },
  computed: {
    ...mapState("user", ["myInfo"]),
    replyData() {
      return {
        id: this.$route.params.id,
        cont: this.content,
      };
    },
  },
  methods: {
    ...mapActions("feed", ["writeComment"]),
    insertContent(event) {
      this.content = event.target.value;
    },
    onWrite() {
      this.writeComment(this.replyData)
      this.content = ''
    }
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.back {
  width: 100vw;
  height: 100px;
  padding-top: 50px;
  background: #fff;
}
.user-profile {
  align-self: center;
}
reply-regist {
  width: 280px;
}
.default-user-image,
.user-profile {
  width: 2rem;
  height: 2rem;
  border-radius: 100%;
}
.my-image {
  width: 40px;
  height: 40px;
  background-color: rgba(196, 196, 196, 1);
  margin-right: 6px;
  border-radius: 50%;
}
.input-box {
  width: 240px;
  background: #f1f1f1;
  height: 40px;
  border-radius: 20px;
}
.reply-input {
  background: none;
  box-shadow: none;
  border-radius: 0;
  border: none;
  font-size: 14px;
  letter-spacing: 1px;
  word-spacing: 1px;
  line-height: 15px;
  outline: none;
  padding: 10px 20px;
  height: 40px;
  width: 180px;
  word-wrap: break-word;
}
.btn-submit {
  color: #683ec9;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
}
</style>
