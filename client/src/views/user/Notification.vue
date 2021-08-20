<template>
  <div>
    <SimpleHeader
      class="notification-header"
      :title=title
    />
    <div class="notification-body d-flex flex-column align-items-center gap-3">
      <div v-if="notifications.length" class="align-self-end delete-alert mb-2">
        <i 
          class="fi-sr-cross-circle del-btn"
          @click="resetAlert"
        ></i>
        <button 
          class="btn-primary1 del-text"
          @click="resetAlert"
        >알림 모두 읽음</button>
      </div>
      <NotificationItem
        v-for="(note, idx) in notifications"
        :key=idx
        :note=note
      />
      <div
        v-if="!notifications.length"
        class="no-result mt-5"
      >새로운 알림이 없습니다.</div>
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
    resetAlert () {
      this.$store.commit('user/RESET_NOTIFICATION')
    }
  },
  computed: {
    ...mapState('user', ['notification', 'myInfo']),
    notifications () {
      return this.notification.slice().reverse()
    }
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
    padding: 20px 20px 100px;
    color: #212121;
    position: fixed;
    overflow-y: scroll;
    overflow-x: hidden;
  }
  .delete-alert {
    position: relative;
    width: fit-content;
    height: fit-content;
  }
  .del-btn {
    position: absolute;
    opacity: 1;
    right: 0;
    bottom: -8px;
    font-size: 25px;
    transition: all 0.5s ease-in-out;
    color: #683ec9;
    z-index: 2;
    cursor: pointer;
  }
  .del-text {
    border: none;
    outline: none;
    width: fit-content;
    font-size: 0.75rem;
    font-weight: 500;
    padding-left: 15px;
    padding-right: 35px;
    height: 24.8px;
    border-radius: 13px;
    opacity: 0;
    transition: all 0.7s ease-in-out;
  }
  .delete-alert:hover .del-btn {
    color: #FFDC7C;
  }
  .delete-alert:hover .del-text {
    opacity: 1;
  } 
  .no-result {
    color: #C4C4C4;
  }
</style>