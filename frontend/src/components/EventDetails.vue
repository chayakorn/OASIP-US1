<script setup>
import moment from 'moment'
import { ref } from 'vue';
import EditEvent from './EditEvent.vue';
const emit = defineEmits(['closeModal'])
// import { useRoute, useRouter } from 'vue-router'
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

const edit = ref(true)
// const myRouter = useRouter()
// const { params } = useRoute()
// const item = JSON.parse(params.item)
const date = moment.utc(props.item.eventStartTime).format('DD MMM YYYY')
let startTime = moment.utc(props.item.eventStartTime).format('h:mm A')
// const closeModal = () => {}

const endTime = moment(startTime, 'h:mm A').add(props.item.eventDuration, 'minutes').format('h:mm A')

const log = (event) => {
  if (event.target.id == 'modal') {
    emit('closeModal', false)
  }
}
</script>

<template>

  <div
    class="bg-black/25 overflow-x-hidden overflow-y-auto absolute inset-0 z-40 outline-none focus:outline-none justify-center items-center flex"
    @click="log" id="modal">
    <div class="modal-container bg-white w-9/12 px-5 pt-4 pb-20 h-[76%] rounded-3xl z-50">
      <div class="modal-header">
        <div class="grid grid-cols-3">
          <div></div>
          <div class="font-bold text-2xl text-center">Scheduled details</div>
          <div>
            <div class="float-right">
              <button class="hover:bg-[#F1F3F4] transition duration-100 rounded-lg grid"
                @click="emit('closeModal', false)">
                <svg width="2.5em" height="2.5em" viewBox="0 0 24 24" class="">
                  <path fill="currentColor"
                    d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z">
                  </path>
                </svg>
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Body -->
      <div class="modal-body pt-2">
        <div :class="[
          color,
          'text-white text-2xl p-3 pl-6 font-semibold rounded-2xl'
        ]">
          {{ item.bookingName }}
        </div>
        <br />

        <div v-show="edit">
          <div  class="grid grid-cols-2 divide-x-2">
            <!-- col1 -->
            <div>
              <!-- CLINIC -->
              <div class="flex py-1 pl-4 pt-0.5">
                <span class="p-2 font-semibold text-[#5E6366]">Clinic</span>
                <div class="flex-none bg-[#F1F3F4] rounded-2xl w-4/5 py-2 px-4 text-black-700">
                  {{ item.categoryName }} <br />
                </div>
              </div>

              <!-- DATE -->
              <div class="flex pl-4 py-1">
                <span class="py-2 font-semibold px-2 pr-3.5 text-[#5E6366]">Date</span>
                <div class="flex-none bg-[#F1F3F4] rounded-2xl w-4/5 py-2 px-4 text-black-700">
                  {{ date }} <br />
                </div>
              </div>

              <!-- TIME -->
              <div class="grid grid-cols-2 divide-x-2">
                <div class="flex pl-4 py-1">
                  <span class="font-semibold py-2 px-2 pr-3.5 text-[#5E6366]">Time</span>
                  <div class="flex-none bg-[#F1F3F4] rounded-2xl w-60 py-2 px-4 text-black-700">
                    {{ startTime }} - {{ endTime }}
                  </div>
                  <!-- DURATION -->
                  <div class="flex pl-4 float-right">
                    <span class="pt-2 px-2">
                      <svg class="" width="1.5em" height="1.5em" viewBox="0 0 24 24">
                        <path fill="#5E6366"
                          d="M12 20c4.4 0 8-3.6 8-8s-3.6-8-8-8s-8 3.6-8 8s3.6 8 8 8m0-18c5.5 0 10 4.5 10 10s-4.5 10-10 10S2 17.5 2 12S6.5 2 12 2m5 11.9l-.7 1.3l-5.3-2.9V7h1.5v4.4l4.5 2.5Z">
                        </path>
                      </svg>
                    </span>
                    <div class="flex-none bg-[#F1F3F4] rounded-2xl w-36 py-2 px-4 text-black-700">
                      {{ item.eventDuration }} mins
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- col2 -->
            <div>
              <!-- NAME -->
              <div class="flex py-1 pl-9">
                <span class="text-[#5E6366] p-2 font-semibold">Name</span>
                <div class="flex-none bg-[#F1F3F4] rounded-2xl w-10/12 py-1.5 px-4 text-black-700">
                  {{ item.name }}
                </div>
              </div>
              <!-- EMAIL -->
              <div class="flex py-1 pl-9">
                <span class="text-[#5E6366] p-2 pr-3 font-semibold">Email</span>
                <div class="flex-none bg-[#F1F3F4] appearance-none rounded-2xl w-10/12 py-2 px-4 text-black-700">
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
            <div class="resize-none rounded-2xl bg-[#F1F3F4] w-full h-32 px-5 pt-4 pb-5 ">
              <span v-show="item.eventNotes.length"> {{ item.eventNotes }}</span>
              <span v-if="!item.eventNotes.length" class="text-gray-400"> No description</span>
            </div>
          </div>
          <!-- BUTTON -->
          <div class="grid grid-cols-2 pt-4">
            <!-- DELETE -->
            <div class=" justify-self-end px-4 " @click="">
              <button
                class="flex place-items-center  bg-[#EA3D2F] rounded-[5px] text-white font-semibold w-36 h-12 px-1">
                <span class="p-1 bg-white rounded-[5px]">
                  <svg width="2em" height="2em" viewBox="0 0 24 24">
                    <path fill="#EA3D2F"
                      d="M12 1.75a3.25 3.25 0 0 1 3.245 3.066L15.25 5h5.25a.75.75 0 0 1 .102 1.493L20.5 6.5h-.796l-1.28 13.02a2.75 2.75 0 0 1-2.561 2.474l-.176.006H8.313a2.75 2.75 0 0 1-2.714-2.307l-.023-.174L4.295 6.5H3.5a.75.75 0 0 1-.743-.648L2.75 5.75a.75.75 0 0 1 .648-.743L3.5 5h5.25A3.25 3.25 0 0 1 12 1.75Zm6.197 4.75H5.802l1.267 12.872a1.25 1.25 0 0 0 1.117 1.122l.127.006h7.374c.6 0 1.109-.425 1.225-1.002l.02-.126L18.196 6.5ZM13.75 9.25a.75.75 0 0 1 .743.648L14.5 10v7a.75.75 0 0 1-1.493.102L13 17v-7a.75.75 0 0 1 .75-.75Zm-3.5 0a.75.75 0 0 1 .743.648L11 10v7a.75.75 0 0 1-1.493.102L9.5 17v-7a.75.75 0 0 1 .75-.75Zm1.75-6a1.75 1.75 0 0 0-1.744 1.606L10.25 5h3.5A1.75 1.75 0 0 0 12 3.25Z">
                    </path>
                  </svg>
                </span><span class="px-4 text-lg">DELETE</span>
              </button>
            </div>
            <!-- EDIT -->
            <div class="px-4" @click="edit = !edit">
              <button
                class="flex place-items-center rounded-[5px] bg-[#367BF5] text-white font-semibold w-36 h-12 px-1">
                <span class="p-1 bg-white rounded-[5px]">
                  <svg width="2em" height="2em" viewBox="0 0 256 256">
                    <path fill="#367BF5"
                      d="M218.3 93.7L192 120l-56-56l26.3-26.3a8 8 0 0 1 11.4 0l44.6 44.6a8 8 0 0 1 0 11.4Z"
                      opacity=".0">
                    </path>
                    <path fill="#367BF5"
                      d="M216 208H115.3L224 99.3a15.9 15.9 0 0 0 0-22.6L179.3 32a16.1 16.1 0 0 0-22.6 0l-120 120a15.9 15.9 0 0 0-4.7 11.3V208a16 16 0 0 0 16 16h168a8 8 0 0 0 0-16ZM168 43.3L212.7 88L192 108.7L147.3 64Zm-120 120l88-88l44.7 44.7l-88 88H48Z">
                    </path>
                  </svg>
                </span><span class="px-7 text-lg">EDIT</span>
              </button>
            </div>
          </div>
         
        </div>
         <EditEvent v-show="!edit" :item="item"/>
      </div>
      <!-- <div class="md:flex md:items-center mb-6">
            <div class="md:w-1/9">
              <label
                disabled
                class="flex-none text-gray-500 font-normal md:text-right mb-1 md:mb-0 pr-4"
              >
                Clinic
              </label>
            </div>

            <div class="w-2/5">
              <input
                disabled
                class="flex-none bg-gray-200 appearance-none border-2 border-gray-200 rounded-xl w-full py-2 px-4 text-black-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
                id="inline-full-name"
                value=""
              />
            </div>
          </div>

          <div class="md:flex md:items-center mb-6">
            <div class="md:w-1/9">
              <label
                disabled
                class="flex-none text-gray-500 font-normal md:text-right mb-1 md:mb-0 pr-5"
              >
                Date
              </label>
            </div>

            <div class="w-2/5">
              <input
                disabled
                class="flex-none bg-gray-200 appearance-none border-2 border-gray-200 rounded-xl w-full py-2 px-4 text-black-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
                id="inline-full-name"
                type="text"
                value="31 December 2022"
              />
            </div>
          </div>

          <div class="md:flex md:items-center mb-6">
            <div class="md:w-1/9">
              <label
                class="flex-none text-gray-500 font-normal md:text-right mb-1 md:mb-0 pr-5"
              >
                Time
              </label>
            </div>

            <div class="w-1/5">
              <input
                disabled
                class="flex-none bg-gray-200 appearance-none border-2 border-gray-200 rounded-xl w-full py-2 px-4 text-black-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500 read-only"
                type="text"
                value=""
              />
            </div>
         
          </div> -->
    </div>
  </div>
</template>

<style scoped>
/* .modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
  width: 1200px;
  height: 600px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 25px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
}

.modal-body {
  margin: 8px 0;
}

.button {
  padding: 0px 0 0 1100px;
} */
</style>
