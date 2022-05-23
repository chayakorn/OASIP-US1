<script setup>
defineEmits(['cancel', 'confirm'])
defineProps({
  question: {
    type: String,
    default: 'Are you sure'
  },
  underline: {
    type: String,
    default: ''
  },
  color: {
    type: String,
    default: '000000'
  },
  desc: {
    type: String,
    default: 'If you "confirm", this process will centinue.'
  }
})
</script>

<template>
  <div>
    <div
      class="bg-black/25 overflow-x-hidden overflow-y-auto absolute inset-0 z-40 outline-none focus:outline-none justify-center items-center flex"
      @click="log"
      id="modal"
    >
      <div
        class="w-[30%] h-[27.5%] p-[1%] bg-white grid place-items-center grid-rows-3 rounded-2xl gap-y-5"
      >
        <div
          class="flex justify-center w-full h-full border-b font-bold text-xl"
        >
          {{ question }}&nbsp;
          <span :class="`underline text-[#${color}]`">{{ underline }}</span
          >&nbsp;?
        </div>
        <div
          class="grid content-center w-full h-full text-center text-gray-500"
        >
          {{ desc }}
        </div>
        <div class="grid grid-cols-2 w-full h-full gap-x-[5%]">
          <button
            @click="$emit('cancel', false)"
            class="bg-[#FEE4E2] font-semibold text-lg text-[#EA3D2F] rounded-[20px] hover:scale-95 hover:shadow-inner"
          >
            CANCEL
          </button>
          <button
            @click="$emit('confirm', true)"
            class="bg-[#DCF7E3] font-semibold text-lg text-[#2FA84F] rounded-[20px] hover:scale-95 hover:shadow-inner"
          >
            CONFIRM
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
