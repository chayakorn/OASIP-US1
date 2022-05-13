import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './index.css'
import moment from 'moment'
import 'v-calendar/dist/style.css'
import VCalendar from 'v-calendar'
import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'

createApp(App)
  .use(router)
  .use(VCalendar, {})
  .use(moment)
  .component('Datepicker', Datepicker)
  .mount('#app')
