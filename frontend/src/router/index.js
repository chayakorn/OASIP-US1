import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import EventLists from '../views/EventLists.vue'
import Calendar from '../views/Calendar.vue'
import Setting from '../views/Setting.vue'
import NotFound from '../views/NotFound.vue'
import DevelopBy from '../views/DevelopBy.vue'
const history = createWebHistory('/us1')
const routes = [
  {
    path: '/:catchNotMatchPath(.*)',
    name: 'NotFound',
    component: NotFound
  },
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/event-lists',
    name: 'EventLists',
    component: EventLists
  },
  {
    path: '/calendar',
    name: 'Calendar',
    component: Calendar
  },
  {
    path: '/setting',
    name: 'Setting',
    component: Setting
  },
  {
    path: '/develop-by',
    name: 'DevelopBy',
    component: DevelopBy
  }
]
const router = createRouter({ history, routes })
export default router
