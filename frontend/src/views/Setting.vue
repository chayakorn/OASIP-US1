<script setup>
import { ref, computed } from 'vue'
import { useCategories } from '../stores/categories.js';
// defineProps({
//   colorCode: {
//     type: String,
//     require: true
//   }
// })
// const myCategory = useCategories()
// myCategory.getAllCategories()
// const categoryLists = computed(() => myCategory.categoryLists)

// const categoryBg = (cateId) => {
//   switch (cateId) {
//     case 1:
//       return 'bg-[#031B89]'
//     case 2:
//       return 'bg-[#6196FF]'
//     case 3:
//       return 'bg-[#A9B5FF]'
//     case 4:
//       return 'bg-[#FAA7B7]'
//     case 5:
//       return 'bg-[#FB7A8E]'
//   }
// }
const isEdit = ref(false)
const editMode = (index) => {
  isEdit.value = true
  console.log(index)
}
const closeEditMode = ()=>{
  isEdit.value = false
}

const saveChange = ()=>{
  isEdit.value = false
}

</script>

<template>
  <div class="p-10">
    <div class="text-4xl font-bold">
      Event Categories
    </div>

    <div class=" my-5 grid place-items-center">
      <table class="w-full text-sm text-center text-gray-500 dark:text-gray-400 ">
        <thead class="text-base font-light text-[#919699] uppercase ">
          <tr>
            <th scope="col" class="px-6 py-3 text-left">
              Category name
            </th>
            <th scope="col" class="px-6 py-3 text-center">
              Description
            </th>
            <th scope="col" class="px-6 py-3 text-left">
              Duration
            </th>
            <th scope="col" class="px-6 py-3">
              <span class="sr-only">Edit</span>
            </th>
          </tr>
        </thead>

        <!-- Body -->
        <tbody class="">
          <tr class="bg-white border-b  hover:scale-105 duration-500">
            <th scope="row" class="px-6 py-5 font-medium text-gray-900">
              <div v-show=!isEdit :class="['bg-red-500 text-white text-lg p-5 pl-6 font-semibold rounded-xl']">
                <!-- {{ category.eventCategoryName }} -->
                Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live.
              </div>
              {{ index }}
              <div v-show="isEdit">
                HI
              </div>
            </th>
            <td class="px-5 py-4 text-sm">
              <div></div>
              <!-- <div v-if="category.eventCategoryDescription.length <= 0">No description</div> -->
              <div v-show="!isEdit">Far far away, behind the word mountains, far from the countries Vokalia and
                Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the
                Semantics, a large language ocean. A small river named Duden flows by their place and supplies it with
                the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into
                your mouth. Even the all-powerful Pointing has no control about the blind texts it is an almost
                unorthographic lif
              </div>
              <div v-show="isEdit">
                HI
              </div>
            </td>
            <td class="px-6 py-4">
              <div v-show="!isEdit">480 minutes</div>
              <div v-show="isEdit">
                HI
              </div>
            </td>
            <td class="px-8 py-4 text-right">
              <div v-show="!isEdit" class="text-lg cursor-pointer text-blue-600 dark:text-blue-500 hover:underline"
                @click="editMode(index)">Edit</div>
              <div v-show="isEdit" class="flex">
                <span class="pr-5 cursor-pointer" @click="saveChange" >
                  <svg width="3em" height="3em" viewBox="0 0 24 24">
                    <path fill="none" stroke="#2FA84F" strokeDasharray="24" strokeDashoffset="24" strokeLinecap="round"
                      strokeLinejoin="round" strokeWidth="2" d="M5 11L11 17L21 7">
                      <animate fill="freeze" attributename="stroke-dashoffset" dur="0.4s" values="24;0"></animate>
                    </path>
                  </svg>
                </span>

                <span class="cursor-pointer" @click="closeEditMode">
                  <svg width="3em" height="3em" viewBox="0 0 24 24">
                    <path fill="none" stroke="#EA3D2F" strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5"
                      d="M6.758 17.243L12.001 12m5.243-5.243L12 12m0 0L6.758 6.757M12.001 12l5.243 5.243"></path>
                  </svg>
                </span>
              </div>
            </td>
          </tr>

          <tr class="bg-white border-b  hover:scale-105 duration-500">
            <th scope="row" class="px-6 py-5 font-medium text-gray-900">
              <div v-show=!isEdit :class="['bg-red-500 text-white text-lg p-5 pl-6 font-semibold rounded-xl']">
                <!-- {{ category.eventCategoryName }} -->
                Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live.
              </div>
              <div v-show="isEdit">
                HI
              </div>
            </th>
            <td class="px-5 py-4 text-sm">
              <div></div>
              <!-- <div v-if="category.eventCategoryDescription.length <= 0">No description</div> -->
              <div v-show="!isEdit">Far far away, behind the word mountains, far from the countries Vokalia and
                Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the
                Semantics, a large language ocean. A small river named Duden flows by their place and supplies it with
                the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into
                your mouth. Even the all-powerful Pointing has no control about the blind texts it is an almost
                unorthographic lif
              </div>
              <div v-show="isEdit">
                HI
              </div>
            </td>
            <td class="px-6 py-4">
              <div v-show="!isEdit">480 minutes</div>
              <div v-show="isEdit">
                HI
              </div>
            </td>
            <td class="px-8 py-4 text-right">
              <div v-show="!isEdit" class="text-lg cursor-pointer text-blue-600 dark:text-blue-500 hover:underline"
                @click="editMode(index)">Edit</div>
              <div v-show="isEdit" class="flex">
                <span class="pr-5 cursor-pointer" @click="saveChange" >
                  <svg width="3em" height="3em" viewBox="0 0 24 24">
                    <path fill="none" stroke="#2FA84F" strokeDasharray="24" strokeDashoffset="24" strokeLinecap="round"
                      strokeLinejoin="round" strokeWidth="2" d="M5 11L11 17L21 7">
                      <animate fill="freeze" attributename="stroke-dashoffset" dur="0.4s" values="24;0"></animate>
                    </path>
                  </svg>
                </span>

                <span class="cursor-pointer" @click="closeEditMode">
                  <svg width="3em" height="3em" viewBox="0 0 24 24">
                    <path fill="none" stroke="#EA3D2F" strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5"
                      d="M6.758 17.243L12.001 12m5.243-5.243L12 12m0 0L6.758 6.757M12.001 12l5.243 5.243"></path>
                  </svg>
                </span>
              </div>
            </td>
          </tr>


        </tbody>

      </table>
    </div>

  </div>
</template>

<style>
</style>
