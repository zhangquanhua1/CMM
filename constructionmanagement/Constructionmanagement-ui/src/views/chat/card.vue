<template>
  <div id="card">
  	<header>
  		<img class="avatar" v-bind:src="avatar" v-bind:alt="userName">
  		<p class="name">{{userName}}</p>
  	</header>
    <footer>
      <input class="search" type="text" v-model="filterKey" placeholder="根据用户名称搜索" >
    </footer>
  </div>
</template>

<script>
import store from '@/store'

export default {
  name: 'card',
  data () {
    return {
      avatar: store.getters.avatar,
      userName: store.getters.name,
      filterKey:''
    }
  },
  watch:{
    filterKey(n,o){
     this.$store.commit("SET_filterKey",n)
    }
  },

}
</script>

<style lang="scss" scoped>
#card {
	padding: 12px;
  .avatar{
  	width: 40px;
  	height: 40px;
  	vertical-align: middle;/*这个是图片和文字居中对齐*/
  }
  .name {
  	display: inline-block;
  	padding: 10px;
  	margin-bottom: 15px;
  	font-size: 16px;
  }
  .search {
  	background: #26292E;
  	height: 30px;
  	line-height: 30px;
  	padding: 0 10px;
  	border: 1px solid #3a3a3a;
  	border-radius: 4px;
  	outline: none;/*鼠标点击后不会出现蓝色边框*/
    color: #FFF;
  }
}
</style>
