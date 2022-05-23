<script setup>
import moment from 'moment'
import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import { computed, ref } from 'vue'
import { useEvents } from '../stores/events.js'
import Confirm from './Confirm.vue'
import { useClock } from '../stores/clock.js'
const emit = defineEmits(['cancel', 'save', 'closeModal'])
const props = defineProps({
  item: {
    type: Object,
    require: true
  },
  color: {
    type: String,
    require: true
  },
  isEditMode: {
    type: Boolean,
    require: true
  }
})
const myClock = useClock()
const myEvents = useEvents()

const isEditMode = computed(() => props.isEditMode)

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
      props.item.eventCategoryId,
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
  ).add(props.item.eventDuration, 'minutes')
  if (
    list.value.some(
      (e) =>
        props.item.id != e.id &&
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
    props.item.eventDuration - 1,
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
    startTime.add(props.item.eventDuration, 'minutes')
  } while (startTime <= last)
  return timeStops
}
// create time slots
const createSlots = () => (timeStops.value = getTimeStops('00:00', '23:59'))

const editingEvent = computed(() => {
  return {
    eventStartTime: props.item.eventStartTime,
    eventNotes: props.item.eventNotes
  }
})
const date = ref(editingEvent.value.eventStartTime)
const time = ref(
  `${moment(editingEvent.value.eventStartTime).format('HH:mm')} - ${moment(
    editingEvent.value.eventStartTime
  )
    .add(props.item.eventDuration, 'minutes')
    .format('HH:mm')}`
)
const newTime = ref('')
const note = ref(editingEvent.value.eventNotes)

const dateTime = computed(() =>
  moment(
    `${moment(date.value).format('YYYY-MM-DD')} ${
      time.value ? moment(time.value).format('HH:mm') : newTime.value
    }`
  ).toISOString()
)

const cancelStatus = ref(false)
const cancelPopup = (status) => {
  if ((cancelStatus.value = status)) {
    cancelStatus.value = false
    emit('cancel')
    date.value = editingEvent.value.eventStartTime
    time.value = `${moment(editingEvent.value.eventStartTime).format(
      'HH:mm'
    )} - ${moment(editingEvent.value.eventStartTime)
      .add(props.item.eventDuration, 'minutes')
      .format('HH:mm')}`
    newTime.value = ''
    note.value = editingEvent.value.eventNotes
  }
}
const saveStatus = ref(false)
const cancelSave = (status) => {
  saveStatus.value = status
}
const save = (status) => {
  if (status) {
    myEvents.editEvent(
      {
        eventStartTime: dateTime.value,
        eventNotes: note.value
      },
      props.item.id
    )
    emit('closeModal')
    emit('save')
  }
}
const checkNull = () => {
  if (date.value && newTime.value) {
    saveStatus.value = true
  } else alert('Please selected date that you want to change.')
}
</script>

<template>
  <div>
    <div class="grid gap-y-5 m-5">
      <div
        :class="[
          color,
          'text-white text-2xl p-3 pl-6 font-semibold rounded-2xl'
        ]"
      >
        {{ item.bookingName }}
      </div>
      <div class="grid grid-rows-5 gap-y-5">
        <div class="row-span-2 grid grid-cols-2 divide-x">
          <div class="grid grid-rows-2 gap-y-3 mr-5">
            <div class="flex items-center">
              <span class="p-2 font-semibold text-[#5E6366] w-28">Clinic</span>
              <div
                class="grid content-center bg-[#F1F3F4] rounded-2xl px-4 w-full h-10"
              >
                {{ item.categoryName }}
              </div>
            </div>
            <div class="row-start-2 flex items-center">
              <span class="p-2 font-semibold text-[#5E6366] w-28">Email</span>
              <div
                class="grid content-center bg-[#F1F3F4] rounded-2xl w-full h-10 px-4"
              >
                <span class="overflow-x-auto"> {{ item.bookingEmail }}</span>
              </div>
            </div>
          </div>
          <div class="grid grid-rows-2 gap-y-3 pl-5">
            <div class="flex items-center">
              <span class="p-2 font-semibold text-[#5E6366] w-28">Date</span>
              <div
                v-if="!isEditMode"
                class="grid content-center bg-[#F1F3F4] rounded-2xl px-4 w-full h-10"
              >
                {{ moment(item.eventStartTime).format('DD MMMM YYYY') }}
              </div>
              <Datepicker
                v-else
                v-model="date"
                :enableTimePicker="false"
                :format="format"
                :minDate="new Date()"
                placeholder="Select Date"
                hideInputIcon
                vertical
                class="grid content-center w-full h-full"
                @closed="getEventByDate(), createSlots()"
              />
            </div>
            <div class="grid grid-cols-5">
              <div class="col-span-3 flex items-center">
                <span class="p-2 font-semibold text-[#5E6366] w-28">Time</span>
                <div
                  v-if="!isEditMode"
                  class="grid content-center bg-[#F1F3F4] rounded-2xl px-4 w-5/6 h-10"
                >
                  {{ moment(editingEvent.eventStartTime).format('HH:mm') }} -
                  {{
                    moment(editingEvent.eventStartTime)
                      .add(item.eventDuration, 'minutes')
                      .format('HH:mm')
                  }}
                </div>
                <!-- <Datepicker
                  v-else
                  v-model="time"
                  timePicker
                  is24
                  :minDate="new Date()"
                  :minTime="{
                    hours: moment(editingEvent.eventStartTime).isAfter(
                      moment(new Date())
                    )
                      ? null
                      : new Date().getHours(),
                    minutes: moment(editingEvent.eventStartTime).isAfter(
                      moment(new Date())
                    )
                      ? null
                      : new Date().getMinutes()
                  }"
                  placeholder="Select Time"
                  :disabled="date == '' ? true : false"
                  hideInputIcon
                  @closed="checkOverlap"
                  class="grid content-center w-5/6 h-full"
                /> -->

                <div v-else class="relative w-5/6">
                  <!-- <Datepicker
                    v-if="time"
                    v-model="time"
                    timePicker
                    is24
                    :minDate="new Date()"
                    :minTime="{
                      hours: moment(editingEvent.eventStartTime).isAfter(
                        moment(new Date())
                      )
                        ? null
                        : new Date().getHours(),
                      minutes: moment(editingEvent.eventStartTime).isAfter(
                        moment(new Date())
                      )
                        ? null
                        : new Date().getMinutes()
                    }"
                    placeholder="Select Time"
                    :disabled="date == '' ? true : false"
                    hideInputIcon
                    class="grid content-center"
                    @cleared="getEventByDate(), createSlots()"
                  /> -->
                  <select
                    v-model="newTime"
                    :class="[
                      'rounded-xl h-10 px-2 border-2 border-gray-400',
                      newTime ? 'w-11/12' : 'w-full'
                    ]"
                    @click="getEventByDate(), createSlots()"
                    @change="time = ''"
                  >
                    <option value="" disabled hidden>{{ time }}</option>
                    <option
                      :disabled="
                        checkOverlap(slot) ||
                        (slot < moment(myClock.clock).format('HH:mm') &&
                          moment(date).format('YYYY-MM-DD') ==
                            moment(myClock.clock).format('YYYY-MM-DD'))
                      "
                      v-for="slot in timeStops"
                      :value="slot"
                    >
                      {{ slot }} -
                      {{
                        moment(slot, 'HH:mm')
                          .add(props.item.eventDuration, 'minutes')
                          .format('HH:mm')
                      }}
                    </option>
                  </select>
                  <button
                    type="button"
                    v-if="newTime"
                    @click="newTime = ''"
                    class="absolute -right-3 top-2"
                  >
                    <svg width="1.5em" height="1.5em" viewBox="0 0 24 24">
                      <path
                        fill="#919699"
                        d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z"
                      ></path>
                    </svg>
                  </button>
                </div>
              </div>
              <div class="col-span-2 flex items-center">
                <span class="font-semibold text-[#5E6366] ml-5 mr-2">
                  <svg
                    class=""
                    width="1.5em"
                    height="1.5em"
                    viewBox="0 0 24 24"
                  >
                    <path
                      fill="#5E6366"
                      d="M12 20c4.4 0 8-3.6 8-8s-3.6-8-8-8s-8 3.6-8 8s3.6 8 8 8m0-18c5.5 0 10 4.5 10 10s-4.5 10-10 10S2 17.5 2 12S6.5 2 12 2m5 11.9l-.7 1.3l-5.3-2.9V7h1.5v4.4l4.5 2.5Z"
                    ></path></svg
                ></span>
                <div
                  class="grid content-center bg-[#F1F3F4] rounded-2xl px-4 w-full h-10"
                >
                  {{ item.eventDuration }} min
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row-span-3 relative">
          <div class="p-2">
            <span class="text-[#5E6366] font-semibold">Description | Note</span>
          </div>
          <div
            v-if="!isEditMode"
            style="width: 70vw"
            class="break-words rounded-xl h-40 bg-[#F1F3F4] overflow-auto p-3 border border-gray-400"
          >
            <span v-if="item.eventNotes"> {{ item.eventNotes }}</span>
            <span v-if="!item.eventNotes" class="text-gray-400">
              No description</span
            >
          </div>
          <div v-if="isEditMode">
            <textarea
              style="width: 70vw"
              class="break-words resize-none rounded-xl h-40 p-3 bg-white border border-gray-400"
              placeholder="Enter your description . . ."
              v-model="note"
            />
            <div
              v-if="note.length >= 500"
              class="absolute text-xs text-[#F3A72E]"
            >
              The number of characters has a limit of 500 characters.
            </div>
            <div class="absolute right-0 top-4 text-xs text-gray-500">
              {{ note.length }}/500
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-if="isEditMode" class="flex justify-center gap-x-10">
      <button
        class="bg-[#EA3D2F] rounded-[5px] text-white font-semibold w-36 h-12 px-1"
      >
        <div @click="cancelStatus = true" class="flex place-items-center">
          <span class="p-1 bg-white rounded-[5px]">
            <svg width="2em" height="2em" viewBox="0 0 24 24">
              <path
                fill="#EA3D2F"
                d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z"
              ></path></svg></span
          ><span class="px-4 text-lg">CANCEL</span>
        </div>
      </button>
      <button
        class="'flex place-items-center rounded-[5px] bg-[#2FA84F] text-white font-semibold w-36 h-12 px-1"
      >
        <div @click="checkNull" class="flex place-items-center">
          <span class="p-1 bg-white rounded-[5px]">
            <svg width="2em" height="2em" viewBox="0 0 24 24">
              <path
                fill="#2FA84F"
                d="M9 16.17L5.53 12.7a.996.996 0 1 0-1.41 1.41l4.18 4.18c.39.39 1.02.39 1.41 0L20.29 7.71a.996.996 0 1 0-1.41-1.41L9 16.17z"
              ></path></svg></span
          ><span class="px-7 text-lg">SAVE</span>
        </div>
      </button>
    </div>
    <Confirm
      v-if="cancelStatus"
      underline="cancel"
      color="EA3D2F"
      desc="If you <confirm>, your last edit will be discarded."
      @cancel="cancelPopup"
      @confirm="cancelPopup"
    />
    <Confirm
      v-if="saveStatus"
      underline="save"
      color="2FA84F"
      desc="If you <confirm>, this schedule will be saved."
      @cancel="cancelSave"
      @confirm="save"
    />
  </div>
</template>

<style></style>
