<script setup>
import { computed, ref } from 'vue'
import EventItem from '../components/EventItem.vue'
import { useEvents } from '../stores/events.js'
import Notice from '../components/Notice.vue'
import Filter from '../components/Filter.vue'
import { useCategories } from '../stores/categories.js'

// use state "Events"
const myEvents = useEvents()
//  GET
myEvents.getAllEvents()
const eventLists = computed(() => myEvents.eventLists)

const currentLists = computed(() =>
  eventLists.value.sort(
    (a, b) => new Date(b.eventStartTime) - new Date(a.eventStartTime)
  )
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

const selectedCategories = ref([])
</script>

<template>
  <div class="p-10">
    <div class="grid mb-5">
      <div>
        <div class="text-4xl font-bold mb-5">Scheduled Events</div>
        <div class="grid grid-cols-5">
          <div class="col-span-2">
            <span class="text-2xl font-bold">All Events</span
            ><span class="ml-2 text-gray-400"
              >{{ eventLists.length }} events</span
            >
          </div>
          <div class="flex items-center gap-x-5 px-5 text-sm col-span-3">
            <div
              v-for="(cate, index) in categoryLists"
              class="hover:animate-bounce"
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
            <button @click="selectedCategories = []">Clear</button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="eventLists.length > 0">
      <!-- No Group -->
      <div class="contentSize flex flex-wrap gap-x-10 gap-y-5">
        <EventItem
          v-for="list in currentLists"
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
  box-shadow: 0px 1px 3px;
}
input.chk-btn + label:active,
input.chk-btn:checked + label {
  background: white;
  box-shadow: 0px 1px 3px;
}
</style>
