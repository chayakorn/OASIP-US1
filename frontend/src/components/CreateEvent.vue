<script setup>
import moment from 'moment'
import { computed, onUpdated, ref } from 'vue'

import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import { useEvents } from '../stores/events.js'

const emit = defineEmits(['closeCreate', 'notice'])
const props = defineProps({
  category: {
    type: Object,
    require: true
  }
})
//use state "Events"
const myEvents = useEvents()

const creatingEvent = computed(() => ({
  bookingName: bookingName.value,
  bookingEmail: email.value,
  eventStartTime: dateTime.value.toISOString(),
  eventEndTime: dateTime.value
    .add(props.category.eventDuration, 'minutes')
    .toISOString(),
  eventCategoryId: { id: props.category.id },
  eventDuration: props.category.eventDuration,
  eventNotes: note.value,
  name: name.value,
  categoryName: props.category.eventCategoryName
}))
const checkNull = (newEvent) => {
  if (
    bookingName.value.length != 0 &&
    email.value.length != 0 &&
    date.value.length != 0 &&
    !error.value
  ) {
    myEvents.createEvent(newEvent)
    reset()
    emit('closeCreate')
    emit('notice', true)
  }
}

const log = (event) => {
  if (event.target.id == 'backdrop') {
    reset()
    emit('closeCreate')
  }
}

const borderStyle =
  'rounded-xl w-full py-2 px-3 mt-2 bg-[#F1F3F4] border border-[#5E6366] focus:outline-none focus:shadow-outline'
const bookingName = ref('')
const name = ref('')
const email = ref('')
const note = ref('')
const date = ref('')
const time = ref('')
const dateTime = computed(() =>
  moment(
    `${moment(date.value).format('YYYY-MM-DD')} ${moment(time.value).format(
      'HH:mm'
    )}`
  )
)
const reset = () => {
  bookingName.value = ''
  email.value = ''
  date.value = ''
  time.value = ''
  note.value = ''
  name.value = ''
}

const error = ref(true)
const prevent = (event) => {
  email.value.includes('.') ? (error.value = false) : (error.value = true)
  event.preventDefault()
  checkNull(creatingEvent.value)
}
const invalid = () => {
  error.value = true
}
const valid = () => {
  error.value = false
}

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
    true
  else {
    date.value = ''
    time.value = ''
    alert('your selected time has been booked, please selete a new time!')
  }
}
</script>

<template>
  <div
    id="backdrop"
    @click="log"
    class="bg-black/25 overflow-x-hidden overflow-y-auto absolute inset-0 z-40 outline-none focus:outline-none justify-center items-center flex"
  >
    <div class="relative bg-white w-10/12 h-4/5 rounded-3xl">
      <div
        @click="emit('closeCreate'), reset()"
        class="cursor-pointer absolute right-0 hover:bg-[#F1F3F4] rounded-lg m-4"
      >
        <svg width="2em" height="2em" viewBox="0 0 24 24">
          <path
            fill="currentColor"
            d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z"
          ></path>
        </svg>
      </div>
      <form action="#" @submit="prevent">
        <div class="h-full mx-14">
          <div class="font-bold text-2xl text-center pt-5">
            Create new appointmenet
          </div>
          <div class="grid grid-cols-3 pt-5">
            <div class="col-span-2">
              <div class="relative mb-5">
                <div class="font-medium">Booking Name | Subject</div>
                <input
                  :class="[borderStyle, 'peer']"
                  type="text"
                  maxlength="100"
                  placeholder="Enter your booking name . . ."
                  v-model="bookingName"
                  required
                />
                <p
                  class="absolute top-0 left-48 ml-2 mt-1 invisible peer-invalid:visible text-red-500 text-xs"
                >
                  * Please fill out this field.
                </p>
                <div
                  v-if="bookingName.length == 100"
                  class="absolute text-xs text-[#F3A72E]"
                >
                  The number of characters has a limit of 100 characters. If it
                  exceeds 100, it will not be able to continue typing.
                </div>
                <div class="absolute right-0 top-4 text-xs text-gray-500">
                  {{ bookingName.length }}/100
                </div>
              </div>
              <div class="mb-5">
                <div class="font-medium">Category</div>
                <div
                  class="rounded-xl w-full py-2 px-3 mt-2 bg-[#F1F3F4] focus:outline-none focus:shadow-outline"
                >
                  {{ category.eventCategoryName }}
                </div>
              </div>
              <div class="relative mb-5">
                <div class="font-medium">Name</div>
                <input
                  :class="[borderStyle]"
                  type="text"
                  placeholder="Firstname Lastname"
                  v-model="name"
                />
              </div>
              <div class="relative mb-5">
                <div class="font-medium">Email</div>
                <input
                  :class="[borderStyle, 'peer']"
                  type="email"
                  maxlength="100"
                  placeholder="username@example.com"
                  v-model="email"
                  @invalid="invalid"
                  @keypress="valid"
                />
                <p
                  class="absolute top-0 left-12 mt-1 invisible peer-invalid:visible text-red-500 text-xs"
                >
                  * Please provide a valid email address.
                </p>
                <div
                  v-if="email.length == 100"
                  class="absolute text-xs text-[#F3A72E]"
                >
                  The number of characters has a limit of 100 characters. If it
                  exceeds 100, it will not be able to continue typing.
                </div>
                <div class="absolute right-0 top-4 text-xs text-gray-500">
                  {{ email.length }}/100
                </div>
              </div>
            </div>
            <div class="bg-[#F7F9FA] ml-10 rounded-2xl grid content-center">
              <div class="grid gap-y-10 ml-10">
                <div class="flex items-center gap-x-2">
                  <svg width="1.5em" height="1.5em" viewBox="0 0 512 512">
                    <path
                      d="M368.005 272h-96v96h96v-96zm-32-208v32h-160V64h-48v32h-24.01c-22.002 0-40 17.998-40 40v272c0 22.002 17.998 40 40 40h304.01c22.002 0 40-17.998 40-40V136c0-22.002-17.998-40-40-40h-24V64h-48zm72 344h-304.01V196h304.01v212z"
                      fill="currentColor"
                    ></path>
                  </svg>
                  <Datepicker
                    v-model="date"
                    :enableTimePicker="false"
                    :format="format"
                    :minDate="new Date()"
                    placeholder="Select Date"
                    hideInputIcon
                    vertical
                  />
                </div>
                <div>
                  <span class="font-medium">Comfirm date : </span>
                  <span :class="[date ? '' : 'text-[#F3A72E]']">
                    {{
                      date
                        ? moment(date).format('DD MMMM YYYY')
                        : 'Please select date !'
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
                    v-model="time"
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
                    :disabled="date == '' ? true : false"
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
                    <span class="font-base text-red-500">{{
                      category.eventDuration
                    }}</span>
                    minutes
                  </div>
                  <div class="text-sm text-gray-400">
                    * Duration depends on category.
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="grid grid-cols-3">
            <div class="relative col-span-2">
              <div class="font-medium">Description | Note</div>
              <textarea
                maxlength="500"
                class="break-words resize-none rounded-xl w-full h-28 py-2 px-3 mt-2 bg-[#F1F3F4] border border-[#5E6366] focus:outline-none focus:shadow-outline"
                placeholder="Enter your description . . ."
                v-model="note"
              />
              <div
                v-if="note.length == 500"
                class="absolute text-xs text-[#F3A72E]"
              >
                The number of characters has a limit of 100 characters. If it
                exceeds 100, it will not be able to continue typing.
              </div>
              <div class="absolute right-0 top-4 text-xs text-gray-500">
                {{ note.length }}/500
              </div>
            </div>
            <div class="ml-10 mb-1 relative">
              <div
                class="grid grid-cols-2 gap-x-10 absolute inset-x-0 bottom-0"
              >
                <div
                  class="bg-[#EA3D2F] cursor-pointer h-10 drop-shadow-lg rounded-md"
                  @click="reset(), emit('closeCreate')"
                >
                  <div class="flex">
                    <div
                      class="bg-white m-1 h-8 w-1/3 rounded grid place-items-center text-[#EA3D2F]"
                    >
                      <svg width="1.5em" height="1.5em" viewBox="0 0 24 24">
                        <path
                          fill="currentColor"
                          d="M12 1.75a3.25 3.25 0 0 1 3.245 3.066L15.25 5h5.25a.75.75 0 0 1 .102 1.493L20.5 6.5h-.796l-1.28 13.02a2.75 2.75 0 0 1-2.561 2.474l-.176.006H8.313a2.75 2.75 0 0 1-2.714-2.307l-.023-.174L4.295 6.5H3.5a.75.75 0 0 1-.743-.648L2.75 5.75a.75.75 0 0 1 .648-.743L3.5 5h5.25A3.25 3.25 0 0 1 12 1.75Zm6.197 4.75H5.802l1.267 12.872a1.25 1.25 0 0 0 1.117 1.122l.127.006h7.374c.6 0 1.109-.425 1.225-1.002l.02-.126L18.196 6.5ZM13.75 9.25a.75.75 0 0 1 .743.648L14.5 10v7a.75.75 0 0 1-1.493.102L13 17v-7a.75.75 0 0 1 .75-.75Zm-3.5 0a.75.75 0 0 1 .743.648L11 10v7a.75.75 0 0 1-1.493.102L9.5 17v-7a.75.75 0 0 1 .75-.75Zm1.75-6a1.75 1.75 0 0 0-1.744 1.606L10.25 5h3.5A1.75 1.75 0 0 0 12 3.25Z"
                        ></path>
                      </svg>
                    </div>
                    <div class="grid place-items-center w-full text-white">
                      CANCEL
                    </div>
                  </div>
                </div>
                <button type="submit">
                  <div
                    class="bg-[#2FA84F] cursor-pointer h-10 drop-shadow-lg rounded-md"
                  >
                    <div class="flex">
                      <div
                        class="bg-white m-1 h-8 w-1/3 rounded grid place-items-center text-[#2FA84F]"
                      >
                        <svg width="1.5em" height="1.5em" viewBox="0 0 24 24">
                          <path
                            fill="currentColor"
                            d="M12 1.75a3.25 3.25 0 0 1 3.245 3.066L15.25 5h5.25a.75.75 0 0 1 .102 1.493L20.5 6.5h-.796l-1.28 13.02a2.75 2.75 0 0 1-2.561 2.474l-.176.006H8.313a2.75 2.75 0 0 1-2.714-2.307l-.023-.174L4.295 6.5H3.5a.75.75 0 0 1-.743-.648L2.75 5.75a.75.75 0 0 1 .648-.743L3.5 5h5.25A3.25 3.25 0 0 1 12 1.75Zm6.197 4.75H5.802l1.267 12.872a1.25 1.25 0 0 0 1.117 1.122l.127.006h7.374c.6 0 1.109-.425 1.225-1.002l.02-.126L18.196 6.5ZM13.75 9.25a.75.75 0 0 1 .743.648L14.5 10v7a.75.75 0 0 1-1.493.102L13 17v-7a.75.75 0 0 1 .75-.75Zm-3.5 0a.75.75 0 0 1 .743.648L11 10v7a.75.75 0 0 1-1.493.102L9.5 17v-7a.75.75 0 0 1 .75-.75Zm1.75-6a1.75 1.75 0 0 0-1.744 1.606L10.25 5h3.5A1.75 1.75 0 0 0 12 3.25Z"
                          ></path>
                        </svg>
                      </div>
                      <div class="grid place-items-center w-full text-white">
                        CREATE
                      </div>
                    </div>
                  </div>
                </button>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<style></style>
