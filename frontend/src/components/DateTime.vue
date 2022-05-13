<script setup>
import { computed, ref } from 'vue'
import moment from 'moment'
import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'

const props = defineProps({ events: { type: Array, default: [] } })

const date = ref()
const time = ref()
const dateTime = computed(
  () =>
    `${moment(date.value).format('YYYY-MM-DD')} ${moment(time.value).format(
      'HH:mm'
    )}`
)

function getTimeStops(start, end) {
  var startTime = moment(start, 'HH:mm')
  var endTime = moment(end, 'HH:mm')

  if (endTime.isBefore(startTime)) {
    endTime.add(1, 'day')
  }

  var timeStops = []

  while (startTime <= endTime) {
    timeStops.push({
      date: moment(startTime).format('DD-MM-YYYY HH:mm'),
      status: ref(false)
    })
    startTime.add(30, 'minutes')
  }
  return timeStops
}

var timeStops = getTimeStops('00:00', '23:59')
// console.log('timeStops ', timeStops)
// timeStops = getTimeStops('11:00', '23:59')
// console.log('timeStops ', timeStops)
const format = (date) => {
  const day = date.getDate()
  const month = date.getMonth() + 1
  const year = date.getFullYear()

  return `${day}/${month}/${year}`
}
</script>

<template>
  <div>
    <!-- <v-date-picker
      color="blue"
      mode="datetime"
      v-model="date"
      timezone=""
      :available-dates="{ start: new Date(), end: null }"
      :valid-hours="{
        min: date == new Date().getDate ? new Date().getHours() : 1,
        max: 24
      }"
    /> -->
    <Datepicker v-model="date" :enableTimePicker="false" :format="format" :minDate="new Date()" />
    <Datepicker v-model="time" timePicker is24 />
    {{ dateTime }}
    <!-- {{ moment(date).format('DD-MM-YYYY') }}
    {{ moment(time).format('HH:mm') }} -->
    <!-- {{ moment(new Date(`${date} ${time}`)).format('DD-MM-YYYY HH:mm') }} -->
    <div></div>
  </div>
</template>

<style></style>
<!-- moment(new Date(list)).isAfter(
              moment().format('DD-MM-YYYY HH:mm')
            ) &&  -->
