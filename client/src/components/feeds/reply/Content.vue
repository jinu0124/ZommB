<template>
  <div class="content-part">
    <img
      alt=""
      class="content-writer-image"
      src="https://static.overlay-tech.com/assets/f7d182ec-a020-4c3a-b892-187659722f02.png"
    />
    <div class="content">
      <p class="content-writer">Nickname</p>
      <p class="article">
        밤 12시, 죽기 바로 전에만 열리는 마법의 도서관에서 인생의 두번째 기회를 드립니다.
      </p>
      <div class="hasgtags">
        <button class="hashtag">#해시태그</button>
        <button class="hashtag">#해시태그</button>
        <button class="hashtag">#해시태그</button>
      </div>
      <div class="content-menu">
        <div class="ellipse-12"></div>
      </div>
      <p class="content-duration">00시간 전</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Content',
}
</script>

<style>
.content-part {
  margin-bottom: 2px;
  display: flex;
  align-items: flex-start;
}
.content-writer-image {
  width: 40px;
  height: 40px;
  margin-top: 11px;
  margin-right: 13px;
  border-radius: 203px;
}
.content {
  margin-right: 10px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.content-writer {
  width: 82px;
  color: rgba(33, 33, 33, 1);
  margin-bottom: 2px;
  font-family: noto-sans-kr-10-bold;
}
.article {
  max-width: 194px;
  color: rgba(33, 33, 33, 1);
  margin-bottom: 2px;
  font-family: noto-sans-kr-10-regular;
}
.hashtags {
  display: flex;
  align-items: flex-start;
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
  display: flex;
  align-items: flex-start;
  border-left: 4px solid rgba(155, 154, 154, 1);
  border-right: 4px solid rgba(155, 154, 154, 1);
}
.ellipse-12 {
  width: 4.1px;
  height: 4px;
  background-color: rgba(155, 154, 154, 1);
  border-radius: 50%;
}
.content-duration {
  width: 13.6%;
  height: 2.86%;
  font-family: "Noto Sans KR";
  font-size: 9px;
  font-weight: 400;
  line-height: normal;
  color: rgba(164, 164, 164, 1);
  margin-bottom: 25px;
  margin-left: 53px;
}
</style>