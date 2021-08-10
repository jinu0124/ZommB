<template>
  <div class="search-default">
    <div class="d-flex flex-column recommand align-items-center">
      <span class="daily-rec"
        ><hr class="half-hr"/>
        오늘의 추천 검색어
        <hr class="half-hr"
      /></span>
    </div>
    <div class="hashtags mt-1">
      <span v-for="(tag, idx) in tags" :key="idx" class="badge rounded-pill me-1">#{{ tag }}</span>
    </div>
    <div class="d-flex flex-column recommand align-items-center">
      <span class="user-rec"
        ><hr class="half-hr2"/>
        내가 알 수도 있는 사람
        <hr class="half-hr2"
      /></span>
    </div>
    <div class="user-list-item">
      <span class="user-images">
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
          src="@/assets/image/common/profileDefault.svg"
          alt="defalut-profile"
          class="default-user-image user-profile"
          type="button"
        />
      </span>
      <span class="user-nickname">{{ nickname }}</span>
      <span>
        <button class="follow btn-5 btn-yellow" @click="follow()" v-show="isFollow">팔로우</button>
      </span>
      <span>
        <button class="follow btn-5 btn-grey" type="button" @click="unfollow()" v-show="!isFollow">
          팔로우 취소
        </button>
      </span>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "SearchDefault",
  data() {
    return {
      content:
        "truncate 테스트입니다. 왼쪽은 저희 집 고양이에여. 너무 귀엽죠? 저도 알아여 ㅎㅎㅎ 몇 자에서 끊기는 거니 도대체..",
      tags: ["해시태그", "테스트", "입니다"],
      isFollow: true,
      nickname: "Nickname",
    };
  },
  methods: {
    follow() {
      this.isFollow = false;
      console.log("팔로우");
    },
    unfollow() {
      this.isFollow = true;
      console.log("팔로우 취소");
    },
  },
  computed: {
    ...mapState("user", ["myInfo"]),
  },
};
</script>

<style scoped>
.half-hr {
  width: 70px;
  margin: auto 10px;
  border-bottom: 3px solid #ffffff;
}
.daily-rec {
  display: flex;
  margin-top: 20px;
  font-size: small;
}
.hashtags {
  text-align: center;
}
.half-hr2 {
  width: 60px;
  margin: auto 10px;
  border-bottom: 3px solid #ffffff;
}
.user-rec {
  display: flex;
  margin-top: 20px;
  font-size: small;
}
.badge {
  font-size: 8px;
  color: #585858;
  background: #ffdc7c;
}
.user-list-item {
  text-align: center;
  align-items: center;
  margin-top: 10px;
  font-size: 15px;
}
.user-profile {
  align-self: center;
}
.default-user-image,
.user-profile {
  width: 35px;
  height: 35px;
  border-radius: 50%;
}
.user-nickname {
  margin: 0 20px 0 5px;
}
.follow {
  width: 100px;
  height: 25px;
}
</style>
