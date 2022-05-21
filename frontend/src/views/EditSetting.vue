<script setup>
import { ref, computed, onBeforeMount } from 'vue';
import { useRoute } from 'vue-router'
import { useCategories } from '../stores/categories.js';
import Confirm from '../components/Confirm.vue'
import router from '../router/index.js';


const myCategory = ref({ "id": "", "eventCategoryName": "", "eventCategoryDescription": "", "eventDuration": 0 })
const useCategory = useCategories()
useCategory.getAllCategories()
const categoryLists = computed(() => useCategory.categoryLists)
onBeforeMount(useCategories().getCategoryById(useRoute().params.category).then((e) => myCategory.value = e))
const emit = defineEmits(['save', 'cancelSave'])

const isDataValid = computed(() => myCategory.value.eventCategoryName.trim().length > 0 && myCategory.value.eventCategoryName.length <= 100 &&
    myCategory.value.eventDuration > 0 && myCategory.value.eventCategoryDescription.length <= 500 && !catNameNotUnique.value)

const catNameNotUnique = ref(false)
const checkName = () => {
    catNameNotUnique.value = false
    categoryLists.value.forEach(e => {
        if (e.eventCategoryName.trim().toLowerCase() === myCategory.value.eventCategoryName.trim().toLowerCase() && e.id != myCategory.value.id) {
            catNameNotUnique.value = true
        }
    })
}


const updateCategory = () => {
    // useCategory.editCategory({
    //     eventCategoryName: myCategory.value.eventCategoryName,
    //     eventCategoryDescription: myCategory.value.eventCategoryDescription,
    //     eventDuration: myCategory.value.eventDuration
    // }, myCategory.value.id)
    router.push({ name: 'Setting'})
}

const saveStatus = ref(false)
const save = (status) => {
    saveStatus.value = true
    emit('save')
}

const cancelStatus = ref(false)
const cancelSave = (status) =>{
    cancelStatus.value = true
    emit('cancelSave')
}

</script>
 
<template>
    <div class="h-screen p-10 " id="background">
        <div class="text-4xl font-bold">
            Editing Event Categories
        </div>
        <div class="flex justify-center my-3">

            <div class="grid place-items-center w-3/5 m-8 shadow-xl bg-white rounded-2xl py-8 gap-5">
                <div class="pt-4">
                    <div class="pb-1 text-sm font-semibold text-[#5E6366] flex">Category Name</div>
                    <div v-if="myCategory.eventCategoryName.trim().length < 1" class="text-xs text-left text-red-500">*
                        Please
                        fill out this field.</div>
                    <input type="text"
                        class=" bg-[#F1F3F4] rounded-md w-[42rem] h-10 pl-3 text-center focus:outline-none  focus:border-[#031B89] border-2 border-[#F1F3F4]"
                        v-model="myCategory.eventCategoryName" required @keyup="checkName()" />
                    <div v-if="myCategory.eventCategoryName.length > 100" class="absolute text-xs text-red-500">
                        The number of characters has a limit of 100 characters. If it
                        exceeds 100, it will not be able to continue typing.</div>
                    <div v-if="catNameNotUnique" class="text-red-500 text-xs ">This category name has been used.
                        Please use another name.</div>
                </div>

                <div class="">
                    <div class="pb-1 text-sm font-semibold text-[#5E6366]">Description</div>
                    <textarea
                        class="resize-none w-[42rem] h-[13rem] rounded-lg px-5 pt-2 text-center bg-[#F1F3F4] focus:outline-none  focus:border-[#031B89] border-2 border-[#F1F3F4]"
                        v-model="myCategory.eventCategoryDescription"
                        :placeholder="myCategory.eventCategoryDescription" />
                    <div v-if="myCategory.eventCategoryDescription.length > 500" class="text-xs text-red-500">
                        The number of characters has a limit of 500 characters. If it
                        exceeds 500, it will not be able to continue typing.
                    </div>
                </div>

                <div class="flex justify-center">
                    <div class="pb-1 text-sm font-semibold text-[#5E6366] px-3 py-2">Duration</div>
                    <div v-if="myCategory.eventDuration.length < 1" class="text-xs text-left text-red-500">*
                        Please
                        fill out this field.</div>

                    <input type="number"
                        class=" text-center rounded-md h-9 w-[9rem] bg-[#F1F3F4] focus:outline-none  focus:border-[#031B89] border-2 border-[#F1F3F4]"
                        v-model="myCategory.eventDuration" :placeholder="myCategory.eventDuration" min="1" max="480" />
                    <span class="px-3 py-1">minutes</span>
                    <div v-if="myCategory.eventDuration > 480" class="absolute text-xs text-red-500">
                        The range of number must be 1-480
                    </div>
                </div>


                <div>
                    <button :disabled="!isDataValid"
                        class="text-white bg-blue-700 w-24 rounded-lg text-sm py-2.5 text-center hover:scale-[1.02] duration-500 disabled:opacity-25 disabled:cursor-not-allowed"
                        @click="save">
                        Submit
                    </button>
                </div>
                <router-link :to="{ name: 'Setting' }">
                    <div class="text-sm rounded-lg text-blue-700 cursor-pointer hover:underline duration-500">
                        Previous Page
                    </div>
                </router-link>
            </div>
        </div>
        <!-- <Confirm v-if="saveStatus" desc="If you <confirm>, this category will be saved." @cancel="cancelPopup" @confirm="savePopup" /> -->
    </div>

</template>
 
<style scoped>
/* #background {
    background: linear-gradient(-45deg, #031B89,  #A9B5FF, #FAA7B7,#6196FF, #FB7A8E);
    background-size: 400% 400%;
    animation: gradient 15s ease infinite;
    height: 100vh;
}

@keyframes gradient {
    0% {
        background-position: 0% 50%;
    }

    50% {
        background-position: 100% 50%;
    }

    100% {
        background-position: 0% 50%;
    }
} */
</style>
 <!-- <button :disabled="checkDataBeforeSubmit"
            class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            @click="checkEditData">Submit</button> -->