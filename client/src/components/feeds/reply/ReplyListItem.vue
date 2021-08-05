<template>
  <div class="reply-list-item">
    <div class="replier-image"></div>
    <span>user1</span>
    <span>
      <div>댓글 어쩌구 저쩌구...</div>
      <div class="reply-like-num">좋아요 3개</div>
    </span>
    <span>
      <img
        alt=""
        class="reply-like"
        src="https://static.overlay-tech.com/assets/701fe450-b80b-4620-966e-0e08fbe9daa2.svg"
      />
    </span>
    <ReplyMenu/>
  </div>
</template>

<script>
import ReplyMenu from '@/components/feeds/reply/ReplyMenu.vue';

export default {
  name: 'ReplyListItem',
  components: {
    ReplyMenu,
  }
}
</script>

<style scoped>
.reply-list-item {
  display: flex;
  align-items: center;
}
.replier-image {
  width: 40px;
  height: 40px;
  background-color: rgba(196, 196, 196, 1);
  margin-right: 13px;
  border-radius: 50%;
}
.reply-content {
  margin-right: 12px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.reply-like {
  width: 4.78%;
  height: 20.69%;
}
.reply-plus {
  margin-bottom: 2px;
  padding: 0 0 0 53px;
  display: flex;
  align-items: center;
}
.reply-duration {
  font-family: "Noto Sans KR";
  font-size: 9px;
  font-weight: 400;
  line-height: normal;
  color: rgba(164, 164, 164, 1);
  margin-right: 14px;
}
.reply-like-num {
  font-family: "Noto Sans KR";
  font-size: 9px;
  font-weight: 400;
  line-height: normal;
  color: rgba(164, 164, 164, 1);
  margin-right: 10px;
}
.ellipse-12-two {
  width: 3px;
  height: 3px;
  background-color: rgba(196, 196, 196, 1);
  border-radius: 50%;
}
</style>