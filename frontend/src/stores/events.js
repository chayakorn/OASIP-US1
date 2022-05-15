import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref } from 'vue'
export const useEvents = defineStore('categories', () => {
  // Array (ref) for stores all events
  const eventLists = ref([])

  //fetch method GET
  const getAllEvents = async () => {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/event`)
    if (res.status === 200) {
      eventLists.value = await res.json()
    } else console.log('error, cannot get events')
  }

  //fetch method POST
  const createEvent = async (newEvent) => {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/event`, {
      method: 'POST',
      headers: { 'content-type': 'application/json' },
      body: JSON.stringify(newEvent)
    })
    if (res.status === 201) {
      console.log('created successfully')
      getAllEvents()
    } else console.log('error, cannot create event')
  }

  //fetch method DELETE
  const deleteEvent = async (eventId) => {
    const res = await fetch(
      `${import.meta.env.VITE_BASE_URL}/event/${eventId}`,
      {
        method: 'DELETE'
      }
    )
    if (res.status === 200) {
      console.log('deleted successfully')
      eventLists.value = eventLists.value.filter((event) => event.id != eventId)
    } else console.log('error, cannot delete event')
  }

  //fetch method PUT
  const editEvent = async (event, id) => {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/event/${id}`, {
      method: 'PUT',
      headers: {
        'content-type': 'application/json'
      },
      body: JSON.stringify(event)
    })
    if (res.status === 200) {
      console.log('edited successfully')
      updateEvent(event, id)
    } else console.log('error, cannot edit event')
  }

  // function update event
  function updateEvent(updatedEvent, id) {
    eventLists.value.map((event) => {
      if (event.id === id) {
        event.eventStartTime = updatedEvent.eventStartTime
        event.eventEndTime = updatedEvent.eventEndTime
        event.eventNotes = updatedEvent.eventNotes
      }
    })
  }

  return {
    eventLists,
    getAllEvents,
    createEvent,
    deleteEvent,
    editEvent,
    updateEvent
  }
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useEvents, import.meta.hot))
}