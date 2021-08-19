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
import { mapState } from 'vuex'

export default {
  name: 'FeedView',
  components: {
    FeedListItem,
    SimpleHeader
  },
  data () {
    return {
      flags: ['pf', 'sf', 'cw', 'cd'],
      titles: ['프로필', '검색', '위클리', '데일리'],
      type: null,
    }
  },
  methods: {
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
    setFeed (type) {
      if (!type) {
        this.$store.commit('feed/SET_FEED_INFO', this.profileInfo.feed)
      } else if (type === 1) {
        this.$store.commit('feed/SET_FEED_INFO', this.feedResult)
      } else if (type === 2) {
        this.$store.commit('feed/SET_FEED_INFO', this.weeklyFeed)
      } else {
        this.$store.commit('feed/SET_FEED_INFO', this.dailyFeed)
      }
    }
  },
  computed: {
    ...mapState('feed', ['feedInfo']),
    ...mapState('user', ['profileInfo']),
    ...mapState('search', ['feedResult']),
    ...mapState('challenge', ['weeklyFeed', 'dailyFeed']),
  },
  created() {
    const flag = this.$route.params.flag
    this.type = this.flags.indexOf(flag)
    if (this.type < 0) {
      this.$router.push({ name: 'PageNotFound' })
    }
    this.setFeed(this.type)
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
