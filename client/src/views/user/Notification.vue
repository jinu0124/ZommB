<template>
  <div>
    <SimpleHeader
      class="notification-header"
      :title=title
    />
    <div class="notification-body">
      알림이 들어갈 예정
    </div>
  </div>
</template>

<script>
import SimpleHeader from '@/components/SimpleHeader'
export default {
  name: 'Notification',
  components: {
    SimpleHeader
  },
  data () {
    return {
      title: '알림'
    }
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
    padding: 20px 20px 40px;
    color: #212121;
    position: fixed;
    overflow-y: scroll;
    overflow-x: hidden;
  }
</style>