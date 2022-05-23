<script setup>
import { computed, ref } from 'vue'
import moment from 'moment'
import { useEvents } from '../stores/events.js'
import Detail from './Detail.vue'
import Confirm from './Confirm.vue'
import { useClock } from '../stores/clock.js'
const myClock = useClock()
const emit = defineEmits(['closeShowMore', 'deleteSuccess', 'saveSuccess'])
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

const myEvents = useEvents()

const isEditMode = ref(false)
const changeMode = () => (
  (isEditMode.value = !isEditMode.value), (cancelStatus.value = false)
)
const checkEditMode = () => {
  isEditMode.value ? (cancelStatus.value = true) : emit('closeShowMore', false)
}
const cancelStatus = ref(false)
const cancelPopup = (status) => {
  if ((cancelStatus.value = status)) {
    changeMode()
  }
}
const deleteStatus = ref(false)
const deletePopup = (status) => {
  deleteStatus.value = status
  if (status) {
    myEvents.deleteEvent(props.item.id)
    emit('closeShowMore', false)
    emit('deleteSuccess')
  }
}

const backDrop = (event) => {
  if (event.target.id == 'modal') {
    checkEditMode()
  }
}

const checkPast = () => moment(props.item.eventStartTime).isBefore(myClock.date)
</script>

<template>
  <div
    class="bg-black/25 overflow-x-hidden overflow-y-auto absolute inset-0 z-40 outline-none focus:outline-none justify-center items-center flex"
    @click="backDrop"
    id="modal"
  >
    <div class="bg-white w-3/4 h-max px-5 pt-4 pb-8 max-h-fit rounded-3xl z-50">
      <div class="relative mb-2">
        <div v-if="!isEditMode" class="font-bold text-2xl text-center">
          Scheduled details
        </div>
        <div v-if="isEditMode" class="font-bold text-2xl text-center">
          Editing details
        </div>
        <button
          @click="checkEditMode"
          class="absolute right-0 top-0 float-right hover:bg-[#F1F3F4] transition duration-100 rounded-lg"
        >
          <svg width="2em" height="2em" viewBox="0 0 24 24">
            <path
              fill="currentColor"
              d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z"
            ></path>
          </svg>
        </button>
      </div>

      <!-- Body -->
      <Detail
        :item="item"
        :color="color"
        :isEditMode="isEditMode"
        :cancelModal="cancelStatus"
        @cancel="changeMode"
        @save="$emit('saveSuccess')"
        @closeModal="emit('closeShowMore', false)"
      />
      <!-- Footer -->
      <div v-if="!isEditMode" class="flex justify-center gap-x-10">
        <button
          class="bg-[#EA3D2F] rounded-[5px] text-white font-semibold w-36 h-12 px-1"
        >
          <div @click="deleteStatus = true" class="flex place-items-center">
            <span class="p-1 bg-white rounded-[5px]">
              <svg width="2em" height="2em" viewBox="0 0 24 24">
                <path
                  fill="#EA3D2F"
                  d="M12 1.75a3.25 3.25 0 0 1 3.245 3.066L15.25 5h5.25a.75.75 0 0 1 .102 1.493L20.5 6.5h-.796l-1.28 13.02a2.75 2.75 0 0 1-2.561 2.474l-.176.006H8.313a2.75 2.75 0 0 1-2.714-2.307l-.023-.174L4.295 6.5H3.5a.75.75 0 0 1-.743-.648L2.75 5.75a.75.75 0 0 1 .648-.743L3.5 5h5.25A3.25 3.25 0 0 1 12 1.75Zm6.197 4.75H5.802l1.267 12.872a1.25 1.25 0 0 0 1.117 1.122l.127.006h7.374c.6 0 1.109-.425 1.225-1.002l.02-.126L18.196 6.5ZM13.75 9.25a.75.75 0 0 1 .743.648L14.5 10v7a.75.75 0 0 1-1.493.102L13 17v-7a.75.75 0 0 1 .75-.75Zm-3.5 0a.75.75 0 0 1 .743.648L11 10v7a.75.75 0 0 1-1.493.102L9.5 17v-7a.75.75 0 0 1 .75-.75Zm1.75-6a1.75 1.75 0 0 0-1.744 1.606L10.25 5h3.5A1.75 1.75 0 0 0 12 3.25Z"
                ></path>
              </svg> </span
            ><span class="px-4 text-lg">DELETE</span>
          </div>
        </button>
        <button
          :disabled="checkPast()"
          :class="[
            isEditMode ? 'bg-[#2FA84F]' : 'bg-[#367BF5]',
            'flex place-items-center rounded-[5px] text-white font-semibold w-36 h-12 px-1 disabled:opacity-25 disabled:cursor-not-allowed'
          ]"
        >
          <div
            @click="checkPast() ? '' : changeMode()"
            class="flex place-items-center"
          >
            <span class="p-1 bg-white rounded-[5px]">
              <svg width="2em" height="2em" viewBox="0 0 256 256">
                <path
                  fill="#367BF5"
                  d="M218.3 93.7L192 120l-56-56l26.3-26.3a8 8 0 0 1 11.4 0l44.6 44.6a8 8 0 0 1 0 11.4Z"
                  opacity=".0"
                ></path>
                <path
                  fill="#367BF5"
                  d="M216 208H115.3L224 99.3a15.9 15.9 0 0 0 0-22.6L179.3 32a16.1 16.1 0 0 0-22.6 0l-120 120a15.9 15.9 0 0 0-4.7 11.3V208a16 16 0 0 0 16 16h168a8 8 0 0 0 0-16ZM168 43.3L212.7 88L192 108.7L147.3 64Zm-120 120l88-88l44.7 44.7l-88 88H48Z"
                ></path></svg></span
            ><span class="px-7 text-lg">EDIT</span>
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
        v-if="deleteStatus"
        underline="delete"
        color="EA3D2F"
        desc="If you <confirm>, this schedule will be deleted."
        @cancel="deletePopup"
        @confirm="deletePopup"
      />
    </div>
  </div>
</template>

<style scoped></style>
