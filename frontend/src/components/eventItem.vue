<script setup>
import moment from 'moment'
defineProps({
  item: {
    type: Object,
    require: true
  }
})
const categoryBg = (catId) => {
  switch (catId) {
    case 1:
      return 'bg-[#6196FF]'
    case 2:
      return 'bg-[#A9B5FF]'
    case 3:
      return 'bg-[#FAA7B7]'
    case 4:
      return 'bg-[#FB7A8E]'
    case 5:
      return 'bg-[#031B89]'
  }
}
</script>

<template>
  <div class="bg-white w-[22.5%] p-2 rounded-lg">
    <router-link
      :to="{
        name: 'EventDetails',
        params: { eventId: item.id, eventItem: JSON.stringify(item) }
      }"
      ><div
        :class="[
          categoryBg(item.eventCategoryId),
          'text-white text-lg p-2 font-medium rounded-lg'
        ]"
      >
        {{ item.bookingName }}
      </div>
      <div class="font-bold text-sm m-2">{{ item.categoryName }}</div>
      <div class="text-sm m-2">
        {{ moment.utc(item.eventStartTime).format('DD MMM YYYY | h:mm A') }}
        <span class="float-right">{{ item.eventDuration }} mins</span>
      </div>
      <div
        class="border-solid border-t border-[#E3E5E5] text-center text-[#367BF5] text-sm p-1 pt-2"
      >
        show more details
      </div>
    </router-link>
  </div>
</template>

<style></style>
