import Vue from 'vue'
import VueRouter from 'vue-router'

import UserList from "../components/UserList.vue"
import AddUser from "../components/AddUser.vue"
import SearchUser from "../components/SearchUser.vue"

Vue.use(VueRouter)

const routes = [
  {
    path: "/userList",
    name: "userList",
    component: UserList,
  },
  {
    path: "/addUser",
    name: "addUser",
    component: AddUser
  },
  {
    path: "/searchUser",
    name: "searchUser",
    component: SearchUser
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
