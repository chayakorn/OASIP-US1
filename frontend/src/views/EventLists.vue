<script setup>
import moment from 'moment'
import EventItem from '../components/EventItem.vue'
import Notice from '../components/Notice.vue'
import { computed, ref } from 'vue'
import { useEvents } from '../stores/events.js'
import { useCategories } from '../stores/categories.js'

// State of categories
const myCategory = useCategories()
myCategory.getAllCategories()
const categoryLists = computed(() => useCategories().categoryLists)

// State of events
const myEvents = useEvents()
myEvents.getEventsPage()
const eventLists = computed(() => myEvents.eventLists)

// Event list filter by All, Upcoming, Past
const period = ref('a')
const filterPeriod = () => {
  myEvents.getEventsPage(
    period.value,
    selectedCategories.value.length > 0
      ? selectedCategories.value
      : [1, 2, 3, 4, 5],
    0,
    page.value.pageSize,
    period.value == 'u' ? true : false
  )
  date.value = ''
  filterBy.value = false
}

// Filter by toggle, categories or date
const filterBy = ref(false)
const chooseCategory = () => {
  filterBy.value = !filterBy.value
  date.value = ''
  myEvents.getEventsPage()
}
const chooseDate = () => {
  filterBy.value = !filterBy.value
  selectedCategories.value = []
  period.value = 'a'
  myEvents.getEventsPage()
}

// Event list filter by categories
const selectedCategories = ref([])
const checkSelected = () =>
  selectedCategories.value.length > 0
    ? selectedCategories.value
    : [1, 2, 3, 4, 5]
const filterCategories = () =>
  setTimeout(() => {
    myEvents.getEventsPage(
      period.value,
      checkSelected(),
      0,
      page.value.pageSize,
      period.value == 'u' ? true : false
    )
  }, 0.001)

// Event list filter by date
const date = ref()
const filterDate = () =>
  myEvents.getEventByDate(
    date.value,
    moment().format().split('+')[1],
    moment().format().split('T')[1].includes('-'),
    0,
    page.value.pageSize,
    true
  )

// Pagination
const page = computed(() => {
  return {
    pageNumber: myEvents.listDetails.number,
    pageSize: myEvents.listDetails.size
  }
})
const loadMore = () =>
  myEvents.getEventsPage(
    period.value,
    selectedCategories.value.length > 0
      ? selectedCategories.value
      : [1, 2, 3, 4, 5],
    ++page.value.pageNumber,
    page.value.pageSize,
    period.value == 'u' ? true : false
  )

// Reset
const reset = () => {
  myEvents.getEventsPage()
  selectedCategories.value = []
  period.value = 'a'
  date.value = ''
  filterBy.value = false
}

// Notice
const noticeDelete = ref(false)
const toggleDelete = () => {
  noticeDelete.value = true
  setTimeout(() => {
    noticeDelete.value = false
  }, 1500)
}

const noticeSaved = ref(false)
const toggleSave = () => {
  noticeSaved.value = true
  setTimeout(() => {
    noticeSaved.value = false
  }, 1500)
}
</script>

<template>
  <div>
    <div class="grid p-10 pb-0">
      <div class="grid grid-cols-5">
        <div class="col-span-2">
          <div class="text-4xl font-bold mb-5">Scheduled Events</div>
          <div>
            <select
              class="rounded-lg p-1 focus:outline-none"
              v-model="period"
              @change="filterPeriod"
            >
              <option value="a">All Events</option>
              <option value="u">Upcoming Events</option>
              <option value="p">Past Events</option>
            </select>
            <span class="ml-2 text-gray-400"
              >{{ myEvents.listDetails.totalElements }} events</span
            >
          </div>
        </div>
        <div class="col-span-3 grid grid-rows-2 gap-y-5">
          <div class="flex items-center justify-end gap-x-5">
            Filter by
            <div
              class="grid grid-cols-2 w-1/4 h-10 p-1 font-light gap-x-1 bg-white rounded-lg border-2"
            >
              <button
                type="button"
                @click="chooseCategory"
                :disabled="!filterBy"
                class="w-full rounded-md disabled:bg-[#919699] disabled:text-white disabled:shadow-inner"
              >
                Category
              </button>
              <button
                type="button"
                @click="chooseDate"
                :disabled="filterBy"
                class="w-full rounded-md disabled:bg-[#919699] disabled:text-white disabled:shadow-inner"
              >
                Date
              </button>
            </div>
          </div>
          <div class="flex items-center justify-end gap-x-5">
            <div v-if="filterBy" class="flex items-center justify-end gap-x-2">
              <span>Date</span>:<input
                type="date"
                v-model="date"
                class="px-1 rounded-xl"
                @change="filterDate"
              />
            </div>
            <div v-else class="flex items-center justify-end gap-x-5 text-sm">
              <div
                v-for="(cate, index) in categoryLists"
                class="rounded-md hover:scale-95 duration-500"
              >
                <input
                  type="checkbox"
                  :id="index + 1"
                  :value="index + 1"
                  v-model="selectedCategories"
                  class="chk-btn"
                />
                <label :for="index + 1" @click="filterCategories">{{
                  cate.eventCategoryName.split('Clinic')[0]
                }}</label>
              </div>
            </div>
            <button
              @click="reset"
              class="text-white px-3 rounded-full bg-red-500 hover:bg-red-600 hover:scale-95 duration-500"
            >
              Reset all
            </button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="eventLists.length > 0">
      <div
        :class="[
          'flex flex-wrap gap-x-10 gap-y-5 py-2 pl-5 pr-3 ml-5 mr-10 mt-5 overflow-y-auto rounded-lg',
          myEvents.listDetails.totalElements < 12 ? 'h-min' : 'h-[75vh]'
        ]"
      >
        <EventItem
          v-for="list in eventLists"
          :item="list"
          @deleteNotice="toggleDelete"
          @saveNotice="toggleSave"
        />
        <div v-if="!myEvents.listDetails.last" class="w-full text-center">
          <button @click="loadMore" class="font-semibold text-[#367BF5]">
            Load more . . .
          </button>
        </div>
      </div>
    </div>
    <div
      v-else
      class="h-3/4 grid place-content-center text-[#C6CACC] font-bold text-4xl"
    >
      <span v-if="period == 'a'">No Scheduled Events.</span>
      <span v-if="period == 'u'">No On-Going or Upcoming Events.</span>
      <span v-if="period == 'p'">No Past Events.</span>
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
