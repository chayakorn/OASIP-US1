<script setup>
import Developer from '../components/Developer.vue'
</script>

<template>
  <div class="px-[10%] py-[5%] grid grid-rows-5">
    <div class="text-center text-5xl font-bold">DEVELOPER TEAM</div>
    <div class="row-span-4 grid grid-cols-3 gap-x-10 h-full">
      <Developer
        name="Kanjanaporn Chuenmaneerat"
        id="63130500005"
        photo="./images/yok.jpg"
        git="https://github.com/VioletKan"
        team="kanjanaporn.stan@mail.kmutt.ac.th"
      />
      <Developer
        name="Chayakorn Ngamoranlerd"
        id="63130500017"
        photo="./images/j.jpg"
        git="https://github.com/chayakorn"
        team="chayakorn.j@mail.kmutt.ac.th"
      />
      <Developer
        name="Thiti Ouengcharoen"
        id="63130500059"
        photo="./images/ben.jpg"
        git="https://github.com/bbenjmn01"
        team="thiti.ouengcharoen@mail.kmutt.ac.th"
      />
    </div>
  </div>
</template>

<style></style>
