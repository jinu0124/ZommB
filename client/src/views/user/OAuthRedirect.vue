<template>
    <div>
        소셜 로그인을 기다리는 중입니다~
    </div>
</template>


<script>
import { mapActions } from "vuex"
export default {
    created(){
        this.login();
    },
    methods: {
        ...mapActions('user', ['onSocialLogin']),
        // 소셜로그인
        async login () {
            await this.onSocialLogin(this.userData)
                .catch ((err) => {
                    console.log(err)
                    if (err.status === 403) {
                        this.needEmailConfirm = true
                        this.hasAlert = true
                    } else if (err.status === 401) {
                        this.wrongInput = true
                        setTimeout(() => {
                        this.wrongInput = false
                        }, 2000)
                    }
            })
        },
    },
    computed: {
        userData: function () {
            return {
                'code': this.$route.query.code
        }
    },
  },
}
</script>