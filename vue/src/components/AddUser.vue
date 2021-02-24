<template>
  <div>
    <div class="input-box-wrapper">
      <div class="mr-10">
        <label for="email">Email</label>
        <input type="text" id="email" required v-model="signUpInfo.email" name="email">
      </div>
      <div class="mr-10">
        <label for="name">Name</label>
        <input type="text" id="name" required v-model="signUpInfo.name" name="name">
      </div>
      <div class="mr-10">
        <label for="password">Password</label>
        <input type="password" id="password" required v-model="signUpInfo.password" name="password">
      </div>
      <div class="mr-10">
        <label>ROLE</label>
        <input type="radio" id="admin" required v-model="admin" name="roles">
        <input type="radio" id="user" required v-model="user" name="roles">
      </div>
      <button v-on:click="saveCustomer">Save</button>
    </div>
  </div>
</template>

<script>
import {mapActions} from 'vuex'

export default {
  name: "AddUser",
  data() {
    return {
      signUpInfo: {},
      admin: ['ROLE_ADMIN', 'ROLE_USER'],
      user: ['ROLE_USER']
    }
  },
  methods: {
    ...mapActions(['signUp']),
    saveCustomer() {
      this.signUpInfo.roles = ['ROLE_ADMIN']
      this.signUp(this.signUpInfo)
    },
  },
}
</script>

<style scoped>
.input-box-wrapper {
  margin: auto;
  text-align: left;
  max-width: 250px;
}

.mr-10 {
  margin: 10px;
}
</style>
