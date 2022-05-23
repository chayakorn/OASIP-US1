<script setup>
import { useClock } from './stores/clock.js'
const myClock = useClock()
</script>

<template>
  <div class="flex">
    <div class="relative w-1/12">
      <div class="absolute w-full mt-5 pr-2 grid justify-items-center">
        <router-link :to="{ name: 'Home' }"
          ><img src="./assets/images/icon.png" width="90"
        /></router-link>
      </div>
      <!-- clock -->
      <div class="font-plus-jarkata absolute top-24 text-center">
        {{ myClock.clock }}
      </div>

      <div class="h-full grid place-content-center space-y-10">
        <!-- Home -->
        <router-link :to="{ name: 'Home' }" class="hover:scale-110 duration-700"
          ><svg width="3em" height="3em" viewBox="0 0 48 48">
            <path
              fill="currentColor"
              d="M21.606 5.855a3.778 3.778 0 0 1 4.788 0l13.175 10.742A3.91 3.91 0 0 1 41 19.628v19.485C41 41.26 39.288 43 37.175 43h-5.1c-2.113 0-3.825-1.74-3.825-3.887V29.75c0-.715-.57-1.295-1.275-1.295h-5.95c-.704 0-1.275.58-1.275 1.295v9.364c0 2.147-1.712 3.887-3.825 3.887h-5.1C8.713 43 7 41.26 7 39.113V19.628a3.91 3.91 0 0 1 1.431-3.031L21.606 5.855Z"
            ></path></svg
        ></router-link>

        <!-- Event Lists -->
        <router-link
          :to="{ name: 'EventLists' }"
          class="hover:scale-110 duration-700"
          ><svg width="3em" height="3em" viewBox="0 0 24 24">
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              d="M6 3a3 3 0 0 0-3 3v2a3 3 0 0 0 3 3h2a3 3 0 0 0 3-3V6a3 3 0 0 0-3-3H6zm0 10a3 3 0 0 0-3 3v2a3 3 0 0 0 3 3h2a3 3 0 0 0 3-3v-2a3 3 0 0 0-3-3H6zm10 0a3 3 0 0 0-3 3v2a3 3 0 0 0 3 3h2a3 3 0 0 0 3-3v-2a3 3 0 0 0-3-3h-2zm0-10a3 3 0 0 0-3 3v2a3 3 0 0 0 3 3h2a3 3 0 0 0 3-3V6a3 3 0 0 0-3-3h-2z"
              fill="currentColor"
            ></path></svg
        ></router-link>
        <!-- Setting -->
        <router-link
          :to="{ name: 'Setting' }"
          :style="$route.path.includes('setting') ? 'color: #031b89;' : ''"
          class="hover:scale-110 duration-700"
          ><svg width="3em" height="3em" viewBox="0 0 24 24">
            <path
              fill="currentColor"
              d="M9.954 2.21a9.99 9.99 0 0 1 4.091-.002A3.993 3.993 0 0 0 16 5.07a3.993 3.993 0 0 0 3.457.261A9.99 9.99 0 0 1 21.5 8.876A3.993 3.993 0 0 0 20 12a3.99 3.99 0 0 0 1.502 3.124a10.043 10.043 0 0 1-2.046 3.543a3.993 3.993 0 0 0-3.456.261a3.993 3.993 0 0 0-1.954 2.86a9.99 9.99 0 0 1-4.091.004A3.993 3.993 0 0 0 8 18.927a3.993 3.993 0 0 0-3.457-.26A9.99 9.99 0 0 1 2.5 15.121A3.993 3.993 0 0 0 4 11.999a3.993 3.993 0 0 0-1.502-3.124a10.043 10.043 0 0 1 2.046-3.543A3.993 3.993 0 0 0 8 5.071a3.993 3.993 0 0 0 1.954-2.86zM12 15a3 3 0 1 0 0-6a3 3 0 0 0 0 6z"
            ></path></svg
        ></router-link>
      </div>
    </div>
    <div
      class="absolute w-1/12 bottom-5 -ml-1 mt-5 grid place-content-center hover:scale-110 duration-700"
    >
      <router-link :to="{ name: 'DevelopBy' }"
        ><svg width="2.5em" height="2.5em" viewBox="0 0 32 32">
          <path
            fill="currentColor"
            d="M8 4v4H4V4h4M2 2v8h8V2zm16 5v4h-4V7h4m-6-2v8h8V5zM8 16v4H4v-4h4m-6-2v8h8v-8z"
          ></path>
          <path
            fill="currentColor"
            d="M22 10v6h-6v6h-6v8h20V10Zm-4 8h4v4h-4Zm-2 10h-4v-4h4Zm6 0h-4v-4h4Zm6 0h-4v-4h4Zm0-6h-4v-4h4Zm-4-6v-4h4v4Z"
          ></path></svg
      ></router-link>
    </div>
    <div class="w-full">
      <router-view
        :class="[
          'font-poppin h-screen',
          $route.name == 'Home' ? '' : 'bg-[#F7F9FA]'
        ]"
      ></router-view>
    </div>
  </div>
</template>

<style scoped>
a {
  color: #cbd8f2;
}
.router-link-active {
  color: #031b89;
}
</style>
<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Prompt:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Pragati+Narrow:wght@400;700&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;1,200;1,300;1,400;1,500;1,600;1,700;1,800&display=swap');

.font-poppin {
  font-family: 'Poppins', 'Prompt', sans-serif;
}
.font-pragati {
  font-family: 'Pragati Narrow', sans-serif;
}
.font-plus-jarkata {
  font-family: 'Plus Jakarta Sans', sans-serif;
}

.bg-home {
  background-image: linear-gradient(#f7f9fa, #dde0f2);
}

.detail {
  color: #367bf5;
  text-decoration: none;
}

/* width */
::-webkit-scrollbar {
  width: 10px;
}

/* Track 
::-webkit-scrollbar-track {
  box-shadow: inset 0 0 2px grey;
  border-radius: 10px;
}*/

/* Handle */
::-webkit-scrollbar-thumb {
  background: gray;
  border-radius: 10px;
}
</style>
