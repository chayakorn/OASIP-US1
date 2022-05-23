<script setup>
defineEmits(['create'])
defineProps({
  cateItem: {
    type: Object,
    require: true
  }
})
const categoryBg = (cateId) => {
  switch (cateId) {
    case 1:
      return {
        bg: 'bg-gradient-to-b from-[#031B89] to-[#B3B9D5]',
        plus: 'hover:text-[#031B89]'
      }
    case 2:
      return {
        bg: 'bg-gradient-to-b from-[#6196FF] to-[#CBD8F2]',
        plus: 'hover:text-[#6196FF]'
      }
    case 3:
      return {
        bg: 'bg-gradient-to-b from-[#A9B5FF] to-[#DDE0F2]',
        plus: 'hover:text-[#A9B5FF]'
      }
    case 4:
      return {
        bg: 'bg-gradient-to-b from-[#FAA7B7] to-[#F1D1D6]',
        plus: 'hover:text-[#FAA7B7]'
      }
    case 5:
      return {
        bg: 'bg-gradient-to-b from-[#FB7A8E] to-[#F1DCE0]',
        plus: 'hover:text-[#FB7A8E]'
      }
  }
}
</script>

<template>
  <div
    :class="[
      'box',
      categoryBg(cateItem.id).bg,
      'relative rounded-xl h-[28em] my-10 text-white',
      categoryBg(cateItem.id).plus
    ]"
  >
    <div class="test grid justify-items-center">
      <div class="to-hide mt-14 mb-3">
        <img :src="cateItem.image" width="80" />
      </div>
      <div
        class="header font-pragati text-white text-4xl font-semibold text-center h-20 grid content-center"
      >
        {{ cateItem.eventCategoryName.split('Clinic')[0] }}
      </div>
      <div class="to-hide text-white text-xl font-normal mt-2">Clinic</div>
    </div>
    <button
      id="createbtn"
      class="flex justify-center absolute z-10 inset-x-0 bottom-0 my-4 hover:scale-110 duration-700"
      @click="$emit('create', cateItem.eventCategoryName)"
    >
      <svg width="2em" height="2em" viewBox="0 0 24 24">
        <path
          fill="currentColor"
          d="M18 12.998h-5v5a1 1 0 0 1-2 0v-5H6a1 1 0 0 1 0-2h5v-5a1 1 0 0 1 2 0v5h5a1 1 0 0 1 0 2z"
        ></path>
      </svg>
    </button>
    <div class="hid-box">
      <div
        class="bg-white text-black text-lg font-normal rounded-xl h-full p-5 py-5 text-center"
      >
        <div class="h-4/6 border-b-2 border-[#E3E5E5]">
          <span v-show="cateItem.eventCategoryDescription.length">{{
            cateItem.eventCategoryDescription
          }}</span>
          <span
            v-if="!cateItem.eventCategoryDescription.length"
            class="text-gray-400"
            >No description</span
          >
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.box {
  overflow: hidden;
}
.hid-box {
  top: 100%;
  position: relative;
  transition: 0.5s ease;
  height: 100%;
}

.box:hover > .hid-box {
  top: 0;
}

.box:hover > .test {
  transform: translateY(-20%);
  transition: all 0.75s;
}

.box:hover > div .header {
  height: 0;
  margin: -5% 0 0 0;
  transition: all 0.5s linear;
}

.box:hover > div .to-hide {
  margin: -4.5% 0 0 0;
  opacity: 0;
  transition: height 0.5s, visibility 0.5s, opacity 0.5s linear;
}
</style>
