<script setup>
import { onBeforeMount, ref, computed } from 'vue'
import EventItem from '../components/EventItem.vue';
// import Lists from '../components/Lists.vue'

const eventLists = ref([])
const getAllEvents = async () => {
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/event`)
  if (res.status === 200) {
    eventLists.value = await res.json()
  } else console.log('error, cannot get events')
}

// getAllEvents()
onBeforeMount(async () => {
  await getAllEvents()
})

const itemId = (itemId) => {
  eventLists.value = eventLists.value.filter((id) => id.id != itemId)
}
// const result = Object.values(
//   eventLists.value.reduce((acc, x) => {
//     acc[moment.utc(x.eventStartTime).format('DD MMM YYYY')] = [
//       ...(acc[moment.utc(x.eventStartTime).format('DD MMM YYYY')] || []),
//       x
//     ]
//     return acc
//   }, {})
// )
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
        <EventItem v-for="list in currentLists" :item="list" @itemId="itemId"/>
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
