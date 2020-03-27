<template>
  <div id="app">
    <div>
      <h3>Vue SpringBoot H2 CRUD example</h3>
    </div>
    <nav>
      <button class="mr-15" @click="$router.push('/userList').catch(()=>{})">User List</button>
      <button class="mr-15" @click="$router.push('/addUser').catch(()=>{})">Add User</button>
      <button class="mr-15" @click="$router.push('/searchUser').catch(()=>{})">Search User</button>
    </nav>
    <br/>
    <router-view/>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

  .mr-15 {
    margin: 15px;
  }
</style>
