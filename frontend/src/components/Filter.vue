<script setup>
import { computed, ref } from 'vue'
import { useCategories } from '../stores/categories.js'

const myCategory = useCategories()
myCategory.getAllCategories()
const categoryLists = computed(() => useCategories().categoryLists)

const selectedCategories = ref([])
</script>

<template>
  <div class="grid grid-rows-2 rounded-lg">
    <div class="grid grid-cols-4 text-center content-center px-1">
      <button class="rounded-md hover:bg-[#E3E5E5] py-1">All</button>
      <button class="rounded-md hover:bg-[#E3E5E5]">Upcoming</button>
      <button class="rounded-md hover:bg-[#E3E5E5]">Past</button>
      <input type="date" />
    </div>
    <div class="flex items-center gap-x-5 px-5 text-sm">
      <div v-for="(cate, index) in categoryLists">
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
      <!-- <div class="dropdown inline-block relative">
        <button
          class="text-gray-700 font-semibold py-2 px-4 rounded inline-flex items-center"
        >
          <span class="mr-1">Categories</span>
          <svg
            class="fill-current h-4 w-4"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 20 20"
          >
            <path
              d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"
            />
          </svg>
        </button>
        <div class="dropdown-menu absolute hidden text-gray-700 pt-1 w-full">
          <div
            v-for="(cate, index) in categoryLists"
            :class="[
              'bg-red-500 text-left h-10',
              index == 0 ? 'rounded-t' : '',
              index == categoryLists.length - 1 ? 'rounded-b' : ''
            ]"
          >
            <input
              type="checkbox"
              :value="index + 1"
              v-model="selectedCategories"
            />
            <label>{{ cate.eventCategoryName.split('Clinic')[0] }}</label>
          </div>
        </div>
      </div> -->
    </div>
  </div>
</template>

<style></style>
