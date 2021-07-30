<template>
  <div class="reply-list-item">
    <div class="replier-image"></div>
    <div class="reply-content">
      <p class="replier-nickname">Replier</p>
      <p class="reply-article">
        댓글입니다. 댓글이에여. 제 정체는 바로바로 댓글입니다.
      </p>
    </div>
    <img
      alt=""
      class="reply-like"
      type="button"
      src="https://static.overlay-tech.com/assets/d93b4223-78e0-484c-bee1-1f2c14c625af.svg"
    />
    <div class="reply-plus">
      <p class="reply-duration">0분 전</p>
      <p class="reply-like-num">좋아요 5개</p>
      <div class="reply-menu-btn">
        <div class="ellipse-12-two"></div>
      </div>
      <ReplyMenu/>
    </div>
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