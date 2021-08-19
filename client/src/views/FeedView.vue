<template>
  <div>
    <SimpleHeader
      class="feed-header"
      :title=titles[type]
    />
    <div id="feed-detail" class="feed">
      <div class="feed-list">
        <button class="top-btn" @click="goToTop">
          <i class="fi-sr-caret-up"></i>
        </button>
        <FeedListItem
          v-for="feed in feedInfo"
          class="feed-item up-on-scroll"
          :id="'feed' + feed.id"
          :key="feed.id"
          :feed="feed"
        />
      </div>
    </div>
  </div>
</template>

<script>
import FeedListItem from '@/components/feeds/feed/FeedListItem'
import SimpleHeader from '@/components/SimpleHeader'
import { mapState, mapActions } from 'vuex'

export default {
  name: 'FeedView',
  components: {
    FeedListItem,
    SimpleHeader
  },
  data () {
    return {
      title: 'temp',
      page: 1,
      flags: ['pf', 'sf', 'cw', 'cd'],
      titles: ['프로필', '검색', '위클리', '데일리'],
      type: null,
    }
  },
  methods: {
    ...mapActions("feed", ["getFeedInfo"]),
    needTopBtn() {
      const currentTop = document.getElementById("feed-detail").scrollTop;
      const btn = document.querySelector(".top-btn");
      if (btn) {
        if (currentTop != 0) {
          btn.style.opacity = "1";
        } else {
          btn.style.opacity = "0";
        }
      }
    },
    goToTop() {
      document.getElementById("feed-detail").scrollTop = 0;
    },
  },
  computed: {
    ...mapState('user', ['profileInfo']),
    ...mapState('search', ['feedResult']),
    ...mapState('challenge', ['weeklyFeed', 'dailyFeed']),
    feedInfo () {
      if (!this.type) {
        return this.profileInfo.feed
      } else if (this.type === 1) {
        return this.feedResult
      } else if (this.type === 2) {
        return this.weeklyFeed
      } return this.dailyFeed
    },
  },
  created() {
    const flag = this.$route.params.flag
    this.type = this.flags.indexOf(flag)
    if (this.type < 0) {
      this.$router.push({ name: 'PageNotFound' })
    }
  },
  mounted() {
    const feedPage = document.getElementById("feed-detail");
    feedPage.addEventListener("scroll", this.needTopBtn);
    this.$nextTick(function() {
      const targetId= `feed${this.$route.params.target}`
      var target = document.getElementById(targetId)
      target.scrollIntoView(true)
    })
  },
};
</script>

<style scoped>
.feed-header {
  color: #212121;
}
.feed {
  height: 100vh;
  width: 100vw;
  background: #ffffff;
  border-radius: 30px 0px 0px 0px;
  margin-top: 60px;
  padding: 20px 20px 100px;
  position: fixed;
  overflow: scroll;
  color: #212121;
}
.feed::-webkit-scrollbar {
  display: none;
}
.fd-header .title {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 10px;
}
.write-btn {
  width: 24px;
  height: 24px;
  margin-top: 7px;
}
</style>
