<template>
    <div>
        <h4>Find by Age</h4>
        <div>
            <input type="number" id="age" required v-model="age" name="age">
        </div>
        <div>
            <button v-on:click="searchUserInfo">Search Age</button>
        </div>
        <div class="info-wrapper" v-for="(user, index) in searchedUser" :key="index">
            <h6>name : {{user.name}}</h6>
            <h6>age  : {{user.age}}</h6>
            <h6>ph   : {{user.ph}}</h6>
        </div>
    </div>
</template>

<script>
    import { mapActions, mapGetters } from 'vuex'
    export default {
        name: "SearchUser",
        data(){
            return {
                age: null,
            }
        },
        methods:{
            ...mapActions(['searchUser']),
            searchUserInfo(){
                this.searchUser(this.age)
            }
        },
        computed:{
            ...mapGetters(['searchedUser'])
        }
    }
</script>

<style scoped>
.info-wrapper{
    border: 1px solid black;
}
</style>
