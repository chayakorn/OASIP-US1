<script setup>
import { onBeforeMount, ref } from 'vue'
import Card from '../components/Card.vue'
import CreateEvent from '../components/CreateEvent.vue'

const eventCategories = ref([])
const getCategories = async () => {
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/eventcat`)
  if (res.status === 200) {
    eventCategories.value = await res.json()
  } else console.log('error, cannot get category')
}

onBeforeMount(async () => {
  await getCategories()
})
const createModalStatus = ref(false)
const cateToCreate = ref({})
const createToggle = (cate) => {
  createModalStatus.value = !createModalStatus.value
  cateToCreate.value = cate
}
</script>

<template>
  <div class="bg-image p-10">
    <div class="text-3xl font-semibold">
      Online Appointment Scheduling System for Integrated Project Clinics
      (OASIP)
    </div>
    <div class="grid grid-cols-5 gap-x-10">
      <Card
        v-for="cate in eventCategories"
        :cateItem="cate"
        @create="createToggle(cate)"
      />
      <CreateEvent
        v-show="createModalStatus"
        @closeCreate="createToggle(cateToCreate)"
        :category="cateToCreate"
      />
    </div>
  </div>
</template>

<style scoped>
.bg-image {
  background-image: url(../assets/images/home.png),
    linear-gradient(#f7f9fa, #dde0f2);
  background-repeat: no-repeat, no-repeat;
  background-size: auto 100%;
  background-position: bottom;
  background-attachment: fixed;
}
</style>
