<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
const myRouter = useRouter()
const { params } = useRoute()
const closem = () => myRouter.push({ name: 'EventLists' })
const eventLists = ref([
  {
    bookingId: 1,
    bookingName: 'Somchai Jaidee (OR-7)',
    bookingEmail: 'somchai.jai@mail.kmutt.ac.th',
    eventCategoryName: 'DevOps/Infra Clinic',
    eventStartTime: '23 May 2022 13.30',
    eventDuration: 30,
    eventNotes: ''
  },
  {
    bookingId: 2,
    bookingName: 'Somsri Rakdee (SJ-3)',
    bookingEmail: 'somsri.rak@mail.kmutt.ac.th',
    eventCategoryName: 'Project Management Clinic',
    eventStartTime: '27 April 2022 9.30',
    eventDuration: 30,
    eventNotes: 'ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน'
  },
  {
    bookingId: 3,
    bookingName: 'สมเกียรติ ขยันเรียน กลุ่ม TT-4',
    bookingEmail: 'somkiat.kay@kmutt.ac.th',
    eventCategoryName: 'Database Clinic',
    eventStartTime: '23 May 2022 16.30',
    eventDuration: 15,
    eventNotes: ''
  }
])
</script>

<template>
  <div class="modal-mask">
    <div class="modal-wrapper">
      <div class="modal-container">
        <div class="modal-header">
          <h3>Schedule details</h3>
          <button @click="closem">
            <svg width="3em" height="3em" viewBox="0 0 32 32">
              <path
                fill="currentColor"
                d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z"
              ></path>
            </svg>
          </button>
        </div>
        <!-- Body -->
        <div class="modal-body">
          <div v-for="list in eventLists">
            <ul>
              <li>{{ list.bookingName }}</li>
            </ul>
          </div>
          <div class="md:flex md:items-center mb-6">
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
                value="{{{}}}}}"
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
            {{ params.eventItem }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.modal-mask {
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

.modal-header h3 {
  margin-top: 0;
  color: black;
  font-weight: bold;
}

.modal-body {
  margin: 30px 0;
}
.button {
  padding: 0px 0 0 1100px;
}
</style>
