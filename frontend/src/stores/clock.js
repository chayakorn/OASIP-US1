import moment from 'moment'
import { defineStore, acceptHMRUpdate } from 'pinia'
import { computed, ref } from 'vue'
export const useClock = defineStore('clock', () => {
  const date = ref()
  setInterval(() => {
    date.value = moment(Date.now())
  }, 1000)
  const clock = computed(() => {
    return moment(date.value).format('DD MMM YYYY HH:mm:ss')
  })

  return {
    date,
    clock
  }
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useClock, import.meta.hot))
}
