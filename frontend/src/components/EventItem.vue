<script setup>
import moment from 'moment'
import { ref } from 'vue'
import EventDetails from './EventDetails.vue'
const props = defineProps({
  item: {
    type: Object,
    require: true
  }
})

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
const showMore = ref(false)

const closeModal = (status) => {
  showMore.value = status

}

const showDetails = ()=>{
  showMore.value = !showMore.value
}

const refreshPage = (itemId)=>{
  showMore.value = false
 filter((item)=>{item !== itemId})
}
</script>

<template>
  <div
    @click="showDetails"
    class="bg-white w-[22.5%] p-2 rounded-lg cursor-pointer"
  >
    <div
      :class="[
        categoryBg(item.eventCategoryId),
        'text-white text-lg p-2 font-medium rounded-lg'
      ]"
    >
      {{ item.bookingName }}
    </div>
    <div class="font-bold text-sm m-2">{{ item.categoryName }}</div>
    <div class="text-sm m-2">
      {{ moment(item.eventStartTime).format('DD MMM YYYY | h:mm A') }}
      <span class="float-right">{{ item.eventDuration }} mins</span>
    </div>
    <div
      class="border-solid border-t border-[#E3E5E5] text-center text-[#367BF5] text-sm p-1 pt-2"
    >
      show more details
    </div>
  </div>
  <EventDetails
    v-show="showMore"
    :item="item"
    :color="categoryBg(item.eventCategoryId)"
    @closeModal="closeModal"
    @refreshPage="refreshPage"
  />
</template>

<style></style>
