<script setup>
import { onBeforeMount, ref } from 'vue'
import Card from '../components/Card.vue'
import CreateEvent from '../components/CreateEvent.vue'
import Notice from '../components/Notice.vue'

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
    <div>
      <!-- <input
        :class="[
          borderStyle,
          'peer peer-invalid:border-2 peer-invalid:border-red-500'
        ]"
        type="text"
        required
        placeholder="username@example.com"
      /> -->
      <!-- <p class="mt-2 invisible peer-invalid:visible text-pink-600 text-sm">
        Please provide a valid email address.
      </p> -->
    </div>
    <!-- <Notice
      v-show="noticeCreate"
      text="Successfully created the schedule !"
      classCSS="mt-10 bg-[#2FA84F] text-white text-xl grid place-content-center w-1/3 h-16 rounded-full"
    /> -->
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

/* .validation {
  opacity: 0;
  font-size: 12px;
  font-family: sans-serif;
  color: crimson;
  transition: opacity;
}

input:required:valid {
  border-color: forestgreen;
}

input:required:invalid {
  border-color: crimson;
}

input:required:invalid:not(:placeholder-shown) + .validation {
  opacity: 1;
} */
</style>
