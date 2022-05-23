import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref } from 'vue'
export const useEvents = defineStore('events', () => {
  // Array (ref) for stores all events
  const eventLists = ref([])
  const listDetails = ref([])
  const omit = (prop, { [prop]: _, ...rest }) => rest

  //GET
  // check for intitial page
  const checkPage = (page, result) =>
    page == 0
      ? (eventLists.value = result.content)
      : eventLists.value.push(...result.content)

  //fetch method GET page
  const getEventsPage = async (
    uap = 'a',
    categories = [1, 2, 3, 4, 5],
    page = 0,
    pageSize = 12,
    orderBy = false
  ) => {
    let params = categories.join('&catid=')
    const res = await fetch(
      `${
        import.meta.env.VITE_BASE_URL
      }/events?uap=${uap}&catid=${params}&page=${page}&pageSize=${pageSize}&sortBy=eventStartTime&isAsc=${orderBy}`
    )
    if (res.status === 200) {
      let result = await res.json()
      checkPage(page, result)
      listDetails.value = omit('content', result)
    } else console.log('error, cannot get events')
  }

  //fetch method GET by id
  const getEventById = async (id) => {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/event/${id}`)
    if (res.status === 200) {
      return await res.json()
    } else console.log('error, cannot get events')
  }

  //fetch method GET by date
  const getEventByDate = async (
    date,
    offSet,
    isNegative,
    page = 0,
    pageSize = 12,
    orderBy = true
  ) => {
    const res = await fetch(
      `${
        import.meta.env.VITE_BASE_URL
      }/events/by-date?date=${date}&offSet=${offSet}&negative=${isNegative}&page=${page}&pageSize=${pageSize}&sortBy=eventStartTime&isAsc=${orderBy}`
    )
    if (res.status === 200) {
      let result = await res.json()
      checkPage(page, result)
      listDetails.value = omit('content', result)
    } else console.log('error, cannot get events')
  }

  //fetch method GET by id & date for check overlap frontend
  const getEventByIdDate = async (id, date, offSet, isNegative) => {
    const res = await fetch(
      `${
        import.meta.env.VITE_BASE_URL
      }/events/by-date-and-cat?catid=${id}&date=${date}&offSet=${offSet}&negative=${isNegative}`
    )
    if (res.status === 200) {
      return await res.json()
    } else console.log('error, cannot get events')
  }

  //POST
  const createEvent = async (newEvent) => {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/events`, {
      method: 'POST',
      headers: { 'content-type': 'application/json' },
      body: JSON.stringify(newEvent)
    })
    if (res.status === 201) {
      console.log('created successfully')
      getEventsPage()
    } else console.log('error, cannot create event')
  }

  //DELETE
  const deleteEvent = async (eventId) => {
    const res = await fetch(
      `${import.meta.env.VITE_BASE_URL}/events/${eventId}`,
      {
        method: 'DELETE'
      }
    )
    if (res.status === 200) {
      console.log('deleted successfully')
      getEventsPage()
      // eventLists.value = eventLists.value.filter((event) => event.id != eventId)
    } else console.log('error, cannot delete event')
  }

  //PUT
  const editEvent = async (event, id) => {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/events/${id}`, {
      method: 'PUT',
      headers: {
        'content-type': 'application/json'
      },
      body: JSON.stringify(event)
    })
    if (res.status === 200) {
      updateEvent(event, id)
      console.log('edited successfully')
    } else console.log('error, cannot edit event')
  }

  // function update event
  function updateEvent(updatedEvent, id) {
    eventLists.value.map((event) => {
      if (event.id == id) {
        event.eventStartTime = updatedEvent.eventStartTime
        event.eventNotes = updatedEvent.eventNotes
      }
    })
  }

  return {
    eventLists,
    getEventsPage,
    getEventById,
    getEventByDate,
    getEventByIdDate,
    createEvent,
    deleteEvent,
    editEvent,
    updateEvent,
    listDetails
  }
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useEvents, import.meta.hot))
}
