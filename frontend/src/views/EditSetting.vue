<script setup>
import { ref, computed, onBeforeMount } from 'vue';
import { useRoute } from 'vue-router'
import { useCategories } from '../stores/categories.js';


const myCategory = ref({ "eventCategoryName": "", "eventCategoryDescription": "", "eventDuration": 0 })
const useCategory = useCategories()
useCategory.getAllCategories()
const categoryLists = computed(() => useCategory.categoryLists)
onBeforeMount(useCategories().getCategoryById(useRoute().params.category).then((e) => myCategory.value = e))

const status = ref()
const checkDataBeforeSubmit = computed(() => !myCategory.value.eventCategoryName.length > 0 || !myCategory.value.eventDuration > 0 ? true : false)

const checkEditData = () => {
    categoryLists.value.forEach(e => {
        if (e.eventCategoryName.trim().toLowerCase() === myCategory.value.eventCategoryName.trim().toLowerCase()) {
            status.value = true
        }
    })
    if (status.value) {
        console.log('This name has been used');
        status.value = false
    }
    else
        console.log('mai sum')
}

const checkName = computed(() => categoryLists.value.forEach(e => {
    e.eventCategoryName.trim().toLowerCase() === myCategory.value.eventCategoryName.trim().toLowerCase() ? true : false
    // console.log(e.eventCategoryName)
    // console.log(myCategory.value.eventCategoryName)
    console.log(checkName)
}
))

// console.log(checkName)

const updateCategory = (cate, id) => {
    useCategory.editCategory({
        eventCategoryName: myCategory.value.eventCategoryName,
        eventCategoryDescription: myCategory.value.eventCategoryDescription,
        eventDuration: myCategory.value.eventDuration
    }, myCategory.value.id)
}

</script>
 
<template>
    <div class="h-screen p-10">
        <div class="text-4xl font-bold">
            Editing Event Categories
        </div>

        <div class="bg-yellow-400 h-screen overflow-hidden flex items-center justify-center">
            <div class="p-8 ">
                <input type="text" class="border-lg bg-grey-700" v-model="myCategory.eventCategoryName" required
                    maxlength="100" @keypress="checkName" />
                <div v-if="myCategory.eventCategoryName.length >= 100" class="absolute text-xs text-red-500">
                    The number of characters has a limit of 100 characters. If it
                    exceeds 100, it will not be able to continue typing.</div>
                <div v-if="checkName" class="text-red-500 text-xs">This category name has been used</div>
            </div>

            <div class="p-8">
                <textarea maxlength='500' class="resize-none border-lg bg-grey-700 w-96 h-36"
                    v-model="myCategory.eventCategoryDescription" :placeholder="myCategory.eventCategoryDescription" />
                <div v-if="myCategory.eventCategoryDescription.length >= 500" class="absolute text-xs text-red-500">
                    The number of characters has a limit of 500 characters. If it
                    exceeds 500, it will not be able to continue typing.
                </div>
            </div>

            <div class="p-8">
                <input type="number" class="border-lg bg-grey-700" v-model="myCategory.eventDuration"
                    :placeholder="myCategory.eventDuration" min="1" max="480" />
            </div>
            <div v-if="myCategory.eventDuration >= 480" class="absolute text-xs text-red-500">
                The range of number must be 1-480
                The number of characters has a limit of 500 characters. If it
                exceeds 500, it will not be able to continue typing.
            </div>
        </div>
        <button :disabled="checkDataBeforeSubmit"
            class="text-white bg-blue-700 hover:bg-blue-800  font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center  dark:hover:bg-blue-700 disabled:opacity-25 disabled:cursor-not-allowed"
            @click="checkEditData">
            Submit</button>
        <!-- </form> -->

    </div>
</template>
 
<style>
</style>
 <!-- <button :disabled="checkDataBeforeSubmit"
            class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            @click="checkEditData">Submit</button> -->