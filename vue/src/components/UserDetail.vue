<template>
    <div v-if="this.user">
        <h4>Customer</h4>
        <div>
            <label>Name: </label> {{this.user.name}}
        </div>
        <div>
            <label>Age: </label> {{this.user.age}}
        </div>
        <div>
            <label>PH: </label> {{this.user.ph}}
        </div>
        <div>
            <label>Active: </label> {{this.user.active}}
        </div>
        <button v-if="this.user.active"
              v-on:click="updateUser(false)"
              class=''>Inactive</button>
        <button v-else
              v-on:click="updateUser(true)"
              class="">Active</button>
        <button class="" @click="deleteUserId(user)">Delete</button>
    </div>
    <div v-else>
        <br/>
        <p>Click on a User</p>
    </div>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        name: "User",
        props:["user"],
        methods: {
            ...mapActions(['deleteUser', 'updateUserActive']),
            updateUser(status){
                this.user.active = status;
                this.updateUserActive(this.user)
            },
            deleteUserId(user){
                this.deleteUser(user.id)
                this.$emit('initUserDetail')
            }
        },
    }
</script>

<style scoped>

</style>
