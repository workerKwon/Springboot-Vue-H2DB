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
        readUserList(store) {
            http.get('/userList')
                .then(response => {
                    store.commit('setUserList', response.data)
                })
                .catch(error => {
                    console.log(error)
                })
        },
        addUser(store, userInfo) {
            http.post('/user', userInfo)
                .then(() => {})
                .catch(error => {
                    console.log(error)
                })
        },
        updateUserActive(store, userInfo) {
            http.put(`/user/${userInfo.id}`, userInfo)
                .then(() => {})
                .catch(error => {
                    console.log(error)
                })
        },
        deleteUser(store, userId) {
            http.delete(`/user/${userId}`)
                .then(() => {
                    store.dispatch('readUserList')
                })
                .catch(error => {
                    console.log(error)
                })
        },
        searchUser(store, userAge) {
            http.get(`/users/age/${userAge}`)
                .then(response => {
                    store.commit('setSearchedUser', response.data)
                })
                .catch(error => {
                    console.log(error)
                })
        }
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
