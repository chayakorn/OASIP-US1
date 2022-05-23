<script setup>
import { useRouter } from 'vue-router'
const myRouter = useRouter()
const closeModal = () => myRouter.push({ name: 'Home' })
</script>

<template>
  <div class="modal-mask grid place-content-center">
    <div
      class="content flex items-center bg-white gap-x-10 p-10 drop-shadow-xl rounded-sm"
    >
      <img src="../assets/images/icon.png" width="256" class="pt-7" />
      <div class="pr-6 grid gap-y-2">
        <div class="text-6xl font-bold">404</div>
        <div class="font-semibold">Page Not Found</div>
        <div class="text-sm font-light">
          Woops. Looks like this page doesn't exist.
        </div>
      </div>
    </div>
    <div
      @click="closeModal"
      class="mt-16 animate-bounce flex gap-x-2 text-lg font-semibold text-blue-700 cursor-pointer hover:underline justify-self-center"
    >
      <svg width="1.5em" height="1.5em" viewBox="0 0 24 24">
        <path
          fill="currentColor"
          d="M20 11H7.83l5.59-5.59L12 4l-8 8l8 8l1.41-1.41L7.83 13H20v-2z"
        ></path>
      </svg>
      <div>Back to homepage</div>
    </div>
  </div>
</template>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #f7f9fa;
}
.content {
  box-shadow: 20px 20px gray;
}
</style>
