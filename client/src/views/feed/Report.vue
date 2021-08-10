<template>
  <div>
    <ReportBody/>
    <ReportSuccess/>
  </div>
</template>

<script>
import ReportBody from '@/components/feeds/report/ReportBody.vue'
import ReportSuccess from '@/components/feeds/report/ReportSuccess.vue'

export default {
  name: 'Report',
  components: {
    ReportBody,
    ReportSuccess,
  },
}
</script>

<style scoped>

</style>