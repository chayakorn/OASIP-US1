<script setup>
defineProps({
  name: {
    type: String,
    default: ''
  },
  id: {
    type: String,
    default: ''
  },
  photo: {
    type: String,
    default: ''
  },
  git: {
    type: String,
    default: ''
  },
  team: {
    type: String,
    default: ''
  }
})
</script>

<template>
  <div
    class="bg-white rounded-2xl grid justify-items-center drop-shadow-lg hover:scale-105 duration-500"
  >
    <img :src="photo" class="mt-10 w-48 h-48 bg-gray-500 rounded-full" />
    <div class="text-center">
      <span class="text-lg font-bold">{{ name }}</span
      ><br />({{ id }})
    </div>

    <div class="flex gap-x-5">
      <div
        class="h-12 w-12 grid place-content-center rounded-xl hover:bg-[#E3E5E5] duration-500"
      >
        <a :href="`${git}`" target="_blank"
          ><svg width="2.5em" height="2.25em" viewBox="0 0 24 24">
            <path
              fill="currentColor"
              d="M12 2A10 10 0 0 0 2 12c0 4.42 2.87 8.17 6.84 9.5c.5.08.66-.23.66-.5v-1.69c-2.77.6-3.36-1.34-3.36-1.34c-.46-1.16-1.11-1.47-1.11-1.47c-.91-.62.07-.6.07-.6c1 .07 1.53 1.03 1.53 1.03c.87 1.52 2.34 1.07 2.91.83c.09-.65.35-1.09.63-1.34c-2.22-.25-4.55-1.11-4.55-4.92c0-1.11.38-2 1.03-2.71c-.1-.25-.45-1.29.1-2.64c0 0 .84-.27 2.75 1.02c.79-.22 1.65-.33 2.5-.33c.85 0 1.71.11 2.5.33c1.91-1.29 2.75-1.02 2.75-1.02c.55 1.35.2 2.39.1 2.64c.65.71 1.03 1.6 1.03 2.71c0 3.82-2.34 4.66-4.57 4.91c.36.31.69.92.69 1.85V21c0 .27.16.59.67.5C19.14 20.16 22 16.42 22 12A10 10 0 0 0 12 2Z"
            ></path></svg
        ></a>
      </div>
      <div
        class="h-12 w-12 grid place-content-center rounded-xl hover:bg-[#E3E5E5] duration-500"
      >
        <a
          :href="`https://teams.microsoft.com/l/chat/0/0?users=${team}`"
          target="_blank"
          ><svg width="2.16em" height="2em" viewBox="0 0 256 239">
            <defs>
              <path
                id="IconifyId180c88db39a97b98176"
                d="M136.93 64.476v12.8a32.674 32.674 0 0 1-5.953-.952a38.698 38.698 0 0 1-26.79-22.742h21.848c6.008.022 10.872 4.887 10.895 10.894Z"
                opacity=".2"
              ></path>
            </defs>
            <defs>
              <linearGradient
                id="IconifyId180c88db39a97b98177"
                x1="17.372%"
                x2="82.628%"
                y1="-6.51%"
                y2="106.51%"
              >
                <stop offset="0%" stopColor="#5A62C3"></stop>
                <stop offset="50%" stopColor="#4D55BD"></stop>
                <stop offset="100%" stopColor="#3940AB"></stop>
              </linearGradient>
            </defs>
            <path
              fill="#5059C9"
              d="M178.563 89.302h66.125c6.248 0 11.312 5.065 11.312 11.312v60.231c0 22.96-18.613 41.574-41.573 41.574h-.197c-22.96.003-41.576-18.607-41.579-41.568V95.215a5.912 5.912 0 0 1 5.912-5.913Z"
            ></path>
            <circle cx="223.256" cy="50.605" r="26.791" fill="#5059C9"></circle>
            <circle cx="139.907" cy="38.698" r="38.698" fill="#7B83EB"></circle>
            <path
              fill="#7B83EB"
              d="M191.506 89.302H82.355c-6.173.153-11.056 5.276-10.913 11.449v68.697c-.862 37.044 28.445 67.785 65.488 68.692c37.043-.907 66.35-31.648 65.489-68.692v-68.697c.143-6.173-4.74-11.296-10.913-11.449Z"
            ></path>
            <path
              d="M142.884 89.302v96.268a10.96 10.96 0 0 1-6.787 10.062c-1.3.55-2.697.833-4.108.833H76.68c-.774-1.965-1.488-3.93-2.084-5.953a72.509 72.509 0 0 1-3.155-21.076v-68.703c-.143-6.163 4.732-11.278 10.895-11.43h60.547Z"
              opacity=".1"
            ></path>
            <path
              d="M136.93 89.302v102.222c0 1.411-.283 2.808-.833 4.108a10.96 10.96 0 0 1-10.062 6.787H79.48c-1.012-1.965-1.965-3.93-2.798-5.954a59.049 59.049 0 0 1-2.084-5.953a72.508 72.508 0 0 1-3.155-21.076v-68.703c-.143-6.163 4.732-11.278 10.895-11.43h54.593Z"
              opacity=".2"
            ></path>
            <path
              d="M136.93 89.302v90.315c-.045 5.998-4.896 10.85-10.895 10.895H74.597a72.508 72.508 0 0 1-3.155-21.076v-68.703c-.143-6.163 4.732-11.278 10.895-11.43h54.593Z"
              opacity=".2"
            ></path>
            <path
              d="M130.977 89.302v90.315c-.046 5.998-4.897 10.85-10.895 10.895H74.597a72.508 72.508 0 0 1-3.155-21.076v-68.703c-.143-6.163 4.732-11.278 10.895-11.43h48.64Z"
              opacity=".2"
            ></path>
            <path
              d="M142.884 58.523v18.753c-1.012.06-1.965.12-2.977.12c-1.012 0-1.965-.06-2.977-.12a32.674 32.674 0 0 1-5.953-.952a38.698 38.698 0 0 1-26.791-22.742a33.082 33.082 0 0 1-1.905-5.954h29.708c6.007.023 10.872 4.887 10.895 10.895Z"
              opacity=".1"
            ></path>
            <use xlink:href="#IconifyId180c88db39a97b98176" opacity=".2"></use>
            <use xlink:href="#IconifyId180c88db39a97b98176" opacity=".2"></use>
            <path
              d="M130.977 64.476v11.848a38.698 38.698 0 0 1-26.791-22.743h15.896c6.008.023 10.872 4.888 10.895 10.895Z"
              opacity=".2"
            ></path>
            <path
              fill="url(#IconifyId180c88db39a97b98177)"
              d="M10.913 53.581h109.15c6.028 0 10.914 4.886 10.914 10.913v109.151c0 6.027-4.886 10.913-10.913 10.913H10.913C4.886 184.558 0 179.672 0 173.645V64.495C0 58.466 4.886 53.58 10.913 53.58Z"
            ></path>
            <path
              fill="#FFF"
              d="M94.208 95.125h-21.82v59.416H58.487V95.125H36.769V83.599h57.439z"
            ></path></svg
        ></a>
      </div>
    </div>
  </div>
</template>

<style></style>
