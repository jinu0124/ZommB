<template>
  <div>
    <SimpleHeader
      class="notification-header"
      :title=title
    />
    <div class="notification-body d-flex flex-column align-items-center gap-3">
      <NotificationItem
        v-for="(note, idx) in notification"
        :key=idx
        :note=note
      />
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import SimpleHeader from '@/components/SimpleHeader'
import NotificationItem from '@/components/user/NotificationItem'

export default {
  name: 'Notification',
  components: {
    SimpleHeader,
    NotificationItem
  },
  data () {
    return {
      title: '알림'
    }
  },
  methods: {
    ...mapActions('user', ['getFollowing']),
  },
  computed: {
    ...mapState('user', ['notification', 'notiCnt', 'myInfo'])
  },
  created () {
    this.getFollowing(this.myInfo.id)
  }
}
</script>

<style scoped>
  .notification-header {
    color: #212121;
  }
  .notification-body {
    background: #fff;
    height: 100%;
    min-height: 100vh;
    width: 100vw;
    border-radius: 30px 0px 0px 0px;
    margin-top: 60px;
    padding: 30px 20px 40px;
    color: #212121;
    position: fixed;
    overflow-y: scroll;
    overflow-x: hidden;
  }
</style>