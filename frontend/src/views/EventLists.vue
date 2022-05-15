<script setup>
import { computed } from 'vue'
import EventItem from '../components/EventItem.vue'
import { useEvents } from '../stores/events.js'

//use state "Events"
const myEvents = useEvents()
const eventLists = computed(() => myEvents.eventLists)
//  GET
myEvents.getAllEvents()

const currentLists = computed(() =>
  eventLists.value.sort(
    (a, b) => new Date(b.eventStartTime) - new Date(a.eventStartTime)
  )
)
</script>

<template>
  <div class="p-10">
    <div class="text-4xl font-bold">Scheduled Events</div>
    <div class="my-5">
      <span class="text-2xl font-bold">All Events</span
      ><span class="ml-2 text-gray-400">{{ eventLists.length }} events</span>
    </div>
    <div v-if="eventLists.length > 0">
      <!-- No Group -->
      <div class="contentSize flex flex-wrap gap-x-10 gap-y-5">
        <EventItem v-for="list in currentLists" :item="list" />
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
  </div>
</template>

<style></style>
