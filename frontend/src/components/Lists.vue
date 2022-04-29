<script setup>
import { computed } from 'vue'
import EventItem from './eventItem.vue'
import moment from 'moment'

const props = defineProps({
  lists: {
    // No Group
    type: Array,
    default: []
    // GroupBy Day
    // type: Object,
    // default: {}
  }
})
const currentLists = computed(() => props.lists)

</script>

<template>
  <div>
    <!-- No Group -->
    <div class="flex flex-wrap gap-x-10 gap-y-5">
      <EventItem v-for="list in currentLists" :item="list" />
    </div>

    <!-- GroupBy Day -->
    <!-- <div
      v-for="list in currentLists"
      class="flex flex-wrap gap-x-10 gap-y-5 mt-10"
    >
      <h2
        class="w-full border-solid border-b border-black text-center leading-[0.1em] bg-[#F7F9FA]"
      >
        <span class="bg-[#F7F9FA] px-5">{{
          moment.utc(list[0].eventStartTime).format('DD MMMM YYYY')
        }}</span>
      </h2>
      <EventItem v-for="l in list" :item="l" />
    </div> -->
  </div>
</template>

<style>
</style>
