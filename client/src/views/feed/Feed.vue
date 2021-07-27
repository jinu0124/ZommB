<template>
  
</template>
<!--순서
Feed Main Header
Feed List > Feed List Item > Feed Header, Article Image, Like and Reply, Content, Replies, AddInfo
-->
<!--
Feed Part 접근 시 처음으로 보여줄 페이지
1. 상단에 있는 기존의 Header에 게시물 작성 버튼 추가
2. 내가 팔로우한 회원들의 게시물 List를 v-for를 통해 뿌려줌(infinity scroll)
3. FeedDetail은 게시물 ListItem이 된다. 
  각 Item에는 게시물작성자의 image,nickname,선택한도서,feedmenu,선택한사진,좋아요버튼과 수, 댓글버튼과 수,
  content,hashtag,replies,작성된시간으로부터 지난 시간 등을 표현한다.
-->
<script>
import Header from '@/components/Header.vue';//Feed 페이지에 접근했을 때만 게시물 작성 버튼이 보이도록 구현 필요
import FeedList from '@/components/feeds/feed/FeedList.vue';
import FeedListItem from '@/components/feeds/feed/FeedListItem.vue';
import FeedMenu from '@/components/feeds/feed/FeedMenu.vue';
import WriteFeed from '@/components/feeds/write/WriteFeed.vue';

export default {
  name: 'Feed',
  components: {
    Header,
    FeedList,
    FeedListItem,
    FeedMenu,
    WriteFeed,
  },
  data() {
    return {};
  },
  methods: {

  },
};
</script>

<style>

</style>