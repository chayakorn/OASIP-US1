<script setup>
import moment from 'moment'
import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import { computed, ref } from 'vue'
import { useEvents } from '../stores/events.js'
import Confirm from './Confirm.vue'
const emit = defineEmits(['cancel', 'save','closeModal'])
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
const myEvents = useEvents()
const getCategoryById = async (id) => {
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/eventcat/${id}`)
  if (res.status === 200) {
    return await res.json()
  } else console.log('error, cannot get category')
}
const category = ref(getCategoryById(props.item.eventCategoryId))

const isEditMode = computed(() => props.isEditMode)

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
  ).add(category.value.eventDuration, 'minutes')
  if (
    !myEvents.eventLists
      .filter((item) => item.eventCategoryId == category.id)
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
    editingEvent.value.eventStartTime = dateTime.value
  else {
    date.value = ''
    time.value = ''
    alert('your selected time has been booked, please selete a new time!')
  }
}
const editingEvent = computed(() => ({
  eventStartTime: props.item.eventStartTime,
  eventNotes: props.item.eventNotes
}))
const date = ref(editingEvent.value.eventStartTime)
const time = ref({
  hours: moment(editingEvent.value.eventStartTime).format('HH'),
  minutes: moment(editingEvent.value.eventStartTime).format('mm'),
  seconds: 0
})
const note = ref(editingEvent.value.eventNotes)

const dateTime = computed(() =>
  moment(
    `${moment(date.value).format('YYYY-MM-DD')} ${moment(time.value).format(
      'HH:mm'
    )}`
  ).toISOString()
)
const cancelStatus = ref(false)
const cancelPopup = (status) => {
  cancelStatus.value = status
  if (status) {
    cancelStatus.value = false
    emit('cancel')
    props.item.eventStartTime = props.item.eventStartTime
      ? props.item.eventStartTime
      : ''
    props.item.eventNotes = props.item.eventNotes ? props.item.eventNotes : ''
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
  if (date.value && time.value) {
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
      <div class="grid grid-rows-2 gap-y-5">
        <div class="grid grid-cols-2 divide-x">
          <div class="grid grid-rows-3 gap-y-3 mr-5">
            <div class="flex items-center">
              <span class="p-2 font-semibold text-[#5E6366] w-28">Clinic</span>
              <div
                class="grid content-center bg-[#F1F3F4] rounded-2xl px-4 w-full h-full"
              >
                {{ item.categoryName }}
              </div>
            </div>
            <div class="flex items-center">
              <span class="p-2 font-semibold text-[#5E6366] w-28">Date</span>
              <div
                v-if="!isEditMode"
                class="grid content-center bg-[#F1F3F4] rounded-2xl px-4 w-full h-full"
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
              />
            </div>
            <div class="grid grid-cols-5">
              <div class="col-span-3 flex items-center">
                <span class="p-2 font-semibold text-[#5E6366] w-28">Time</span>
                <div
                  v-if="!isEditMode"
                  class="grid content-center bg-[#F1F3F4] rounded-2xl px-4 w-5/6 h-full"
                >
                  {{ moment(editingEvent.eventStartTime).format('HH:mm') }} -
                  {{
                    moment(editingEvent.eventStartTime)
                      .add(item.eventDuration, 'minutes')
                      .format('HH:mm')
                  }}
                </div>
                <Datepicker
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
                />
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
                  class="grid content-center bg-[#F1F3F4] rounded-2xl px-4 w-full h-full"
                >
                  {{ item.eventDuration }} min
                </div>
              </div>
            </div>
          </div>
          <div class="grid grid-rows-3 gap-y-3 pl-5">
            <div class="flex items-center">
              <span class="p-2 font-semibold text-[#5E6366] w-28">Name</span>
              <div
                class="grid content-center bg-[#F1F3F4] rounded-2xl px-4 w-full h-full"
              >
                {{ item.name ? item.name : '-' }}
              </div>
            </div>
            <div class="row-start-2 flex items-center">
              <span class="p-2 font-semibold text-[#5E6366] w-28">Email</span>
              <div
                class="grid content-center bg-[#F1F3F4] rounded-2xl w-full h-full px-4"
              >
                <span class="overflow-x-auto"> {{ item.bookingEmail }}</span>
              </div>
            </div>
          </div>
        </div>
        <div class="relative">
          <div class="p-2">
            <span class="text-[#5E6366] font-semibold">Description | Note</span>
          </div>
          <div
            v-if="!isEditMode"
            class="resize-none rounded-2xl w-full h-28 bg-[#F1F3F4] p-3 overflow-auto"
          >
            <span v-if="item.eventNotes"> {{ item.eventNotes }}</span>
            <span v-if="!item.eventNotes" class="text-gray-400">
              No description</span
            >
          </div>
          <div v-if="isEditMode">
            <textarea
              maxlength="500"
              class="break-words resize-none rounded-xl w-full h-28 p-3 bg-white border border-[#5E6366] focus:outline-none focus:shadow-outline"
              placeholder="Enter your description . . ."
              v-model="note"
            />
            <div
              v-if="item.eventNotes.length == 500"
              class="absolute text-xs text-[#F3A72E]"
            >
              The number of characters has a limit of 500 characters. If it
              exceeds 500, it will not be able to continue typing.
            </div>
            <div class="absolute right-0 top-4 text-xs text-gray-500">
              {{ item.eventNotes.length }}/500
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
      desc="If you <confirm>, your last edit will be discarded."
      @cancel="cancelPopup"
      @confirm="cancelPopup"
    />
    <Confirm
      v-if="saveStatus"
      desc="If you <confirm>, this schedule will be saved."
      @cancel="cancelSave"
      @confirm="save"
    />
  </div>
</template>

<style></style>
