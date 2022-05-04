<script setup>
import { ref } from 'vue';
import ConfirmDeleteEvent from './ConfirmDeleteEvent.vue';
import EventDetails from './EventDetails.vue'
const emit = defineEmits(['existChange'])
const props = defineProps({
    item: {
        type: Object,
        require: true
    },
    color: {
        type: String,
        require: true
    }
})

// const existChange = ref(true)
const showCancel = ref(false)

const date = ref('')
const time = ref('')
const description = ref('')

const clearData = ()=>{
    date.value = ''
    time.value = ''
    description.value = ''
    showCancel.value = false
    emit('existChange')
}


</script>
 
<template>
    <div>
        <div class="grid grid-cols-2 divide-x-2">
            <!-- col1 -->
            <div>
                <!-- CLINIC -->
                <div class="flex py-1 pl-4 pt-0.5">
                    <span class="p-2 font-semibold text-[#5E6366]">Clinic</span>
                    <div class="flex-none bg-[#F1F3F4] rounded-2xl w-4/5 py-2 px-4 text-[#C6CACC]">{{ item.categoryName
                    }}</div>

                </div>

                <!-- DATE -->
                <div class="flex pl-4 py-1">
                    <span class="grid place-items-center py-2 font-semibold px-2 pr-3.5 text-[#5E6366]">Date</span>
                    <input
                        class="flex-none border-[0.5px] border-[#5E6366] bg-[#F1F3F4] rounded-2xl w-4/5 py-1 px-4 text-black-700 focus:outline-none focus:bg-white focus:border-purple-500"
                        value="">
                    <br />
                </div>

                <!-- TIME -->
                <div class="grid grid-cols-2 divide-x-2">
                    <div class="flex pl-4 py-1">
                        <span class="font-semibold py-2 px-2 pr-3.5 text-[#5E6366]">Time</span>
                        <input
                            class="flex-none border-[0.5px] border-[#5E6366] bg-[#F1F3F4] rounded-2xl w-60 py-2 px-4 text-black-700 focus:outline-none focus:bg-white focus:border-purple-500"
                            value="">

                        <!-- DURATION -->
                        <div class="flex pl-4 float-right">
                            <span class="pt-2 px-2">
                                <svg class="" width="1.5em" height="1.5em" viewBox="0 0 24 24">
                                    <path fill="#5E6366"
                                        d="M12 20c4.4 0 8-3.6 8-8s-3.6-8-8-8s-8 3.6-8 8s3.6 8 8 8m0-18c5.5 0 10 4.5 10 10s-4.5 10-10 10S2 17.5 2 12S6.5 2 12 2m5 11.9l-.7 1.3l-5.3-2.9V7h1.5v4.4l4.5 2.5Z">
                                    </path>
                                </svg>
                            </span>
                            <div class="flex-none bg-[#F1F3F4] rounded-2xl w-36 py-2 px-4 text-[#C6CACC]">
                                {{ item.eventDuration }} mins
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- col2 -->
            <div>
                <!-- NAME -->
                <div class="flex py-1 pl-9">
                    <span class="text-[#5E6366] p-2 font-semibold">Name</span>
                    <div class="flex-none bg-[#F1F3F4] rounded-2xl w-10/12 py-1.5 px-4 text-[#C6CACC]">
                        {{ item.name }}
                    </div>
                </div>
                <!-- EMAIL -->
                <div class="flex py-1 pl-9">
                    <span class="text-[#5E6366] p-2 pr-3 font-semibold">Email</span>
                    <div class="flex-none bg-[#F1F3F4] appearance-none rounded-2xl w-10/12 py-2 px-4 text-[#C6CACC]">
                        {{ item.bookingEmail }}
                    </div>
                </div>
            </div>
        </div>

        <div class="pt-5 border-b-2 mx-5 text-[#5E6366]"></div>

        <!-- DESCRIPTION -->
        <div class="px-5 pt-1">
            <div class="p-2">
                <span class="text-[#5E6366] font-semibold "> Description </span>
            </div>
            <textarea maxlength="500"
                class="break-words resize-none border-[0.5px] border-[#5E6366] rounded-2xl bg-[#F1F3F4] w-full pt-2 h-32 px-5 text-black ">
            </textarea>

        </div>

        <!-- BUTTON -->
        <div class="grid grid-cols-2 pt-4">
            <!-- DELETE -->
            <div class=" justify-self-end px-4 " @click="showCancel = !showCancel">
                <button
                    class="flex place-items-center  bg-[#EA3D2F] rounded-[5px] text-white font-semibold w-36 h-12 px-1">
                    <span class="p-1 bg-white rounded-[5px]">
                        <svg width="2em" height="2em" viewBox="0 0 24 24">
                            <path fill="#EA3D2F"
                                d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z">
                            </path>
                        </svg>
                    </span><span class="px-3 text-lg">CANCEL</span>
                </button>
            </div>
            <!-- EDIT -->
            <div class="px-4" @click="">
                <button
                    class="flex place-items-center rounded-[5px] bg-[#2FA84F] text-white font-semibold w-36 h-12 px-1">
                    <span class="p-1 bg-white rounded-[5px]">
                        <svg width="2em" height="2em" viewBox="0 0 24 24">
                            <path fill="#2FA84F"
                                d="M9 16.17L5.53 12.7a.996.996 0 1 0-1.41 1.41l4.18 4.18c.39.39 1.02.39 1.41 0L20.29 7.71a.996.996 0 1 0-1.41-1.41L9 16.17z">
                            </path>
                        </svg>
                    </span><span class="px-6 text-lg">SAVE</span>
                </button>
            </div>
        </div>

        <div v-show="showCancel">
            <div
                class="bg-black/25 overflow-x-hidden overflow-y-auto absolute inset-0 z-40 outline-none focus:outline-none justify-center items-center flex">
                <div class="modal-container bg-white w-[30%] px-3 pt-1 pb-20 h-[28%] rounded-2xl z-50">
                    <div class="grid grid-cols-1 divide-y-[1px] px-1">
                        <div class="grid place-items-center font-bold text-xl pt-2 pb-2 ">Are you sure?</div>
                        <div class="grid place-items-center font-bold text-lg pt-7 pb-2">Do you want to exists?</div>
                    </div>
                    <div class="grid grid-cols-2 divided-x-0 pt-6">
                        <div class="justify-self-end px-3">
                            <button
                                class="bg-[#FEE4E2] font-semibold text-lg text-[#EA3D2F] rounded-[20px] py-3 px-[65px]"
                                @click="showCancel = !showCancel">CANCEL</button>
                        </div>
                        <div class="px-2">
                            <button class="bg-[#DCF7E3] font-semibold text-lg text-[#2FA84F] rounded-[20px] py-3 px-16"
                                @click="clearData">CONFIRM</button>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</template> 
    

 
<style>
</style>