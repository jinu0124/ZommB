<template>
  <div class="profile-crop modal fade" id="profileCropModal" tabindex="-1" aria-labelledby="profileCropModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen-sm-down">
      <div class="modal-content">
        <div class="modal-header d-flex align-items-center">
          <i 
            class="back-btn fi-rr-angle-small-left"
            type="button"
            data-bs-dismiss="modal"
          ></i>
          <span class="modal-title" id="profileCropModalLabel">프로필 변경</span>
        </div>
        <div class="modal-body d-flex flex-column align-items-center">
          <croppa 
            v-model="croppa"
            class="croppa-img"
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
          <div v-if="isSubmit" class="d-flex gap-3">
            <button
              class="btn-5 btn-grey"
              @click="croppa.remove()"
            >삭제</button>
            <button
              class="btn-5 btn-yellow"
              @click="croppa.chooseFile()"
            >다시 선택</button>
          </div>
          <button
            class="btn-2 mt-3 btn-primary1"
            v-if="isSubmit"
            @click="sendCroppa"
            data-bs-dismiss="modal"
          >선택 완료
          </button>
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
    sendCroppa () {
      this.$emit('select-croppa', this.croppa)
      if (!this.croppa.hasImage()) {
        alert('no image to upload')
        return
      }
    }
  },
  computed: {
    isSubmit () {
      if (this.croppa) {
        return this.croppa.hasImage()
      }
      return false
    }
  }
}
</script>

<style scoped>
  .croppa-img {
    margin: 0 auto 20px;
  }
  .modal.show {
    transition: none !important;
  }
  .modal-content {
    border: none;
    outline: 0;
    /* background: #7B60F1; */
  }
  .modal-header {
    border-radius: 0;
    border: none;
    height: 60px;
    /* background: #7B60F1; */
    padding: 12px 20px;
  }
  .modal-title {
    font-weight: 700;
    color: #683EC9;
    font-size: 1.2rem;
    margin: 0 auto;
  }
  .modal-body {
    box-sizing: border-box;
    background: #fff;
    padding: 2rem 0;
    border-radius: 0.3rem;
  }
  .back-btn {
    padding-top: 5px;
    font-size: 1.5rem;
    color: #683EC9;
  }
  .modal-footer {
    background: #fff;
  }
</style>