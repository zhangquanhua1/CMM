<template>
  <div id="list">
<!--    <footer>-->
<!--      <input class="search" type="text" v-model="filterKey" placeholder="根据用户名称搜索">-->
<!--    </footer>-->
    <ul style="padding-left: 0px">
      <li v-for="item in users" :class="{ active: currentSession?item.userId === currentSession.userId:false}"
          v-on:click="changeCurrentSession(item)"
      >
        <img class="avatar" v-if="item.avatar!=''" :src="'http://localhost:8082'+item.avatar">
        <img class="avatar" v-else src=".././../assets/images/profile.jpg">
        <el-badge :is-dot="isDot[userId+'#'+item.userId]"><p class="name">{{ item.userName }}</p></el-badge>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import store from '@/store'
import { listExceptSelf } from '@/api/system/user'
export default {
  name: 'list',
  data() {
    return {
      currentUserName:store.getters.name,
      users:[],
      userId:localStorage.getItem("userId"),
      currentSession:null,
      isDot:[],
      users2:[],
      filterKey:''
    }
  },
  watch: {
    '$store.state.user.currentSession'(){
      this.currentSession = this.$store.state.user.currentSession
    },
    '$store.state.user.isDot'(){
      this.isDot = this.$store.state.user.isDot
    },
    '$store.state.user.filterKey'(){
      this.users=this.users2.filter((element)=>{
        return element.userName.includes(this.$store.state.user.filterKey);
      })
    }
  },
  created() {
    this.getList()
  },
  methods: {
    changeCurrentSession(currentSession) {
       this.$store.commit('changeCurrentSession', currentSession)
    },
    getList(){
      listExceptSelf().then(response => {
        this.users=response.rows
        this.users2=response.rows
      })
    },
  }

}
</script>

<style lang="scss" scoped>
#list {
  li {
    padding: 16px 15px;
    border-bottom: 1px solid #292C33;
    cursor: pointer;
    list-style: none;

    &:hover {
      background-color: rgba(255, 255, 255, 0.03);
    }
  }

  li.active { /*注意这个是.不是冒号:*/
    background-color: rgba(255, 255, 255, 0.1);
  }

  .avatar {
    border-radius: 2px;
    width: 30px;
    height: 30px;
    vertical-align: middle;
  }

  .name {
    display: inline-block;
    margin-left: 15px;
    margin-top: 0px;
    margin-bottom: 0px;
  }
}
</style>
