<!-- <script setup>
import { computed, ref } from 'vue'
import moment from 'moment'
import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import { useEvents } from '../stores/events.js'

const emits = defineEmits(['sendDate', 'sendTime'])
const props = defineProps({
  category: { type: Object, default: {} },
  date: { type: String, default: '' },
  time: { type: String, default: '' }
})

const dateTime = computed(() => {
  return { date: props.date, time: props.time }
})

// const date = ref()
// const time = ref()
// const dateTime = computed(
//   () =>
//     `${moment(date.value).format('YYYY-MM-DD')} ${moment(time.value).format(
//       'HH:mm'
//     )}`
// )

// const selectedDateTime = computed(() => {
//   return { date: props.date, time: props.time }
// })

// function getTimeStops(start, end) {
//   var startTime = moment(start, 'HH:mm')
//   var endTime = moment(end, 'HH:mm')

//   if (endTime.isBefore(startTime)) {
//     endTime.add(1, 'day')
//   }

//   var timeStops = []

//   while (startTime <= endTime) {
//     timeStops.push({
//       date: moment(startTime).format('DD-MM-YYYY HH:mm'),
//       status: ref(false)
//     })
//     startTime.add(30, 'minutes')
//   }
//   return timeStops
// }

// var timeStops = getTimeStops('00:00', '23:59')
const myEvents = useEvents()
const eventLists = computed(() => myEvents.eventLists)
// GET
myEvents.getAllEvents()

const format = (date) => {
  const day = date.getDate()
  const month = moment(date).format('MMMM')
  const year = date.getFullYear()

  return `${day} ${month} ${year}`
}

const checkOverlap = () => {
  let selectedStartTime = moment(new Date(dateTime.value), 'DD-MM-YYYY HH:mm')
  let selectedEndTime = moment(
    new Date(dateTime.value),
    'DD-MM-YYYY HH:mm'
  ).add(props.category.eventDuration, 'minutes')
  if (
    !eventLists.value
      .filter((item) => item.eventCategoryId == props.category.id)
      .some((e) => {
        if (
          selectedStartTime.isBetween(
            moment(new Date(e.eventStartTime), 'DD-MM-YYYY HH:mm').add(
              -1,
              'minutes'
            ),
            moment(new Date(e.eventEndTime), 'DD-MM-YYYY HH:mm').add(
              1,
              'minutes'
            )
          ) ||
          moment(new Date(e.eventStartTime), 'DD-MM-YYYY HH:mm').isBetween(
            selectedStartTime.add(-1, 'minutes'),
            selectedEndTime.add(1, 'minutes')
          ) ||
          moment(new Date(e.eventEndTime), 'DD-MM-YYYY HH:mm').isBetween(
            selectedStartTime.add(-1, 'minutes'),
            selectedEndTime.add(1, 'minutes')
          )
        )
          return true
        else return false
      })
  )
    // {
    //   console.log(date.value)
    //   console.log(moment(time.value).format('HH:mm'))
    //   console.log(dateTime.value)
    // }
    emits('sendTime', moment(dateTime.value.time).format('HH:mm'))
  else alert('your selected time has been booked, please choose a new time!')
}
</script>

<template>
  <div class="grid gap-y-10 ml-10">
    <div class="flex items-center gap-x-2">
      <svg width="1.5em" height="1.5em" viewBox="0 0 512 512">
        <path
          d="M368.005 272h-96v96h96v-96zm-32-208v32h-160V64h-48v32h-24.01c-22.002 0-40 17.998-40 40v272c0 22.002 17.998 40 40 40h304.01c22.002 0 40-17.998 40-40V136c0-22.002-17.998-40-40-40h-24V64h-48zm72 344h-304.01V196h304.01v212z"
          fill="currentColor"
        ></path>
      </svg>
      <Datepicker
        v-model="dateTime.date"
        :enableTimePicker="false"
        :format="format"
        :minDate="new Date()"
        placeholder="Select Date"
        hideInputIcon
        vertical
        @closed="$emit('sendDate', moment(dateTime.date).format('YYYY-MM-DD'))"
      />
    </div>
    <div>
      <span class="font-medium">Comfirm date : </span>
      <span :class="[date ? '' : 'text-[#F3A72E]']">
        {{
          date ? moment(date).format('DD MMMM YYYY') : 'Please select date !'
        }}</span
      >
    </div>
    <div class="flex items-center gap-x-2">
      <svg width="1.5em" height="1.5em" viewBox="0 0 24 24">
        <path
          fill="currentColor"
          d="M12 20c4.4 0 8-3.6 8-8s-3.6-8-8-8s-8 3.6-8 8s3.6 8 8 8m0-18c5.5 0 10 4.5 10 10s-4.5 10-10 10S2 17.5 2 12S6.5 2 12 2m5 11.9l-.7 1.3l-5.3-2.9V7h1.5v4.4l4.5 2.5Z"
        ></path>
      </svg>
      <Datepicker
        v-model="dateTime.time"
        timePicker
        is24
        :minDate="new Date()"
        :minTime="{
          hours: moment(date).isAfter(moment(new Date()))
            ? null
            : new Date().getHours(),
          minutes: moment(date).isAfter(moment(new Date()))
            ? null
            : new Date().getMinutes()
        }"
        placeholder="Select Time"
        :disabled="date == undefined ? true : false"
        hideInputIcon
        @closed="checkOverlap"
      />
    </div>
    <div>
      <span class="font-medium">Comfirm time : </span>
      <span :class="[time ? '' : 'text-[#F3A72E]']">
        {{
          time
            ? `${moment(time).format('HH:mm')} - ${moment(time)
                .add(props.category.eventDuration, 'minutes')
                .format('HH:mm')}`
            : 'Please select time !'
        }}</span
      >
    </div>
    <div>
      <div>
        <span class="font-medium">Durations: </span>
        <span class="font-base text-red-500">{{ category.eventDuration }}</span>
        minutes
      </div>
      <div class="text-sm text-gray-400">* Duration depends on category.</div>
    </div>
  </div>
</template>

<style></style>
 -->
