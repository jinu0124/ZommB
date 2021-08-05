<template>
  <div>
    <ReportHeader/>
    <ReportBody/>
  </div>
</template>

<script>
import ReportHeader from '@/components/feeds/report/ReportHeader.vue'
import ReportBody from '@/components/feeds/report/ReportBody.vue'

export default {
  name: 'Report',
  components: {
    ReportHeader,
    ReportBody,
  },
}
</script>

<style>

</style>