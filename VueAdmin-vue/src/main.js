import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vuex from 'vuex'

import Element from "element-ui"
import "element-ui/lib/theme-chalk/index.css"

import axios from './axios'
import global from './globalFun'

Vue.prototype.$axios = axios //
Vue.config.productionTip = false

// require("./mock.js")

Vue.use(Element)

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')

if (!window.localStorage) {
    window.localStorage = {
        getItem: function(key) {
            return window.sessionStorage.getItem(key);
        },
        setItem: function(key, value) {
            return window.sessionStorage.setItem(key, value);
        },
        removeItem: function(key) {
            return window.sessionStorage.removeItem(key);
        }
    };
}
