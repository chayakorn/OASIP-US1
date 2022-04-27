<script setup>
import { ref } from 'vue'
import Card from '../components/Card.vue'

const eventCategories = ref([
  {
    id: 1,
    eventCategoryName: 'Project Management Clinic',
    eventCategoryDescription:
      'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดงระหว่างขอคำปรึกษา',
    eventDuration: 30,
    image: './public/images/project.png'
  },
  {
    id: 2,
    eventCategoryName: 'DevOps/Infra Clinic',
    eventCategoryDescription:
      'Use this event category for DevOps/Infra clinic.',
    eventDuration: 20,
    image: './public/images/devops.png'
  },
  {
    id: 3,
    eventCategoryName: 'Database Clinic',
    eventCategoryDescription:
      'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I',
    eventDuration: 15,
    image: './public/images/database.png'
  },
  {
    id: 4,
    eventCategoryName: 'Client-side Clinic',
    eventCategoryDescription:
      'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I',
    eventDuration: 30,
    image: './public/images/frontend.png'
  },
  {
    id: 5,
    eventCategoryName: 'Server-side Clinic',
    eventCategoryDescription: '',
    eventDuration: 30,
    image: './public/images/backend.png'
  }
])
</script>

<template>
  <div class="bg-image p-10">
    <div class="text-3xl font-semibold">
      Online Appointment Scheduling System for Integrated Project Clinics
      (OASIP)
    </div>
    <div class="grid grid-cols-5 gap-x-10">
      <Card v-for="cate in eventCategories" :cateItem="cate" />
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
