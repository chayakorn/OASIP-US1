import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './index.css'
import moment from 'moment'
import { createPinia } from 'pinia'

createApp(App).use(router).use(createPinia()).use(moment).mount('#app')
