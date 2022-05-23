//0
import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import user from './modules/user'
import tagsView from './modules/tagsView'
import permission from './modules/permission'
import settings from './modules/settings'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    user,
    tagsView,
    permission,
    settings
  },
  getters
})

// store.watch(function (state) {
//   return state.user.sessions
// }, function (val) {
//   localStorage.setItem('vue-chat-session', JSON.stringify(val));
// }, {
//   deep: true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
// })
export default store
