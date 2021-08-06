<template>
  <div>
    <ReportHeader/>
    <ReportBody/>
    <ReportSuccess/>
  </div>
</template>

<script>
import ReportHeader from '@/components/feeds/report/ReportHeader.vue'
import ReportBody from '@/components/feeds/report/ReportBody.vue'
import ReportSuccess from '@/components/feeds/report/ReportSuccess.vue'

export default {
  name: 'Report',
  components: {
    ReportHeader,
    ReportBody,
    ReportSuccess,
  },
}
</script>

<style scoped>

</style>