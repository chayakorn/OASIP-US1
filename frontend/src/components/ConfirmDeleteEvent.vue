<script setup>
const log = (event) => {
  if (event.target.id == 'modal') {
    emit('closeModal', false)
  }
}
</script>

<template>
  <div>
    <div
      class="bg-black/25 overflow-x-hidden overflow-y-auto absolute inset-0 z-40 outline-none focus:outline-none justify-center items-center flex"
      @click="log"
      id="modal"
    >
      <div
        class="modal-container bg-white w-[30%] px-3 pt-1 pb-20 h-[28%] rounded-2xl z-50"
      >
        <div class="grid grid-cols-1 divide-y-[1px] px-1">
          <div class="grid place-items-center font-bold text-xl pt-2 pb-2">
            Are you sure?
          </div>
          <div class="grid place-items-center font-bold text-lg pt-7 pb-2">
            Do you want to delete this schedule?
          </div>
        </div>
        <div class="grid grid-cols-2 divided-x-0 pt-6">
          <div class="justify-self-end px-3">
            <button
              class="bg-[#FEE4E2] font-semibold text-lg text-[#EA3D2F] rounded-[20px] py-3 px-[65px]"
            >
              CANCEL
            </button>
          </div>
          <div class="px-2">
            <button
              class="bg-[#DCF7E3] font-semibold text-lg text-[#2FA84F] rounded-[20px] py-3 px-16"
            >
              CONFIRM
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
