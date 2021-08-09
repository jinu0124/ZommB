<template>
  <div class="content-part">
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
    <div class="content">
      <p class="content-writer">{{ nickname }}</p>
      <p class="article">{{ content }}</p>
      <div>
        <span v-for="(tag, idx) in tags" :key="idx" class="content-tag rounded-pill me-1"
          >#{{ tag }}</span
        >
      </div>
      <p class="content-duration">{{ duration }}시간 전</p>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Content",
  data() {
    return {
      nickname: "Nickname",
      content: "밤 12시, 죽기 바로 전에만 열리는 마법의 도서관에서 인생의 두 번째 기회를 드립니다.",
      tags: ["해시태그", "테스트", "입니다."],
      duration: "3",
    };
  },
  computed: {
    ...mapState("user", ["myInfo"]),
  },
};
</script>

<style scoped>
.content-part {
  margin: 0 auto;
  display: flex;
  align-items: flex-start;
  width: 320px;
}
.user-profile,
.default-user-image {
  width: 40px;
  height: 40px;
  margin-top: 11px;
  margin-right: 13px;
  border-radius: 203px;
}
.content {
  width: 100%;
  margin-right: 10px;
  display: flex;
  flex-direction: column;
  text-align: left;
  align-items: flex-start;
}
.content-writer {
  width: 82px;
  color: rgba(33, 33, 33, 1);
  margin-bottom: 2px;
  font-family: noto-sans-kr-10-bold;
}
.article {
  max-width: 300px;
  color: rgba(33, 33, 33, 1);
  margin-bottom: 2px;
  font-family: noto-sans-kr-10-regular;
}

.hashtag {
  height: 12px;
  width: 35px;
  color: rgba(88, 88, 88, 1);
  display: flex;
  font-family: noto-sans-kr-8-bold;
}
.content-menu {
  margin-top: 27px;
  padding: 0 2.45px;
  align-items: flex-start;
  border-left: 4px solid rgba(155, 154, 154, 1);
  border-right: 4px solid rgba(155, 154, 154, 1);
}
.content-duration {
  width: 20%;
  height: 2.86%;
  font-family: "Noto Sans KR";
  font-size: 9px;
  font-weight: 400;
  line-height: normal;
  color: rgba(164, 164, 164, 1);
}
.content-tag {
  color: #585858;
  background: #ffdc7c;
}
</style>
