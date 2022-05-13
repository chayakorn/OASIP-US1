<script setup>
import { ref } from 'vue'
import DateTime from '../components/DateTime.vue'

const eventCategories = ref([])
const getCategories = async () => {
  const res = await fetch(`http://intproj21.sit.kmutt.ac.th/us1/api/eventcat`)
  if (res.status === 200) {
    eventCategories.value = await res.json()
  } else console.log('error, cannot get category')
}

getCategories()

const eventLists = ref([])
const getAllEvents = async () => {
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/event`)
  if (res.status === 200) {
    eventLists.value = await res.json()
  } else console.log('error, cannot get events')
}

getAllEvents()
</script>

<template>
  <div><DateTime :events="eventLists" /></div>
</template>

<style></style>
