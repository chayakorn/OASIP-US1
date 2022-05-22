<script setup>
import { computed, ref } from 'vue'
import Card from '../components/Card.vue'
import CreateEvent from '../components/CreateEvent.vue'
import Notice from '../components/Notice.vue'
import { useCategories } from '../stores/categories.js'

const myCategory = useCategories()
myCategory.getAllCategories()
const eventCategories = computed(() => useCategories().categoryLists)

const createModalStatus = ref(false)
const cateToCreate = ref({})
const createToggle = (cate) => {
  createModalStatus.value = !createModalStatus.value
  cateToCreate.value = cate
}
const noticeCreate = ref(false)
const closeNotice = () =>
  setTimeout(() => {
    noticeCreate.value = false
  }, 15)
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
        @notice="
          (notice) => {
            noticeCreate = notice
            closeNotice()
          }
        "
        @closeCreate="createToggle(cateToCreate)"
        :category="cateToCreate"
      />
    </div>
    <Notice
      v-show="noticeCreate"
      text="Successfully created the schedule !"
      color="#2FA84F"
    />
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
