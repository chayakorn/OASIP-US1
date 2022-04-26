import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import EventLists from '../views/EventLists.vue'
import EventDetails from '../views/EventDetails.vue'
import Calendar from '../views/Calendar.vue'
import Setting from '../views/Setting.vue'
import NotFound from '../views/NotFound.vue'
const history = createWebHistory()
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
    path: '/event-lists/:eventId',
    name: 'EventDetails',
    component: EventDetails
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
  }
]
const router = createRouter({ history, routes })
export default router
