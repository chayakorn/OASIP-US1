<script setup>
import { ref } from 'vue'
import moment from 'moment'
import { useRoute, useRouter } from 'vue-router'
const myRouter = useRouter()
const { params } = useRoute()
const eventI = JSON.parse(params.eventItem)

const date = moment.utc(eventI.eventStartTime).format('DD MMM YYYY')
const time = moment.utc(eventI.eventStartTime).format('h:mm a')
const closem = () => myRouter.push({ name: 'EventLists' })
</script>

<template>
  <div class="modal-mask">
    <div class="modal-wrapper">
      <div class="modal-container">
        <div class="modal-header">

          <div class="float-right mb-5">
            <button class="hover:bg-neutral-100  rounded-lg pl-3 pt-2" @click="closem">
              <svg width="3em" height="3em" viewBox="0 0 32 32">
                <path fill="currentColor"
                  d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z">
                </path>
              </svg>
            </button>
          </div>
          <div class="font-bold text-xl text-center ">Schedule details</div>

        </div>
        <!-- Body -->
        <div class="modal-body">
          <div
            class="flex-none bg-gray-200 appearance-none border-2 border-gray-200 rounded-xl w-full py-2 px-4 text-black-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500">
            {{ eventI.bookingName }}
          </div>
          <br>
          <div>
            Clinic
            <div
              class="flex-none bg-gray-200 appearance-none border-2 border-gray-200 rounded-xl w-full py-2 px-4 text-black-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500">
              {{ eventI.categoryName }} <br>
            </div>
          </div>

          Date {{ date }}
          Time {{ time }}

          <svg width="1em" height="1em" viewBox="0 0 24 24">
            <path fill="currentColor"
              d="M12 20c4.4 0 8-3.6 8-8s-3.6-8-8-8s-8 3.6-8 8s3.6 8 8 8m0-18c5.5 0 10 4.5 10 10s-4.5 10-10 10S2 17.5 2 12S6.5 2 12 2m5 11.9l-.7 1.3l-5.3-2.9V7h1.5v4.4l4.5 2.5Z">
            </path>

          </svg> {{ eventI.eventDuration }} mins <br>


          Name {{ eventI.name }} <br>
          Email {{ eventI.bookingEmail }} <br>
          Description {{ eventI.eventNotes }}
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
