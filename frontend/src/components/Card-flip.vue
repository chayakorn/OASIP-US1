<script setup>
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
        plus: '#031B89'
      }
    case 2:
      return {
        bg: 'bg-gradient-to-b from-[#6196FF] to-[#CBD8F2]',
        plus: '#031B89'
      }
    case 3:
      return {
        bg: 'bg-gradient-to-b from-[#A9B5FF] to-[#DDE0F2]',
        plus: '#031B89'
      }
    case 4:
      return {
        bg: 'bg-gradient-to-b from-[#FAA7B7] to-[#F1D1D6]',
        plus: '#031B89'
      }
    case 5:
      return {
        bg: 'bg-gradient-to-b from-[#FB7A8E] to-[#F1DCE0]',
        plus: '#031B89'
      }
  }
}
</script>

<template>
  <div class="flip my-10 h-[28em]">
    <div class="flip-content">
      <div class="flip-front rounded-xl">
        <div
          :class="[
            categoryBg(cateItem.id).bg,
            'rounded-xl h-[28em] grid justify-items-center'
          ]"
        >
          <div class="mt-14 mb-3 hover:hidden">
            <img :src="cateItem.image" width="80" />
          </div>
          <div
            class="font-pragati text-white text-4xl font-semibold text-center h-20 grid content-center"
          >
            {{ cateItem.eventCategoryName.split('Clinic')[0] }}
          </div>
          <div class="text-white text-xl font-normal mt-2">Clinic</div>
          <div class="text-white mt-32">
            <!-- <svg width="2em" height="2em" viewBox="0 0 24 24">
                <path
                  fill="currentColor"
                  d="M18 12.998h-5v5a1 1 0 0 1-2 0v-5H6a1 1 0 0 1 0-2h5v-5a1 1 0 0 1 2 0v5h5a1 1 0 0 1 0 2z"
                ></path>
              </svg> -->
          </div>
        </div>
      </div>
      <div class="flip-back rounded-xl">
        <div
          :class="[
            categoryBg(cateItem.id).bg,
            'rounded-xl h-[28em] text-white'
          ]"
        >
          <div
            class="font-pragati text-3xl font-semibold text-center h-16 grid content-center"
          >
            {{ cateItem.eventCategoryName.split('Clinic')[0] }}
          </div>
          <div
            class="bg-white text-black text-lg font-normal rounded-xl h-[21.5em] p-2 py-5"
          >
            <span v-show="cateItem.eventCategoryDescription.length">{{
              cateItem.eventCategoryDescription
            }}</span>
            <span
              v-if="!cateItem.eventCategoryDescription.length"
              class="text-gray-400"
              >No description</span
            >
            <div class="absolute inset-x-0 bottom-0">
              <button>
                <svg width="2em" height="2em" viewBox="0 0 24 24">
                  <path
                    fill="currentColor"
                    d="M18 12.998h-5v5a1 1 0 0 1-2 0v-5H6a1 1 0 0 1 0-2h5v-5a1 1 0 0 1 2 0v5h5a1 1 0 0 1 0 2z"
                  ></path>
                </svg>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.flip {
  text-align: center;
  perspective: 600px;
}
.flip-content {
  width: 100%;
  height: 100%;
  transition: transform 1s;
  transform-style: preserve-3d;
}
.flip:hover .flip-content {
  transform: rotateY(180deg);
  transition: transform 1s;
}
.flip-front,
.flip-back {
  position: absolute;
  height: 100%;
  width: 100%;
  line-height: 150px;
  backface-visibility: hidden;
  color: #fff;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.25);
}
.flip-back {
  transform: rotateY(180deg);
}
</style>
