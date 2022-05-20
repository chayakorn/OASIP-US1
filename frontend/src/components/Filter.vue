<script setup>
import { computed, ref } from 'vue'
import { useCategories } from '../stores/categories.js'

const myCategory = useCategories()
myCategory.getAllCategories()
const categoryLists = computed(() => useCategories().categoryLists)

const selectedCategories = ref([])
</script>

<template>
  <div class="grid grid-rows-2 bg-white rounded-lg">
    <div class="grid grid-cols-4 text-center content-center px-1">
      <button class="rounded-md hover:bg-[#E3E5E5]">All</button>
      <button class="rounded-md hover:bg-[#E3E5E5]">Upcoming</button>
      <button class="rounded-md hover:bg-[#E3E5E5]">Past</button>
      <input type="date" />
    </div>
    <div class="grid grid-cols-5 text-center content-center px-1">
      <div v-for="(cate, index) in categoryLists">
        <input
          type="checkbox"
          :value="index + 1"
          v-model="selectedCategories"
        />
        <label>{{ cate.eventCategoryName }}</label>
      </div>
      <!-- <div>
        <input type="checkbox" value="2" v-model="selectedCategories" />
        <label>frontend</label>
      </div>
      <div>
        <input type="checkbox" value="3" v-model="selectedCategories" />
        <label>frontend</label>
      </div>
      <div>
        <input type="checkbox" value="4" v-model="selectedCategories" />
        <label>frontend</label>
      </div>
      <div>
        <input type="checkbox" value="5" v-model="selectedCategories" />
        <label>frontend</label>
      </div> -->
    </div>
    <p>{{ selectedCategories }}</p>
  </div>
</template>

<style></style>
