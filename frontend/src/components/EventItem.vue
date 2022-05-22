<script setup>
import moment from 'moment'
import { ref } from 'vue'
import EventDetails from './EventDetails.vue'
import { useClock } from '../stores/clock.js'
const myClock = useClock()
defineEmits(['deleteNotice', 'saveNotice'])
defineProps({
  item: {
    type: Object,
    require: true
  }
})
const checkPeriod = (dateTime) => {
  if (moment(myClock.date).isBefore(moment(dateTime))) {
    // future
    return 'text-green-500'
  } else if (moment(myClock.date).isAfter(moment(dateTime))) {
    // past
    return 'text-red-500'
  } else {
    // current
    return 'text-blue-500'
  }
}
const categoryBg = (cateId) => {
  switch (cateId) {
    case 1:
      return 'bg-[#031B89]'
    case 2:
      return 'bg-[#6196FF]'
    case 3:
      return 'bg-[#A9B5FF]'
    case 4:
      return 'bg-[#FAA7B7]'
    case 5:
      return 'bg-[#FB7A8E]'
  }
}
const showMoreStatus = ref(false)
const showDetails = () => {
  showMoreStatus.value = !showMoreStatus.value
}
const closeShowMore = (status) => {
  showMoreStatus.value = status
}
</script>

<template>
  <div
    @click="showDetails"
    class="bg-white h-max w-[22.5%] p-2 rounded-lg cursor-pointer hover:scale-105 hover:drop-shadow-xl duration-300"
  >
    <div
      :class="[
        categoryBg(item.eventCategoryId),
        'text-white text-lg p-2 font-medium rounded-lg truncate'
      ]"
    >
      {{ item.bookingName }}
    </div>
    <div class="font-bold text-sm m-2">{{ item.categoryName }}</div>
    <div class="text-sm m-2">
      <span :class="checkPeriod(item.eventStartTime)">
        {{ moment(item.eventStartTime).format('DD MMM YYYY | H:mm') }}</span
      >
      <span class="float-right">{{ item.eventDuration }} min</span>
    </div>
    <div
      class="border-solid border-t border-[#E3E5E5] text-center text-[#367BF5] text-sm p-1 pt-2"
    >
      show more details
    </div>
  </div>
  <EventDetails
    v-if="showMoreStatus"
    :item="item"
    :color="categoryBg(item.eventCategoryId)"
    @closeShowMore="closeShowMore"
    @deleteSuccess="$emit('deleteNotice')"
    @saveSuccess="$emit('saveNotice')"
  />
</template>

<style></style>
