import Vue from 'vue'
import Vuex from 'vuex'
import http from '../http-common'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        userList: [],
        searchedUser: {},
    },
    mutations: {
        setUserList(state, payload) {
            state.userList = payload;
        },
        setSearchedUser(state, payload) {
            state.searchedUser = payload;
        }
    },
    actions: {
        signIn(store, loginInfo){
            const info = new FormData();
            info.append('email', loginInfo.email)
            info.append('password', loginInfo.password)
            http.post('/sign/signIn', info)
                .then(res => {
                    localStorage.setItem("X-AUTH-TOKEN", res.data)
                    console.log(res.data)
                })
                .catch(err => {
                    console.log(err)
                })
        },
        signUp(store, signupInfo){
            const info = new FormData();
            info.append('email', signupInfo.email)
            info.append('password', signupInfo.password)
            info.append('name', signupInfo.name)
            info.append('roles',signupInfo.roles)
            http.post('/sign/signUp', info)
                .then(res => {
                    console.log(res.data)
                })
                .catch(err => {
                    console.log(err)
                })
        },
        readUserList(store) {
            http.get('/list')
                .then(response => {
                    console.log(response.data)
                    store.commit('setUserList', response.data)
                })
                .catch(error => {
                    console.log(error)
                })
        },
        updateUserActive(store, userInfo) {
            http.put(`/update/${userInfo.id}`, userInfo)
                .then(() => {})
                .catch(error => {
                    console.log(error)
                })
        },
        deleteUser(store, userId) {
            http.delete(`/delete/${userId}`)
                .then(() => {
                    store.dispatch('readUserList')
                })
                .catch(error => {
                    console.log(error)
                })
        },
    },
    getters: {
        userList: state => {
            return state.userList
        },
        searchedUser: state => {
            return state.searchedUser
        }
    }
})
