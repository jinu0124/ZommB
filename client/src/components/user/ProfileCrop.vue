<template>
  <div class="profile-crop modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen-lg-down">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">프로필 사진 선택</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body d-flex justify-content-center">
          <croppa 
            v-model="myCroppa"
            :width="320"
            :height="320"
            :quality="2"
            :accept="'image/*'"
            :remove-button-color="'#683EC9'"
            placeholder="프로필 사진을 선택해주세요"
            :placeholder-font-size="0"
            :disabled="false"
            :prevent-white-space="true"
            :show-remove-button="true">
          </croppa>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary">Save changes</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import Croppa from 'vue-croppa'
import 'vue-croppa/dist/vue-croppa.css'
Vue.use(Croppa)

export default {
  name: 'ProfileCrop',
  data: () => {
    return {
      myCroppa: null,
    }
  },
}
</script>

<style scoped>
  .modal-content {
    border: none;
    outline: 0;
  }
  .modal-header {
    border-radius: 0;
    border: none;
  }
  .modal-title {
    font-weight: 700;
  }
  .modal-body {
    padding: 0;
  }
</style>