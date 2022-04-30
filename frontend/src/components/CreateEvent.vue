<script setup>
const emit = defineEmits(['closeCreate'])
defineProps({
  cateName: {
    type: String,
    require: true
  }
})
const log = (event) => {
  if (event.target.id == 'backdrop') {
    emit('closeCreate', false)
  }
}
const borderStyle =
  'rounded-xl w-full py-2 px-3 mt-2 mb-5 bg-[#F1F3F4] border border-[#5E6366] focus:outline-none focus:shadow-outline'
</script>

<template>
  <div
    id="backdrop"
    @click="log"
    class="bg-black/25 overflow-x-hidden overflow-y-auto absolute inset-0 z-40 outline-none focus:outline-none justify-center items-center flex"
  >
    <div class="relative bg-white w-10/12 h-[80%] rounded-3xl">
      <div
        @click="emit('closeCreate', false)"
        class="cursor-pointer absolute right-0 hover:bg-[#F1F3F4] rounded-lg m-4"
      >
        <svg width="2em" height="2em" viewBox="0 0 24 24">
          <path
            fill="currentColor"
            d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z"
          ></path>
        </svg>
      </div>
      <div class="h-full mx-14">
        <div class="font-bold text-2xl text-center pt-5">
          Create new appointmenet
        </div>
        <div class="grid grid-cols-5 pt-5">
          <div class="col-span-2">
            <div class="">
              <div class="font-medium">Booking Name | Subject</div>
              <input
                :class="[borderStyle]"
                type="text"
                maxlength="100"
                placeholder="Enter your booking name . . ."
              />
            </div>
            <div>
              <div class="font-medium">Category</div>
              <div
                class="rounded-xl w-full py-2 px-3 mt-2 mb-5 bg-[#F1F3F4] focus:outline-none focus:shadow-outline"
              >
                {{ cateName }}
              </div>
            </div>
            <div>
              <div class="font-medium">Name</div>
              <input
                :class="[borderStyle]"
                type="text"
                placeholder="Firstname Lastname"
              />
            </div>
            <div>
              <div class="font-medium">Email</div>
              <input
                :class="[borderStyle]"
                type="text"
                placeholder="username@example.com"
              />
            </div>
          </div>
          <div
            class="col-span-3 bg-[#F7F9FA] ml-10 rounded-2xl grid grid-cols-2"
          >
            <div></div>
            <div></div>
          </div>
        </div>
        <div class="flex">
          <div class="w-9/12">
            <div class="font-medium">Description | Note</div>
            <!-- <input
              class="break-words rounded-xl w-full h-40 py-2 px-3 mt-2 mb-5 bg-[#F1F3F4] border border-[#5E6366] focus:outline-none focus:shadow-outline"
              type=""
              placeholder="Enter your description . . ."
            /> -->
            <textarea
              maxlength="500"
              class="break-words resize-none rounded-xl w-full h-5/6 py-2 px-3 mt-2 mb-5 bg-[#F1F3F4] border border-[#5E6366] focus:outline-none focus:shadow-outline"
              placeholder="Enter your description . . ."
              v-model="note"
            ></textarea>
          </div>
          <div>CANCEL</div>
          <div>CREATE</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
