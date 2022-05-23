//0
import { login, logout, getInfo, getOffLine } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import Vue from 'vue'

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    permissions: [],

    //websocket
    filterKey: '',
    stomp: null,
    isDot: [],
    sessions: [],
    users: [],
    currentSession: null,
    userId: null
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    },
    SET_USERID: (state, userId) => {
      state.userId = userId
    },
    SET_filterKey: (state, filterKey) => {
      state.filterKey = filterKey
    },
    //websocket
    changeCurrentSession(state, currentSession) {
      Vue.set(state.isDot, localStorage.getItem('userId') + '#' + currentSession.userId, false)
      state.currentSession = currentSession
    },
    addMessage(state, msg) {
      let mss = state.sessions[localStorage.getItem('userId') + '#' + msg.to]
      if (!mss) {
        Vue.set(state.sessions, localStorage.getItem('userId') + '#' + msg.to, [])
      }
      state.sessions[localStorage.getItem('userId') + '#' + msg.to].push({
        content: msg.content,
        date: new Date(),
        self: !msg.notSelf
      })

    },
    INIT_DATA(state) {
      //浏览器本地的历史聊天记录可以在这里完成
      let data = localStorage.getItem('vue-chat-session')

      console.log('getJSON:' + (data === []))
      if (data) {
        state.sessions = JSON.parse(data)
      }
    }

  },

  actions: {
    // 登录
    Login({ commit, dispatch }, userInfo) {
      const username = userInfo.username.trim()
      const password = userInfo.password
      const code = userInfo.code
      const uuid = userInfo.uuid
      return new Promise((resolve, reject) => {
        login(username, password, code, uuid).then(res => {
          setToken(res.token)
          commit('SET_TOKEN', res.token)
          commit('SET_USERID', res.userId)
          localStorage.setItem('userId', res.userId)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo().then(res => {
          const user = res.user
          const avatar = user.avatar == '' ? require('@/assets/images/profile.jpg') : process.env.VUE_APP_BASE_API + user.avatar
          if (res.roles && res.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', res.roles)
            commit('SET_PERMISSIONS', res.permissions)
          } else {
            commit('SET_ROLES', ['ROLE_DEFAULT'])
          }
          commit('SET_NAME', user.userName)
          commit('SET_AVATAR', avatar)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 退出系统
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_PERMISSIONS', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // // 前端 登出
    // FedLogOut({ commit }) {
    //   return new Promise(resolve => {
    //     commit('SET_TOKEN', '')
    //     removeToken()
    //     resolve()
    //   })
    // }
    connect(context) {
      var OffLines = []
      var uid = localStorage.getItem('userId')
      context.state.stomp = Stomp.over(new SockJS('/ws/ep?userId=' + localStorage.getItem('userId')))
      context.state.stomp.connect({}, success => {
        getOffLine(localStorage.getItem('userId')).then(res => {
          OffLines = res.rows
          OffLines.forEach((e) => {
            e.to = e.from
            context.commit('addMessage', e)
            Vue.set(context.state.isDot, uid + '#' + e.from, true)
          })
        })
        context.commit('INIT_DATA')
        context.state.stomp.subscribe('/user/queue/chat', msg => {
          let receiveMsg = JSON.parse(msg.body)
          console.log('receiveMsg: ' + '接受到消息')
          if (!context.state.currentSession || receiveMsg.from != context.state.currentSession.userId) {
            // Notification.info({
            //   title: '【' + receiveMsg.fromNickname + '】发来一条消息',
            //   message: receiveMsg.content.length > 10 ? receiveMsg.content.substr(0, 10) : receiveMsg.content,
            //   position: 'bottom-right'
            // })
            console.log('修改isDot ' + context.state.userId + '#' + receiveMsg.from)
            Vue.set(context.state.isDot, context.state.userId + '#' + receiveMsg.from, true)
          }
          receiveMsg.notSelf = true
          receiveMsg.to = receiveMsg.from
          context.commit('addMessage', receiveMsg)
        })
      }, error => {

      })
    },
    initData(context) {
      context.commit('INIT_DATA')
    }

  }
}

export default user
