import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './index.css'
import moment from 'moment'

createApp(App).use(router).use(moment).mount('#app')
