<script setup>
defineProps({
  text: {
    type: String,
    default: 'OK'
  },
  imagePath: {
    type: String,
    default: 'OK'
  },
  classCSS: {
    type: String,
    default: ''
  }
})
</script>

<template>
  <div
    class="bg-black/25 overflow-x-hidden overflow-y-auto absolute inset-0 z-40 outline-none focus:outline-none"
  >
    <div class="absolute inset-x-0 top-0 flex justify-center">
      <div :class="[classCSS]"><div class="flex"><img :src="imagePath" />{{ text }}</div></div>
    </div>
  </div>
</template>

<style></style>
