<script setup>
import { computed, ref } from 'vue'
import Lists from '../components/Lists.vue'
import moment from 'moment'

const eventLists = ref([
  {
    id: 2,
    bookingName: 'Somsri Rakdee (SJ-3)',
    bookingEmail: 'somsri.rak@mail.kmutt.ac.th',
    eventStartTime: '2022-04-27T02:30:00Z',
    eventCategoryId: 1,
    eventDuration: 30,
    eventNotes: 'ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน',
    name: '',
    categoryName: 'Project Management Clinic'
  },
  {
    id: 3,
    bookingName: 'สมเกียรติ ขยันเรียน กลุ่ม TT-4',
    bookingEmail: 'somkiat.kay@kmutt.ac.th',
    eventStartTime: '2022-05-23T09:30:00Z',
    eventCategoryId: 3,
    eventDuration: 15,
    eventNotes: '',
    name: '',
    categoryName: 'Database Clinic'
  },
  {
    id: 4,
    bookingName: 'Somchai Jaidee (OR-7)',
    bookingEmail: 'somchai.jai@mail.kmutt.ac.th',
    eventStartTime: '2022-05-23T06:30:00Z',
    eventCategoryId: 2,
    eventDuration: 30,
    eventNotes: '',
    name: '',
    categoryName: 'DevOps/Infra Clinic'
  },
  {
    id: 2,
    bookingName: 'Somsri Rakdee (SJ-3)',
    bookingEmail: 'somsri.rak@mail.kmutt.ac.th',
    eventStartTime: '2022-04-27T03:30:00Z',
    eventCategoryId: 1,
    eventDuration: 30,
    eventNotes: 'ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน',
    name: '',
    categoryName: 'Project Management Clinic'
  },
  {
    id: 3,
    bookingName: 'สมเกียรติ ขยันเรียน กลุ่ม TT-4',
    bookingEmail: 'somkiat.kay@kmutt.ac.th',
    eventStartTime: '2022-05-23T09:30:00Z',
    eventCategoryId: 5,
    eventDuration: 15,
    eventNotes: '',
    name: '',
    categoryName: 'Database Clinic'
  },
  {
    id: 4,
    bookingName: 'Somchai Jaidee (OR-7)',
    bookingEmail: 'somchai.jai@mail.kmutt.ac.th',
    eventStartTime: '2022-05-23T06:30:00Z',
    eventCategoryId: 2,
    eventDuration: 30,
    eventNotes: '',
    name: '',
    categoryName: 'DevOps/Infra Clinic'
  }
])
// const eventLists = ref([])

const result = Object.values(
  eventLists.value.reduce((acc, x) => {
    acc[moment.utc(x.eventStartTime).format('DD MMM YYYY')] = [
      ...(acc[moment.utc(x.eventStartTime).format('DD MMM YYYY')] || []),
      x
    ]
    return acc
  }, {})
)
</script>

<template>
  <div class="p-10">
    <div class="text-4xl font-bold">Scheduled Events</div>
    <div class="my-5">
      <span class="text-2xl font-bold">All Events</span
      ><span class="ml-2 text-gray-400">{{ eventLists.length }} events</span>
    </div>
    <div
      v-if="!eventLists.length > 0"
      class="h-3/4 grid place-content-center text-[#C6CACC] font-bold text-4xl"
    >
      No Scheduled Events.
    </div>
    <div v-if="eventLists.length > 0">
      <!-- No Group -->
      <!-- <Lists
      :lists="
        eventLists.sort(
          (a, b) => new Date(b.eventStartTime) - new Date(a.eventStartTime)
        )
      "
    /> -->
      <!-- GroupBy Day -->
      <Lists
        :lists="
          result
            .sort(
              (a, b) =>
                new Date(b[0].eventStartTime) - new Date(a[0].eventStartTime)
            )
            .map((a) =>
              a.sort(
                (a, b) =>
                  new Date(b.eventStartTime) - new Date(a.eventStartTime)
              )
            )
        "
      />
    </div>
  </div>
</template>

<style></style>
