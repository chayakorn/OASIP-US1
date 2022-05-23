<script setup>
import { computed } from 'vue'
import { useCategories } from '../stores/categories.js'
const myCategory = useCategories()
myCategory.getAllCategories()
const categoryLists = computed(() => myCategory.categoryLists)

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
</script>

<template>
  <div class="p-10">
    <div class="text-4xl font-bold">Event Categories</div>

    <div class="my-5 grid place-items-center">
      <table
        class="w-full text-sm text-center text-gray-500 dark:text-gray-400"
      >
        <thead class="text-base font-light text-[#919699] uppercase">
          <tr>
            <th scope="col" class="px-6 py-3 text-left">Category name</th>
            <th scope="col" class="px-6 py-3 text-center">Description</th>
            <th scope="col" class="px-6 py-3 text-left">Duration</th>
            <th scope="col" class="px-6 py-3">
              <span class="sr-only">Edit</span>
            </th>
          </tr>
        </thead>
        <tbody
          class=""
          v-for="(category, index) in categoryLists.sort((a, b) => b.id - a.id)"
          :key="index"
        >
          <tr
            class="bg-white border-b hover:scale-[1.02] duration-500 shadow-xl"
          >
            <th scope="row" class="px-6 py-5 font-medium text-gray-900">
              <div
                :class="[
                  categoryBg(category.id),
                  'text-white text-lg p-4 pl-6 font-semibold rounded-xl'
                ]"
              >
                {{ category.eventCategoryName }}
              </div>
            </th>
            <td class="px-6 py-4 text-sm">
              <div></div>
              <div v-if="category.eventCategoryDescription.length <= 0">
                No description
              </div>
              <div v-else-if="category.eventCategoryDescription.length > 0">
                {{ category.eventCategoryDescription }}
              </div>
            </td>
            <td class="px-6 py-4">
              <div>{{ category.eventDuration }} minutes</div>
            </td>
            <td class="px-6 py-4 text-right">
              <router-link
                :to="{ name: 'EditSetting', params: { category: category.id } }"
              >
                <div
                  class="font-medium cursor-pointer text-blue-600 dark:text-blue-500 hover:underline"
                >
                  Edit
                </div></router-link
              >
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style></style>
