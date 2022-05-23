<script setup>
import moment from 'moment'
import { computed, ref } from 'vue'
import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import { useClock } from '../stores/clock.js'
import { useEvents } from '../stores/events.js'
import { useCategories } from '../stores/categories.js'

import Confirm from './Confirm.vue'
const emit = defineEmits(['closeCreate', 'notice'])
const props = defineProps({
  category: {
    type: Object,
    require: true
  }
})

// state
const myClock = useClock()
const myEvents = useEvents()
const myCategory = useCategories()

// necessary variable
const bookingName = ref('')
const currentCategory = computed(() => {
  return {
    cateId: ref(props.category.id)
  }
})
const email = ref('')
const note = ref('')
const date = ref('')
const seletedTime = ref('')
const dateTime = computed(() =>
  moment(`${moment(date.value).format('YYYY-MM-DD')} ${seletedTime.value}`)
)
const currentDuration = computed(() => {
  return { cateDuration: ref(props.category.eventDuration) }
})
const changeDuration = () => {
  currentDuration.value.cateDuration.value =
    myCategory.categoryLists[
      currentCategory.value.cateId.value - 1
    ].eventDuration
  date.value = ''
  seletedTime.value = ''
  timeStops.value = []
}
// create object
const creatingEvent = computed(() => ({
  bookingName: bookingName.value,
  bookingEmail: email.value,
  eventStartTime: dateTime.value.toISOString(),
  eventCategoryId: currentCategory.value.cateId.value,
  eventDuration: currentDuration.value.cateDuration.value,
  eventNotes: note.value
}))

// text field style
const borderStyle =
  'rounded-xl w-full py-2 px-3 pr-8 mt-2 bg-[#F1F3F4] border-2 border-gray-400'

// date formatting
const format = (date) => {
  const day = date.getDate()
  const month = moment(date).format('MMMM')
  const year = date.getFullYear()
  return `${day} ${month} ${year}`
}

// non-overlap
// list for store events to check non-overlap
const list = ref([])
// fetch get events for non-overlap
const getEventByDate = () => {
  myEvents
    .getEventByIdDate(
      currentCategory.value.cateId.value,
      moment(date.value).format('YYYY-MM-DD'),
      moment().format().split('+')[1],
      moment().format().split('T')[1].includes('-')
    )
    .then((result) => {
      list.value = result
    })
}
// function for checking overlap
const checkOverlap = (time) => {
  let selectedStartTime = moment(
    new Date(`${moment(date.value).format('YYYY-MM-DD')} ${time}`),
    'DD-MM-YYYY HH:mm'
  )
  let selectedEndTime = moment(
    new Date(`${moment(date.value).format('YYYY-MM-DD')} ${time}`),
    'DD-MM-YYYY HH:mm'
  ).add(currentDuration.value.cateDuration.value, 'minutes')
  if (
    list.value.some(
      (e) =>
        moment(new Date(e.eventStartTime), 'DD-MM-YYYY HH:mm').isBefore(
          selectedEndTime
        ) &&
        moment(new Date(e.eventStartTime), 'DD-MM-YYYY HH:mm')
          .add(e.eventDuration, 'minutes')
          .isAfter(selectedStartTime)
    )
  )
    return true
  else return false
}

// generate time slots
const timeStops = ref([])
// function for generation time slots
const getTimeStops = (start, end) => {
  // intitial data
  let startTime = moment(start, 'HH:mm')
  let endTime = moment(end, 'HH:mm')
  let last = moment(end, 'HH:mm').subtract(
    currentDuration.value.cateDuration.value - 1,
    'minutes'
  )
  // validate startTime, endTime
  if (endTime.isBefore(startTime)) {
    endTime.add(1, 'day')
  }
  // create time slots
  let timeStops = []
  do {
    timeStops.push(moment(startTime).format('HH:mm'))
    startTime.add(currentDuration.value.cateDuration.value, 'minutes')
  } while (startTime <= last)
  return timeStops
}
// create time slots
const createSlots = () => (timeStops.value = getTimeStops('00:00', '23:59'))

// disable button create if there are empty filled
const canCreateStatus = computed(() =>
  bookingName.value.length > 0 &&
  bookingName.value.length <= 100 &&
  email.value.length > 0 &&
  email.value.length <= 100 &&
  date.value &&
  seletedTime.value.length != 0 &&
  note.value.length <= 500
    ? false
    : true
)

// Verify that all fields pass all conditions.
const validateBeforeCreate = () => {
  if (!canCreateStatus.value && !error.value) {
    createStatus.value = true
  }
}

// prevent submit form
const prevent = (event) => {
  event.preventDefault()
  validateBeforeCreate()
}

// email validation
const error = ref(true)
const invalid = () => {
  error.value = true
}
const valid = () => {
  error.value = false
}

// reset
const reset = () => {
  bookingName.value = ''
  currentCategory.value.cateId.value = props.category.id
  email.value = ''
  date.value = ''
  seletedTime.value = ''
  note.value = ''
}

// confirm create
const createStatus = ref(false)
const createPopup = (status) => {
  if ((createStatus.value = status)) {
    myEvents.createEvent(creatingEvent.value)
    reset()
    createStatus.value = false
    emit('closeCreate')
    emit('notice', true)
  }
}

// confirm cancal
const cancelStatus = ref(false)
const cancelPopup = (status) => {
  if ((cancelStatus.value = status)) {
    emit('closeCreate')
    reset()
    cancelStatus.value = false
  }
}

// is field empty
const isEmpty = () =>
  bookingName.value.length != 0 ||
  email.value.length != 0 ||
  date.value ||
  seletedTime.value.length != 0 ||
  note.value.length != 0
    ? false
    : true

// click backdrop to close
const backdrop = (event) => {
  if (event.target.id == 'modal') {
    isEmpty() ? emit('closeCreate') : (cancelStatus.value = true)
  }
}
</script>

<template>
  <div
    id="modal"
    @click="backdrop"
    class="bg-black/25 overflow-x-hidden overflow-y-auto absolute inset-0 z-40 outline-none focus:outline-none justify-center items-center flex"
  >
    <div class="relative bg-white w-10/12 h-3/4 rounded-3xl">
      <div
        @click="reset()"
        class="cursor-pointer absolute border-2 left-0 hover:bg-[#F1F3F4] p-1 rounded-full m-4"
      >
        Clear all
      </div>
      <div
        @click="isEmpty() ? emit('closeCreate') : (cancelStatus = true)"
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
                  placeholder="Enter your booking name . . ."
                  v-model="bookingName"
                  required
                />
                <p
                  class="absolute top-0 left-48 ml-2 mt-1 invisible peer-invalid:visible text-red-500 text-xs"
                >
                  * Please fill out this field.
                </p>
                <button
                  type="button"
                  v-if="bookingName"
                  @click="bookingName = ''"
                  class="absolute top-10 right-2 ml-2"
                >
                  <svg width="1.5em" height="1.5em" viewBox="0 0 24 24">
                    <path
                      fill="#919699"
                      d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z"
                    ></path>
                  </svg>
                </button>
                <div
                  v-if="bookingName.length >= 100"
                  class="absolute text-xs text-[#F3A72E]"
                >
                  The number of characters has a limit of 100 characters.
                </div>
                <div
                  :class="[
                    'absolute right-0 top-4 text-xs',
                    bookingName.length <= 100 ? 'text-gray-500' : 'text-red-500'
                  ]"
                >
                  {{ bookingName.length }}/100
                </div>
              </div>
              <div class="mb-5">
                <div class="font-medium">Category</div>
                <select
                  :class="borderStyle"
                  v-model="currentCategory.cateId.value"
                  @change="changeDuration"
                >
                  <option
                    v-for="cate in myCategory.categoryLists"
                    :value="cate.id"
                  >
                    {{ cate.eventCategoryName }}
                  </option>
                </select>
              </div>
              <div class="relative mb-5">
                <div class="font-medium">Email</div>
                <input
                  :class="[borderStyle, 'peer']"
                  type="email"
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
                <button
                  type="button"
                  v-if="email"
                  @click="email = ''"
                  class="absolute top-10 right-2 ml-2"
                >
                  <svg width="1.5em" height="1.5em" viewBox="0 0 24 24">
                    <path
                      fill="#919699"
                      d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z"
                    ></path>
                  </svg>
                </button>
                <p
                  v-if="email.length <= 0"
                  class="absolute top-0 left-12 mt-1 text-red-500 text-xs"
                >
                  * Please fill out this field.
                </p>
                <div
                  v-if="email.length >= 100"
                  class="absolute text-xs text-[#F3A72E]"
                >
                  The number of characters has a limit of 100 characters.
                </div>
                <div
                  :class="[
                    'absolute right-0 top-4 text-xs',
                    email.length <= 100 ? 'text-gray-500' : 'text-red-500'
                  ]"
                >
                  {{ email.length }}/100
                </div>
              </div>
              <div class="relative col-span-2">
                <div class="font-medium">Description | Note</div>
                <textarea
                  class="container break-words resize-none rounded-xl w-full h-44 py-2 px-3 pr-8 mt-2 bg-[#F1F3F4] border border-gray-400"
                  placeholder="Enter your description . . ."
                  v-model="note"
                />
                <button
                  type="button"
                  v-if="note"
                  @click="note = ''"
                  class="absolute top-10 right-2 ml-2"
                >
                  <svg width="1.5em" height="1.5em" viewBox="0 0 24 24">
                    <path
                      fill="#919699"
                      d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z"
                    ></path>
                  </svg>
                </button>
                <div
                  v-if="note.length >= 500"
                  class="absolute text-xs text-[#F3A72E]"
                >
                  The number of characters has a limit of 500 characters.
                </div>
                <div
                  :class="[
                    'absolute right-0 top-4 text-xs',
                    note.length <= 500 ? 'text-gray-500' : 'text-red-500'
                  ]"
                >
                  {{ note.length }}/500
                </div>
              </div>
            </div>
            <div class="relative ml-10">
              <div class="grid bg-[#F7F9FA] rounded-2xl h-max gap-y-4 p-5">
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
                    @closed="getEventByDate(), createSlots()"
                    @cleared="seletedTime = ''"
                  />
                </div>
                <div class="flex items-center gap-x-1 h-9">
                  <span class="font-medium">Comfirm date : </span>
                  <span
                    :class="[
                      date
                        ? 'bg-white p-1 px-2 border-2 rounded-lg font-semibold'
                        : 'text-[#F3A72E]'
                    ]"
                  >
                    {{
                      date
                        ? moment(date).format('DD MMMM YYYY')
                        : 'Please select date !'
                    }}</span
                  >
                  <button
                    type="button"
                    v-if="date"
                    @click=";(date = ''), (seletedTime = '')"
                    class="text-xs font-semibold text-[#367BF5] hover:underline"
                  >
                    change
                  </button>
                </div>
                <div
                  class="grid grid-cols-2 w-full gap-4 bg-white rounded-lg border-2 p-3 h-40 overflow-auto"
                >
                  <div v-if="date" v-for="slot in timeStops">
                    <button
                      type="button"
                      @click="seletedTime = slot"
                      :disabled="
                        checkOverlap(slot) ||
                        (slot < moment(myClock.clock).format('HH:mm') &&
                          moment(date).format('YYYY-MM-DD') ==
                            moment(myClock.clock).format('YYYY-MM-DD'))
                      "
                      class="w-full cursor-pointer rounded-lg bg-[#DCF7E3] border disabled:opacity-25 disabled:bg-[#E3E5E5] disabled:cursor-not-allowed"
                    >
                      {{ slot }} -
                      {{
                        moment(slot, 'HH:mm')
                          .add(currentDuration.cateDuration.value, 'minutes')
                          .format('HH:mm')
                      }}
                    </button>
                  </div>
                  <div
                    v-else
                    class="col-span-3 grid place-content-center text-3xl font-bold text-[#C6CACC]"
                  >
                    Time slots
                  </div>
                </div>
                <div class="flex items-center gap-x-1 h-9">
                  <span class="font-medium">Comfirm time : </span>
                  <span
                    :class="[
                      seletedTime
                        ? 'bg-white p-1 px-2 border-2 rounded-lg font-semibold'
                        : 'text-[#F3A72E]'
                    ]"
                  >
                    {{
                      seletedTime
                        ? seletedTime +
                          '-' +
                          moment(seletedTime, 'HH:mm')
                            .add(props.category.eventDuration, 'minutes')
                            .format('HH:mm')
                        : 'Please select time !'
                    }}</span
                  ><button
                    type="button"
                    v-if="seletedTime"
                    @click="seletedTime = ''"
                    class="text-xs font-semibold text-[#367BF5] hover:underline"
                  >
                    change
                  </button>
                </div>
                <div>
                  <div>
                    <span class="font-medium">Durations: </span>
                    <span
                      class="font-semibold text-red-500 h-9 bg-white p-1 px-2 border-2 rounded-md"
                      >{{ currentDuration.cateDuration.value }}</span
                    >
                    minutes
                  </div>
                  <div class="text-sm mt-2 text-gray-400">
                    * Duration depends on category.
                  </div>
                </div>
              </div>
              <div class="grid grid-cols-2 absolute bottom-2 w-full gap-x-10">
                <button
                  type="button"
                  @click="
                    isEmpty() ? emit('closeCreate') : (cancelStatus = true)
                  "
                >
                  <div class="bg-[#EA3D2F] h-10 drop-shadow-lg rounded-md">
                    <div class="flex">
                      <div
                        class="bg-white m-1 h-8 w-1/3 rounded grid place-items-center text-[#EA3D2F]"
                      >
                        <svg width="2em" height="2em" viewBox="0 0 24 24">
                          <path
                            fill="currentColor"
                            d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z"
                          ></path>
                        </svg>
                      </div>
                      <div class="grid place-items-center w-full text-white">
                        CANCEL
                      </div>
                    </div>
                  </div>
                </button>
                <button
                  :disabled="canCreateStatus"
                  class="disabled:opacity-25 disabled:cursor-not-allowed"
                  type="submit"
                >
                  <div class="bg-[#2FA84F] h-10 drop-shadow-lg rounded-md">
                    <div class="flex">
                      <div
                        class="bg-white m-1 h-8 w-1/3 rounded grid place-items-center text-[#2FA84F]"
                      >
                        <svg width="2em" height="2em" viewBox="0 0 24 24">
                          <path
                            fill="currentColor"
                            d="M18 12.998h-5v5a1 1 0 0 1-2 0v-5H6a1 1 0 0 1 0-2h5v-5a1 1 0 0 1 2 0v5h5a1 1 0 0 1 0 2z"
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
    <Confirm
      v-if="cancelStatus"
      underline="cancel"
      color="EA3D2F"
      desc="If you <confirm>, the information you enter will not be created."
      @cancel="cancelPopup"
      @confirm="cancelPopup"
    />
    <Confirm
      v-if="createStatus"
      underline="create"
      color="2FA84F"
      desc="If you <confirm>, this appointment will be created."
      @cancel="createPopup"
      @confirm="createPopup"
    />
  </div>
</template>

<style scoped>
.container::-webkit-scrollbar {
  display: none;
}
</style>
