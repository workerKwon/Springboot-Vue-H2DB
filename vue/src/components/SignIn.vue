<template>
  <div>
    <div class="input-box-wrapper">
      <div class="mr-10">
        <label for="email">Email</label>
        <input type="text" id="email" required v-model="loginInfo.email" name="email">
      </div>
      <div class="mr-10">
        <label for="password">Password</label>
        <input type="password" id="password" required v-model="loginInfo.password" name="password">
      </div>
      <button v-on:click="login">login</button>
    </div>
  </div>
</template>

<script>
import {mapActions} from 'vuex'

export default {
  name: "SignIn",
  data() {
    return {
      loginInfo: {}
    }
  },
  methods: {
    ...mapActions([
        'signIn'
    ]),
    login() {
      this.signIn(this.loginInfo)
    }
  }
}
</script>

<style scoped>

</style>