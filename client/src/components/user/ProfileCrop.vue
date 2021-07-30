<template>
  <div class="profile-crop modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen-lg-down">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">프로필 사진 선택</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body d-flex flex-column align-items-center">
          <croppa 
            v-model="croppa"
            :width="320"
            :height="320"
            :quality="2"
            :accept="'image/*'"
            :remove-button-color="'#683EC9'"
            placeholder="프로필 사진을 선택해주세요"
            :placeholder-font-size="0"
            :disabled="false"
            :prevent-white-space="true"
            :show-remove-button="false">
          </croppa>
          <div class="d-flex mt-2 gap-2">
            <button
              class="btn-5 btn-grey"
              @click="croppa.remove()"
            >삭제</button>
            <button
              class="btn-5 btn-yellow"
              @click="croppa.chooseFile()"
            >다시 선택</button>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
          <button
            type="button"
            class="btn btn-primary"
            data-bs-dismiss="modal"
          >저장</button>
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
      croppa: null,
    }
  },
  methods: {

  }
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