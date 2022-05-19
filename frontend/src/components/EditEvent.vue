<script setup>
import { computed, ref } from 'vue'
import moment from 'moment'
import Datepicker from '@vuepic/vue-datepicker'
import '@vuepic/vue-datepicker/dist/main.css'
import { useEvents } from '../stores/events.js'

const emit = defineEmits(['existChange', 'sendEditData'])
const props = defineProps({
    item: {
        type: Object,
        require: true
    },
    color: {
        type: String,
        require: true
    }
})

//use state "Events"
const myEvents = useEvents()
const eventLists = computed(() => myEvents.eventLists)

const format = (date) => {
    const day = date.getDate()
    const month = moment(date).format('MMMM')
    const year = date.getFullYear()

    return `${day} ${month} ${year}`
}

const checkOverlap = () => {
  // GET
  myEvents.getAllEvents()
  let selectedStartTime = moment(new Date(dateTime.value), 'DD-MM-YYYY HH:mm')
  let selectedEndTime = moment(
    new Date(dateTime.value),
    'DD-MM-YYYY HH:mm'
  ).add(props.item.eventDuration, 'minutes')
  if (
    !eventLists.value
      .filter((item) => item.eventCategoryId == props.item.eventCategoryId)
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
          //   ||
          //   selectedEndTime.isBetween(
          //     moment(new Date(e.eventStartTime), 'DD-MM-YYYY HH:mm').add(
          //       -1,
          //       'minutes'
          //     ),
          //     moment(new Date(e.eventEndTime), 'DD-MM-YYYY HH:mm').add(
          //       1,
          //       'minutes'
          //     )
          //   )
        )
          return true
        else return false
      })
  )
    true
  else {
    alert('your selected time has been booked, please choose a new time!')
    time.value = props.item.eventStartTime
  }
}

const showCancel = ref(false)
const description = ref(props.item.eventNotes)
const date = ref(props.item.eventStartTime)
const time = ref({
    hours: moment(props.item.eventStartTime).format('HH'),
    minutes: moment(props.item.eventStartTime).format('mm'),
    seconds: 0
})
const dateTime = computed(
    () =>
        `${moment(date.value).format('YYYY-MM-DD')} ${moment(time.value).format(
            'HH:mm'
        )}`
)

const showCancelFn = () => {
    showCancel.value = !showCancel.value
}

const editingEvent = computed(() => ({
    bookingName: props.item.bookingName,
    bookingEmail: props.item.bookingEmail,
    eventStartTime: moment(new Date(dateTime.value)).toISOString(),
    eventEndTime: moment(dateTime.value)
        .add(props.item.eventDuration, 'minutes')
        .toISOString(),
    eventCategoryId: { id: props.item.eventCategoryId },
    eventDuration: props.item.eventDuration,
    eventNotes: description.value,
    name: props.item.eventName,
    categoryName: props.item.categoryName
}))

const clearData = () => {
  date.value = editingEvent.value.eventStartTime
  time.value = {
    hours: moment(props.item.eventStartTime).format('HH'),
    minutes: moment(props.item.eventStartTime).format('mm'),
    seconds: 0
  }
  description.value = editingEvent.value.eventNotes
  showCancel.value = false
  emit('existChange')
}

const checkNull = (event, id) => {
    if (date.value != null && time.value != null) {
        emit('sendEditData', editingEvent.value, id)
        myEvents.editEvent(editingEvent.value, id)
        clearData(false)
    } else alert('Please selected date that you want to change.')
}

const prevent = (event, id) => {
    checkNull(editingEvent.value, id)
}
</script>

<template>
    <div>
        <div class="grid grid-cols-2 divide-x-2">
            <!-- col1 -->
            <div>
                <!-- CLINIC -->
                <div class="flex py-1 pl-4 pt-0.5">
                    <span class="p-2 font-semibold text-[#5E6366]">Clinic</span>
                    <div class="flex-none bg-[#F1F3F4] rounded-2xl w-4/5 py-2 px-4 text-[#C6CACC]">
                        {{ item.categoryName }}
                    </div>
                </div>

                <!-- DATE -->
                <div class="flex pl-4 py-1 gap-x-2">
                    <span class="grid place-items-center py-2 font-semibold px-2 pr-2 text-[#5E6366]">Date</span>

                    <div class="flex items-center gap-x-2">
                        <Datepicker v-model="date" :enableTimePicker="false" :format="format" :minDate="new Date()"
                            placeholder="Select Date" hideInputIcon vertical />
                    </div>
                    <!-- DURATION -->
                    <div class="flex pl-4">
                        <span class="pt-2 px-2">
                            <svg class="" width="1.5em" height="1.5em" viewBox="0 0 24 24">
                                <path fill="#5E6366"
                                    d="M12 20c4.4 0 8-3.6 8-8s-3.6-8-8-8s-8 3.6-8 8s3.6 8 8 8m0-18c5.5 0 10 4.5 10 10s-4.5 10-10 10S2 17.5 2 12S6.5 2 12 2m5 11.9l-.7 1.3l-5.3-2.9V7h1.5v4.4l4.5 2.5Z">
                                </path>
                            </svg>
                        </span>
                        <div class="flex-none bg-[#F1F3F4] rounded-2xl w-36 py-2 px-4 text-[#C6CACC]">
                            {{ item.eventDuration }} mins
                        </div>
                    </div>
                    <!-- <input type="date"
                                class="flex-none border-[0.5px] border-[#5E6366] bg-[#F1F3F4] rounded-2xl pr-[215px] py-2 px-4  text-black-700 focus:outline-none focus:bg-white focus:border-[#5E6366]"/> -->

                    <!-- <div class="flex items-center justify-center">
                        <div class="datepicker relative form-floating mb-3 xl:w-96">
                            <input type="text"
                                class="form-control block w-full px-3 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
                                placeholder="Select a date" />
                            <label for="floatingInput" class="text-gray-700">Select a date</label>
                        </div>
                    </div> -->
          <br />
        </div>
        <!-- TIME -->
        <!-- <div class="grid grid-cols-2 divide-x-2"> -->
        <div class="flex pl-4 py-1">
          <div class="flex items-center gap-x-2">
            <span class="font-semibold py-2 px-2 pr-2 text-[#5E6366]"
              >Time</span
            >
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
              @closed="checkOverlap"
              placeholder="Select Time"
              :disabled="date == '' ? true : false"
              hideInputIcon
            />
          </div>
          <!-- <span class="font-semibold py-2 px-2 pr-3.5 text-[#5E6366]">Time</span> -->
          <div
            v-show="time"
            class="flex-none rounded-2xl w-60 py-2 px-4 text-black-700"
          >
            {{
              `${moment(time).format('HH:mm')} - ${moment(time)
                .add(item.eventDuration, 'minutes')
                .format('HH:mm')}`
            }}
          </div>
          <!-- <input type="time"
                            class="flex-none border-[0.5px] border-[#5E6366] bg-[#F1F3F4] rounded-2xl w-60 py-2 px-4 text-black-700 focus:outline-none focus:bg-white focus:border-[#5E6366]"
                            v-model="time"> -->
                </div>
                <!-- </div> -->
            </div>
            <!-- col2 -->
            <div>
                <!-- NAME -->
                <div class="flex py-1 pl-9">
                    <span class="text-[#5E6366] p-2 font-semibold">Name</span>
                    <div class="flex-none bg-[#F1F3F4] rounded-2xl w-10/12 py-1.5 px-4 text-[#C6CACC]">
                        {{ item.name }}
                    </div>
                </div>
                <!-- EMAIL -->
                <div class="flex py-1 pl-9">
                    <span class="text-[#5E6366] p-2 pr-3 font-semibold">Email</span>
                    <div class="flex-none bg-[#F1F3F4] appearance-none rounded-2xl w-10/12 py-2 px-4 text-[#C6CACC]">
                        {{ item.bookingEmail }}
                    </div>
                </div>
            </div>
        </div>

        <div class="pt-5 border-b-2 mx-5 text-[#5E6366]"></div>

        <!-- DESCRIPTION -->
        <div class="px-5 pt-1">
            <div class="p-2">
                <span class="text-[#5E6366] font-semibold"> Description </span>
            </div>
            <textarea maxlength="500"
                class="break-words resize-none border-[0.5px] border-[#5E6366] rounded-2xl bg-[#F1F3F4] w-full pt-2 h-32 px-5 text-black focus:outline-none focus:bg-white focus:border-[#5E6366]"
                v-model="description">
      </textarea>
            <div v-if="description.length == 500" class="absolute text-xs text-[#F3A72E]">
                The number of characters has a limit of 500 characters. If it
                exceeds 500, it will not be able to continue typing.
            </div>
        </div>

        <!-- BUTTON -->
        <div class="grid grid-cols-2 pt-4">
            <!-- DELETE -->
            <div class="justify-self-end px-4" @click="showCancelFn()">
                <button
                    class="flex place-items-center bg-[#EA3D2F] rounded-[5px] text-white font-semibold w-36 h-12 px-1">
                    <span class="p-1 bg-white rounded-[5px]">
                        <svg width="2em" height="2em" viewBox="0 0 24 24">
                            <path fill="#EA3D2F"
                                d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z">
                            </path>
                        </svg> </span><span class="px-3 text-lg">CANCEL</span>
                </button>
            </div>
            <!-- EDIT -->
            <div class="px-4" @click="prevent(event, item.id)">
                <button
                    class="flex place-items-center rounded-[5px] bg-[#2FA84F] text-white font-semibold w-36 h-12 px-1">
                    <span class="p-1 bg-white rounded-[5px]">
                        <svg width="2em" height="2em" viewBox="0 0 24 24">
                            <path fill="#2FA84F"
                                d="M9 16.17L5.53 12.7a.996.996 0 1 0-1.41 1.41l4.18 4.18c.39.39 1.02.39 1.41 0L20.29 7.71a.996.996 0 1 0-1.41-1.41L9 16.17z">
                            </path>
                        </svg> </span><span class="px-6 text-lg">SAVE</span>
                </button>
            </div>
        </div>

        <div v-show="showCancel">
            <div
                class="bg-black/25 overflow-x-hidden overflow-y-auto absolute inset-0 z-40 outline-none focus:outline-none justify-center items-center flex">
                <div class="modal-container bg-white w-[30%] px-3 pt-1 pb-20 h-[28%] rounded-2xl z-50">
                    <div class="grid grid-cols-1 divide-y-[1px] px-1">
                        <div class="grid place-items-center font-bold text-xl pt-2 pb-2">
                            Are you sure?
                        </div>
                        <div class="grid place-items-center font-bold text-lg pt-7 pb-2">
                            Do you want to exists?
                        </div>
                    </div>
                    <div class="grid grid-cols-2 divided-x-0 pt-6">
                        <div class="justify-self-end px-3">
                            <button
                                class="bg-[#FEE4E2] font-semibold text-lg text-[#EA3D2F] rounded-[20px] py-3 px-[65px]"
                                @click="showCancelFn()">
                                CANCEL
                            </button>
                        </div>
                        <div class="px-2">
                            <button class="bg-[#DCF7E3] font-semibold text-lg text-[#2FA84F] rounded-[20px] py-3 px-16"
                                @click="clearData(true)">
                                CONFIRM
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style>
</style>
