<template>
    <div class="list">
        <div>
            <h4>User List</h4>
            <ul>
                <li v-for="(user, index) in userList" :key="index">
                    <div @click="userDetail(user)">
                        {{user.name}}
                    </div>
                </li>
            </ul>
        </div>
        <div>
            <userDetail :user="userInfo" @initUserDetail="initUserDetail"></userDetail>
        </div>
    </div>
</template>

<script>
    import userDetail from './UserDetail'
    import {mapActions, mapGetters} from 'vuex'
    export default {
        name: "UserList",
        data(){
            return {
                userInfo: null
            }
        },
        methods: {
            ...mapActions(['readUserList']),
            userDetail(user){
                this.userInfo = user
            },
            initUserDetail(){
                this.userInfo = null
            }
        },
        computed:{
            ...mapGetters(['userList']),
        },
        components:{
            userDetail
        },
        mounted(){
            this.readUserList()
        }

    }
</script>

<style scoped>
    .list {
        text-align: left;
        max-width: 450px;
        margin: auto;
    }
</style>
