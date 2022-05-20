import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref } from 'vue'
export const useCategories = defineStore('categories', () => {
  // Array (ref) for stores all events
  const categoryLists = ref([])

  //fetch method GET
  const getAllCategories = async () => {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/eventcat`)
    if (res.status === 200) {
      categoryLists.value = await res.json()
    } else console.log('error, cannot get category')
  }

  //fetch method GET by id
  const getCategoryById = async (id) => {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/eventcat/${id}`)
    if (res.status === 200) {
      return await res.json()
    } else console.log('error, cannot get category')
  }

  //fetch method PUT
  // const editCategory = async (category, id) => {
  //   const res = await fetch(`${import.meta.env.VITE_BASE_URL}/eventcat/${id}`, {
  //     method: 'PUT',
  //     headers: {
  //       'content-type': 'application/json'
  //     },
  //     body: JSON.stringify(category)
  //   })
  //   if (res.status === 200) {
  //     console.log('edited successfully')
  //     updateCategory(category, id)
  //   } else console.log('error, cannot edit category')
  // }

  return {
    categoryLists,
    getAllCategories,
    getCategoryById
  }
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useCategories, import.meta.hot))
}
