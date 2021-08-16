<template>
  <div>
    <div
      v-if="moveTarget"
      class="backdrop"
    ></div>
    <ProfileBookRating
      v-if="moveTarget"
      class="alert-center"
      data-bs-backdrop="static"
      tabindex="-1"
      aria-hidden="true"
      :book=moveTarget
      @close="closeRating"
      @ok="completeRating"
    />
    <div id="profile" class="profile d-flex flex-column align-items-center">
      <ProfileHeader/>
      <div class="tabs d-flex gap-4 my-3">
        <span 
          @click=changePage(0)
          :class="[ selectedPage === 0 ? 'current' : 'rest']"
        >게시물</span>
        <span 
          @click=changePage(1)
          :class="[ selectedPage === 1 ? 'current' : 'rest']"
        >서재</span>
        <span 
          @click=changePage(2)
          :class="[ selectedPage === 2 ? 'current' : 'rest']"
        >북카트</span>
      </div>
      <ProfileFeeds
        v-if="selectedPage === 0"
        @last="addFeed"
      />
      <ProfileLibrary
        v-else-if="selectedPage === 1"
      />
      <ProfileBookcart
        v-else-if="selectedPage === 2"
      />
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import ProfileHeader from "@/components/user/profile/ProfileHeader"
import ProfileFeeds from "@/components/user/profile/ProfileFeeds"
import ProfileLibrary from "@/components/user/profile/ProfileLibrary"
import ProfileBookcart from "@/components/user/profile/ProfileBookcart"
import ProfileBookRating from "@/components/user/profile/ProfileBookRating"

export default {
  name: "Profile",
  components: {
    ProfileHeader,
    ProfileFeeds,
    ProfileLibrary,
    ProfileBookcart,
    ProfileBookRating
  },
  data() {
    return {
      selectedPage: 0,
      rateBook: false,
      userId: null,
      feedpage: 1
    }
  },
  methods: {
    ...mapActions('user', ['getUserInfo', 'getUserFeed', 'getBookShelf', 'getBookCart', 'getCollections']),
    changePage (val) {
      this.selectedPage = val
    },
    closeRating () {
      this.$store.commit('user/SET_MOVE_TARGET', null)
    },
    completeRating () {
      this.$store.commit('user/SET_MOVE_TARGET', null)
      this.getUserInfo(this.userId)
      this.getBookShelf(this.userId)
      this.getBookCart(this.userId)
    },
    addFeed () {
      this.getUserFeed({
        id: this.userId, 
        page: this.feedpage
      })
      this.feedpage ++
    },
    getProfileInfo () {
    this.getUserInfo(this.userId)
    this.getUserFeed({id: this.userId, page: 0})
    this.getBookShelf(this.userId)
    this.getBookCart(this.userId)
    this.getCollections(this.userId)
    }
  },
  computed: {
    ...mapState('user', ['moveTarget'])
  },
  watch: {
    '$route'() {
      this.userId = this.$route.params.id
      this.getProfileInfo()
    }
  },
  created() {
    this.userId = this.$route.params.id
    this.getProfileInfo ()
  },
};
</script>

<style scoped>
.profile {
  width: 100%;
  background: #ffffff;
  margin-top: 60px;
  height: 100vh;
  border-radius: 30px 0px 0px 0px;
  padding: 20px 20px 80px;
  position: fixed;
  overflow-y: scroll;
  color: #212121;
}
.profile::-webkit-scrollbar {
  display: none;
}
.tabs {
  color: #212121;
  font-size: 13px;
  text-align: center;
  vertical-align: middle;
}
.tabs .current {  
  pointer-events: none;
  font-weight: 700;
  padding: 2px 5px;
  border-bottom: 3px solid #FFDC7C;
}
.tabs .rest {
  padding: 2px 5px;
  border-bottom: 3px solid #C4C4C4;
}
</style>