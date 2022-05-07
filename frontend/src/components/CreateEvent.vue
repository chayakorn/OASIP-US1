<script setup>
import moment from 'moment'
import { computed, ref } from 'vue'

const emit = defineEmits(['closeCreate'])
const props = defineProps({
  category: {
    type: Object,
    require: true
  }
})

const log = (event) => {
  if (event.target.id == 'backdrop') {
    emit('closeCreate', category)
  }
}

const borderStyle =
  'rounded-xl w-full py-2 px-3 mt-2 bg-[#F1F3F4] border border-[#5E6366] focus:outline-none focus:shadow-outline required:border-red-500'
const bookingName = ref('')
const name = ref('')
const email = ref('')
const note = ref('')
const date = ref('')
const showBookingName = computed(() => bookingName.value.length)
const showNote = computed(() => note.value.length)
const reset = () => {
  bookingName.value = ''
  email.value = ''
  date.value = ''
  note.value = ''
  name.value = ''
}

const creatingEvent = computed(() => ({
  bookingName: bookingName.value,
  bookingEmail: email.value,
  eventStartTime: new Date(date.value).toISOString(),
  eventEndTime: moment(date.value)
    .add(props.category.eventDuration, 'minutes')
    .toISOString(),
  eventCategoryId: { id: props.category.id },
  eventDuration: props.category.eventDuration,
  eventNotes: note.value,
  name: name.value,
  categoryName: props.category.eventCategoryName
}))

// CREATE
const createEvent = async (newEvent) => {
  console.log(newEvent)
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/event`, {
    method: 'POST',
    headers: { 'content-type': 'application/json' },
    body: JSON.stringify(newEvent)
  })
  if (res.status === 201) {
    console.log('created successfully')
  } else console.log('error, cannot create')
}
</script>

<template>
  <div
    id="backdrop"
    @click="log"
    class="bg-black/25 overflow-x-hidden overflow-y-auto absolute inset-0 z-40 outline-none focus:outline-none justify-center items-center flex"
  >
    <div class="relative bg-white w-10/12 h-[80%] rounded-3xl">
      <div
        @click="emit('closeCreate', false)"
        class="cursor-pointer absolute right-0 hover:bg-[#F1F3F4] rounded-lg m-4"
      >
        <svg width="2em" height="2em" viewBox="0 0 24 24">
          <path
            fill="currentColor"
            d="M18.3 5.71a.996.996 0 0 0-1.41 0L12 10.59L7.11 5.7A.996.996 0 1 0 5.7 7.11L10.59 12L5.7 16.89a.996.996 0 1 0 1.41 1.41L12 13.41l4.89 4.89a.996.996 0 1 0 1.41-1.41L13.41 12l4.89-4.89c.38-.38.38-1.02 0-1.4z"
          ></path>
        </svg>
      </div>
      <div class="h-full mx-14">
        <div class="font-bold text-2xl text-center pt-5">
          Create new appointmenet
        </div>
        <div class="grid grid-cols-5 pt-5">
          <div class="col-span-2">
            <div class="relative mb-5">
              <div class="font-medium">Booking Name | Subject</div>
              <input
                :class="[borderStyle]"
                type="text"
                maxlength="100"
                placeholder="Enter your booking name . . ."
                v-model="bookingName"
              />
              <div class="absolute right-0 top-4 text-xs text-gray-500">
                {{ showBookingName }}/100
              </div>
            </div>
            <div class="mb-5">
              <div class="font-medium">Category</div>
              <div
                class="rounded-xl w-full py-2 px-3 mt-2 bg-[#F1F3F4] focus:outline-none focus:shadow-outline"
              >
                {{ category.eventCategoryName }}
              </div>
            </div>
            <div class="mb-5">
              <div class="font-medium">Name</div>
              <input
                :class="[borderStyle]"
                type="text"
                placeholder="Firstname Lastname"
                v-model="name"
              />
            </div>
            <div class="mb-5">
              <div class="font-medium">Email</div>
              <input
                :class="[borderStyle, 'peer']"
                type="email"
                placeholder="username@example.com"
                v-model="email"
              />
              <p
                class="mt-2 invisible peer-invalid:visible text-pink-600 text-sm"
              >
                Please provide a valid email address.
              </p>
            </div>
          </div>
          <div
            class="col-span-3 bg-[#F7F9FA] ml-10 rounded-2xl grid grid-cols-2"
          >
            <div class="grid place-content-center">
              <v-date-picker
                color="blue"
                mode="datetime"
                v-model="date"
                timezone=""
                :min-date="new Date()"
              />
            </div>
            <div class="grid grid-rows-3 my-10 ml-5">
              <div>
                <div class="font-medium">Date :</div>
                <div
                  class="bg-white h-1/2 w-5/6 rounded-xl mt-2 grid content-center pl-4"
                >
                  {{ date ? moment(date).format('DD MMMM YYYY') : '' }}
                </div>
              </div>
              <div>
                <div class="font-medium">Time :</div>
                <div
                  class="bg-white h-1/2 w-5/6 rounded-xl mt-2 grid content-center pl-4"
                >
                  {{ date ? moment(date).format('h : mm A') : '' }}
                </div>
              </div>
              <div>
                <div class="font-medium">
                  Duratione:
                  <span class="text-red-500">{{ category.eventDuration }}</span>
                  minutes
                </div>
                <span class="text-sm text-gray-400"
                  >* Duration depends on category.</span
                >
              </div>
            </div>
          </div>
        </div>
        <div class="grid grid-cols-3">
          <div class="relative col-span-2">
            <div class="font-medium">Description | Note</div>
            <textarea
              maxlength="500"
              class="break-words resize-none rounded-xl w-full h-28 py-2 px-3 mt-2 bg-[#F1F3F4] border border-[#5E6366] focus:outline-none focus:shadow-outline"
              placeholder="Enter your description . . ."
              v-model="note"
            />
            <div class="absolute right-0 top-4 text-xs text-gray-500">
              {{ showNote }}/500
            </div>
          </div>
          <div class="ml-10 mb-1 relative">
            <div class="grid grid-cols-2 gap-x-10 absolute inset-x-0 bottom-0">
              <div
                class="bg-[#EA3D2F] cursor-pointer h-10 drop-shadow-lg rounded-md"
                @click="reset(), emit('closeCreate')"
              >
                <div class="flex">
                  <div
                    class="bg-white m-1 h-8 w-1/3 rounded grid place-items-center text-[#EA3D2F]"
                  >
                    <svg width="1.5em" height="1.5em" viewBox="0 0 24 24">
                      <path
                        fill="currentColor"
                        d="M12 1.75a3.25 3.25 0 0 1 3.245 3.066L15.25 5h5.25a.75.75 0 0 1 .102 1.493L20.5 6.5h-.796l-1.28 13.02a2.75 2.75 0 0 1-2.561 2.474l-.176.006H8.313a2.75 2.75 0 0 1-2.714-2.307l-.023-.174L4.295 6.5H3.5a.75.75 0 0 1-.743-.648L2.75 5.75a.75.75 0 0 1 .648-.743L3.5 5h5.25A3.25 3.25 0 0 1 12 1.75Zm6.197 4.75H5.802l1.267 12.872a1.25 1.25 0 0 0 1.117 1.122l.127.006h7.374c.6 0 1.109-.425 1.225-1.002l.02-.126L18.196 6.5ZM13.75 9.25a.75.75 0 0 1 .743.648L14.5 10v7a.75.75 0 0 1-1.493.102L13 17v-7a.75.75 0 0 1 .75-.75Zm-3.5 0a.75.75 0 0 1 .743.648L11 10v7a.75.75 0 0 1-1.493.102L9.5 17v-7a.75.75 0 0 1 .75-.75Zm1.75-6a1.75 1.75 0 0 0-1.744 1.606L10.25 5h3.5A1.75 1.75 0 0 0 12 3.25Z"
                      ></path>
                    </svg>
                  </div>
                  <div class="grid place-items-center w-full text-white">
                    CANCEL
                  </div>
                </div>
              </div>
              <div
                class="bg-[#2FA84F] cursor-pointer h-10 drop-shadow-lg rounded-md"
                @click="reset()"
              >
                <div class="flex">
                  <div
                    class="bg-white m-1 h-8 w-1/3 rounded grid place-items-center text-[#2FA84F]"
                  >
                    <svg width="1.5em" height="1.5em" viewBox="0 0 24 24">
                      <path
                        fill="currentColor"
                        d="M12 1.75a3.25 3.25 0 0 1 3.245 3.066L15.25 5h5.25a.75.75 0 0 1 .102 1.493L20.5 6.5h-.796l-1.28 13.02a2.75 2.75 0 0 1-2.561 2.474l-.176.006H8.313a2.75 2.75 0 0 1-2.714-2.307l-.023-.174L4.295 6.5H3.5a.75.75 0 0 1-.743-.648L2.75 5.75a.75.75 0 0 1 .648-.743L3.5 5h5.25A3.25 3.25 0 0 1 12 1.75Zm6.197 4.75H5.802l1.267 12.872a1.25 1.25 0 0 0 1.117 1.122l.127.006h7.374c.6 0 1.109-.425 1.225-1.002l.02-.126L18.196 6.5ZM13.75 9.25a.75.75 0 0 1 .743.648L14.5 10v7a.75.75 0 0 1-1.493.102L13 17v-7a.75.75 0 0 1 .75-.75Zm-3.5 0a.75.75 0 0 1 .743.648L11 10v7a.75.75 0 0 1-1.493.102L9.5 17v-7a.75.75 0 0 1 .75-.75Zm1.75-6a1.75 1.75 0 0 0-1.744 1.606L10.25 5h3.5A1.75 1.75 0 0 0 12 3.25Z"
                      ></path>
                    </svg>
                  </div>
                  <div
                    class="grid place-items-center w-full text-white"
                    @click="
                      createEvent(creatingEvent), reset(), emit('closeCreate')
                    "
                  >
                    CREATE
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
