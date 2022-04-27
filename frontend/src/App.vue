<script setup></script>

<template>
  <div class="flex">
    <div class="w-1/12">
      <div class="absolute w-1/12 mt-5 grid place-content-center">
        <img src="./assets/images/pin.png" width="60" />
      </div>
      <div class="h-full grid place-content-center space-y-10">
        <!-- Home -->
        <router-link v-show="false" :to="{ name: 'Home' }"
          ><svg width="3em" height="3em" viewBox="0 0 48 48">
            <path
              fill="currentColor"
              d="M21.606 5.855a3.778 3.778 0 0 1 4.788 0l13.175 10.742A3.91 3.91 0 0 1 41 19.628v19.485C41 41.26 39.288 43 37.175 43h-5.1c-2.113 0-3.825-1.74-3.825-3.887V29.75c0-.715-.57-1.295-1.275-1.295h-5.95c-.704 0-1.275.58-1.275 1.295v9.364c0 2.147-1.712 3.887-3.825 3.887h-5.1C8.713 43 7 41.26 7 39.113V19.628a3.91 3.91 0 0 1 1.431-3.031L21.606 5.855Z"
            ></path></svg
        ></router-link>
        <!-- Event Lists -->
        <router-link :to="{ name: 'EventLists' }"
          ><svg width="3em" height="3em" viewBox="0 0 24 24">
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              d="M6 3a3 3 0 0 0-3 3v2a3 3 0 0 0 3 3h2a3 3 0 0 0 3-3V6a3 3 0 0 0-3-3H6zm0 10a3 3 0 0 0-3 3v2a3 3 0 0 0 3 3h2a3 3 0 0 0 3-3v-2a3 3 0 0 0-3-3H6zm10 0a3 3 0 0 0-3 3v2a3 3 0 0 0 3 3h2a3 3 0 0 0 3-3v-2a3 3 0 0 0-3-3h-2zm0-10a3 3 0 0 0-3 3v2a3 3 0 0 0 3 3h2a3 3 0 0 0 3-3V6a3 3 0 0 0-3-3h-2z"
              fill="currentColor"
            ></path></svg
        ></router-link>
        <!-- Calendar -->
        <router-link v-show="false" :to="{ name: 'Calendar' }"
          ><svg width="3em" height="3em" viewBox="0 0 512 512">
            <path
              fill="currentColor"
              d="M32 456a24 24 0 0 0 24 24h400a24 24 0 0 0 24-24V176H32Zm320-244a4 4 0 0 1 4-4h40a4 4 0 0 1 4 4v40a4 4 0 0 1-4 4h-40a4 4 0 0 1-4-4Zm0 80a4 4 0 0 1 4-4h40a4 4 0 0 1 4 4v40a4 4 0 0 1-4 4h-40a4 4 0 0 1-4-4Zm-80-80a4 4 0 0 1 4-4h40a4 4 0 0 1 4 4v40a4 4 0 0 1-4 4h-40a4 4 0 0 1-4-4Zm0 80a4 4 0 0 1 4-4h40a4 4 0 0 1 4 4v40a4 4 0 0 1-4 4h-40a4 4 0 0 1-4-4Zm0 80a4 4 0 0 1 4-4h40a4 4 0 0 1 4 4v40a4 4 0 0 1-4 4h-40a4 4 0 0 1-4-4Zm-80-80a4 4 0 0 1 4-4h40a4 4 0 0 1 4 4v40a4 4 0 0 1-4 4h-40a4 4 0 0 1-4-4Zm0 80a4 4 0 0 1 4-4h40a4 4 0 0 1 4 4v40a4 4 0 0 1-4 4h-40a4 4 0 0 1-4-4Zm-80-80a4 4 0 0 1 4-4h40a4 4 0 0 1 4 4v40a4 4 0 0 1-4 4h-40a4 4 0 0 1-4-4Zm0 80a4 4 0 0 1 4-4h40a4 4 0 0 1 4 4v40a4 4 0 0 1-4 4h-40a4 4 0 0 1-4-4ZM456 64h-55.92V32h-48v32H159.92V32h-48v32H56a23.8 23.8 0 0 0-24 23.77V144h448V87.77A23.8 23.8 0 0 0 456 64Z"
            ></path></svg
        ></router-link>
        <!-- Setting -->
        <router-link v-show="false" :to="{ name: 'Setting' }"
          ><svg width="3em" height="3em" viewBox="0 0 24 24">
            <path
              fill="currentColor"
              d="M9.954 2.21a9.99 9.99 0 0 1 4.091-.002A3.993 3.993 0 0 0 16 5.07a3.993 3.993 0 0 0 3.457.261A9.99 9.99 0 0 1 21.5 8.876A3.993 3.993 0 0 0 20 12a3.99 3.99 0 0 0 1.502 3.124a10.043 10.043 0 0 1-2.046 3.543a3.993 3.993 0 0 0-3.456.261a3.993 3.993 0 0 0-1.954 2.86a9.99 9.99 0 0 1-4.091.004A3.993 3.993 0 0 0 8 18.927a3.993 3.993 0 0 0-3.457-.26A9.99 9.99 0 0 1 2.5 15.121A3.993 3.993 0 0 0 4 11.999a3.993 3.993 0 0 0-1.502-3.124a10.043 10.043 0 0 1 2.046-3.543A3.993 3.993 0 0 0 8 5.071a3.993 3.993 0 0 0 1.954-2.86zM12 15a3 3 0 1 0 0-6a3 3 0 0 0 0 6z"
            ></path></svg
        ></router-link>
      </div>
    </div>
    <div class="w-full">
      <router-view
        :class="[
          'font-poppin h-screen',
          this.$route.name == 'Home' ? '' : 'bg-[#F7F9FA]'
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

.font-poppin {
  font-family: 'Poppins', 'Prompt', sans-serif;
}
.font-pragati {
  font-family: 'Pragati Narrow', sans-serif;
}

.bg-home {
  background-image: linear-gradient(#f7f9fa, #dde0f2);
}

.detail {
  color: #367bf5;
  text-decoration: none;
}
</style>
