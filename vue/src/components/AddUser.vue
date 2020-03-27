<template>
    <div>
        <div class="input-box-wrapper" v-if="!submitted">
            <div class="mr-10">
                <label for="name">Name</label>
                <input type="text" id="name" required v-model="user.name" name="name">
            </div>
            <div class="mr-10">
                <label for="age">Age</label>
                <input type="number" id="age" required v-model="user.age" name="age">
            </div>
            <div class="mr-10">
                <label for="age">PH</label>
                <input type="text" id="ph" required v-model="user.ph" name="ph">
            </div>
            <button v-on:click="saveCustomer">Save</button>
        </div>

        <div v-else>
            <h4>You submitted successfully!</h4>
            <button v-on:click="newCustomer">Add</button>
        </div>
    </div>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        name: "AddUser",
        data(){
            return {
                submitted: false,
                user:{}
            }
        },
        methods:{
            ...mapActions(['addUser']),
            saveCustomer(){
                this.addUser(this.user)
                this.submitted = true
            },
            newCustomer(){
                this.user = {}
                this.submitted = false
            }
        },
    }
</script>

<style scoped>
    .input-box-wrapper{
        margin: auto;
        text-align: left;
        max-width: 250px;
    }
.mr-10{
    margin: 10px;
}
</style>
