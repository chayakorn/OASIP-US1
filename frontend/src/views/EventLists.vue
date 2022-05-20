<script setup>
import { computed, ref } from 'vue'
import EventItem from '../components/EventItem.vue'
import { useEvents } from '../stores/events.js'
import Notice from '../components/Notice.vue'
import Filter from '../components/Filter.vue'

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
</script>

<template>
  <div class="p-10">
    <div class="grid grid-cols-2 mb-5">
      <div>
        <div class="text-4xl font-bold">Scheduled Events</div>
        <div class="">
          <span class="text-2xl font-bold">All Events</span
          ><span class="ml-2 text-gray-400"
            >{{ eventLists.length }} events</span
          >
        </div>
      </div>
      <Filter />
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

<style></style>
