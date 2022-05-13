<script setup>
import { ref } from 'vue'
import moment from 'moment'
const props = defineProps({ events: { type: Array, default: [] } })

const date = ref()

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
    <Datepicker v-model="date" :time-picker-component="timePicker" />
    {{ moment(new Date(date)).format('YYYY-MM-DD') }}
    <div></div>
  </div>
</template>

<style></style>
<!-- moment(new Date(list)).isAfter(
              moment().format('DD-MM-YYYY HH:mm')
            ) &&  -->
