<script setup>
import { computed, ref } from 'vue'
import EventItem from '../components/EventItem.vue'
import { useEvents } from '../stores/events.js'
import Notice from '../components/Notice.vue'
import { useCategories } from '../stores/categories.js'
import moment from 'moment'
import { useClock } from '../stores/clock.js'
const myClock = useClock()
// use state "Events"
const myEvents = useEvents()
//  GET
myEvents.getAllEvents()

const eventLists = computed(() =>
  myEvents.eventLists.filter((e) => checkPeriod(e.eventStartTime) == true)
)

const noticeDelete = ref(false)
const toggleDelete = () => {
  noticeDelete.value = true
  setTimeout(() => {
    noticeDelete.value = false
  }, 2000)
}

const noticeSaved = ref(false)
const toggleSave = () => {
  noticeSaved.value = true
  setTimeout(() => {
    noticeSaved.value = false
  }, 2000)
}

const myCategory = useCategories()
myCategory.getAllCategories()
const categoryLists = computed(() => useCategories().categoryLists)

const period = ref(1)
const checkPeriod = (dateTime) => {
  switch (period.value) {
    case 1:
      return true
    case 2:
      return moment(myClock.date).isBefore(moment(dateTime))
    case 3:
      return moment(myClock.date).isAfter(moment(dateTime))
  }
}
const date = ref()
const selectedCategories = ref([])
const sendCategory = () =>
  setTimeout(() => {
    myEvents.getEventsByCategories(selectedCategories.value)
  }, 0.01)
</script>

<template>
  <div class="p-10">
    <div class="grid mb-5">
      <div class="grid grid-cols-5">
        <div class="col-span-2">
          <div class="text-4xl font-bold mb-5">Scheduled Events</div>
          <div>
            <select class="rounded-lg p-1" v-model="period">
              <option :value="1">All Events</option>
              <option :value="2">Upcoming</option>
              <option :value="3">Past</option>
            </select>
            <span class="ml-2 text-gray-400"
              >{{ eventLists.length }} events</span
            >
          </div>
        </div>
        <div class="col-span-3 grid grid-rows-3 gap-y-1 px-5">
          <div class="flex items-center justify-end">Filter by</div>
          <div class="flex items-center justify-end gap-x-2">
            <span>Date</span>:<input
              type="date"
              v-model="date"
              class="px-1 rounded-xl"
              @change="filterDate"
            />
          </div>
          <div class="flex items-center justify-end gap-x-5 text-sm">
            <div
              @click="sendCategory"
              v-for="(cate, index) in categoryLists"
              class="rounded-md duration-500"
            >
              <input
                type="checkbox"
                :id="index + 1"
                :value="index + 1"
                v-model="selectedCategories"
                class="chk-btn"
              />
              <label :for="index + 1">{{
                cate.eventCategoryName.split('Clinic')[0]
              }}</label>
            </div>
            <button
              @click="
                myEvents.getAllEvents(), (selectedCategories = []), (date = '')
              "
              class="hover:border-b-4 border-red-500"
            >
              Clear all
            </button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="eventLists.length > 0">
      <!-- No Group -->
      <div class="contentSize flex flex-wrap gap-x-10 gap-y-5">
        <EventItem
          v-for="list in eventLists.sort(
            (a, b) => new Date(b.eventStartTime) - new Date(a.eventStartTime)
          )"
          :item="list"
          @deleteNotice="toggleDelete"
          @saveNotice="toggleSave"
        />
      </div>
      <!-- GroupBy Day -->
      <!-- <Lists
        :lists="
          result
            .sort(
              (a, b) =>
                new Date(b[0].eventStartTime) - new Date(a[0].eventStartTime)
            )
            .map((a) =>
              a.sort(
                (a, b) =>
                  new Date(b.eventStartTime) - new Date(a.eventStartTime)
              )
            )
        "
      /> -->
    </div>
    <div
      v-else
      class="h-3/4 grid place-content-center text-[#C6CACC] font-bold text-4xl"
    >
      No Scheduled Events.
    </div>
    <Notice
      v-if="noticeDelete"
      text="Successfully deleted the schedule !"
      color="#EA3D2F"
    />
    <Notice
      v-if="noticeSaved"
      text="Successfully edited the schedule !"
      color="#367BF5"
    />
  </div>
</template>

<style scoped>
.dropdown:hover .dropdown-menu {
  display: block;
}

input.chk-btn {
  display: none;
}
input.chk-btn + label {
  background: ghoswhite;
  padding: 5px 8px;
  cursor: pointer;
  border-radius: 5px;
}
input.chk-btn:not(:checked) + label:hover {
  background-color: #c6cacc;
}
input.chk-btn + label:active,
input.chk-btn:checked + label {
  color: white;
  background-color: #919699;
}
</style>
