package oasip.us1.backend.service;

import oasip.us1.backend.DTO.EventbookingDto;
import oasip.us1.backend.DTO.EventbookingInsertDto;
import oasip.us1.backend.entity.Eventbooking;
import oasip.us1.backend.entity.Eventcategory;
import oasip.us1.backend.repository.EventbookingRepository;
import oasip.us1.backend.repository.EventcategoryRepository;
import oasip.us1.backend.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class EventbookingService {
    @Autowired
    private EventbookingRepository repository;
    @Autowired
    private EventcategoryRepository eventcategoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    public List<EventbookingDto> getAll(){
        return listMapper.mapList(repository.findAll(),EventbookingDto.class,modelMapper) ;
    }
    public EventbookingDto getEventById(int id){
        return modelMapper.map(repository.getById(id),EventbookingDto.class) ;
    }
    public Eventbooking save(EventbookingInsertDto newEventbooking){
        Eventbooking event = modelMapper.map(newEventbooking, Eventbooking.class);
        event.setEventCategoryId(eventcategoryRepository.findById(newEventbooking.getEventCategoryId()).get());
        repository.insertEvent(event.getEventCategoryId().getId(),event.getBookingName(),event.getBookingEmail(),Timestamp.from(event.getEventStartTime()).toString(),event.getEventDuration(),event.getEventNotes(),event.getName());
        return event;
    }
    public Eventbooking update(EventbookingDto updateEventbooking , int bookingid){
        Eventbooking eventbooking = repository.findById(bookingid).map(eventbooking1 -> mapEvent(eventbooking1,updateEventbooking,bookingid)).orElseGet(()-> modelMapper.map(updateEventbooking , Eventbooking.class));
       return repository.saveAndFlush(eventbooking);
    }

    public void delete(int id){
        repository.deleteById(id);
    }
    private Eventbooking mapEvent(Eventbooking existingEventbooking, EventbookingDto updateEventbooking, int bookingid){
        existingEventbooking.setId(bookingid);
        existingEventbooking.setEventCategoryId(eventcategoryRepository.findById(updateEventbooking.getEventCategoryId()).get());
        existingEventbooking.setEventDuration(updateEventbooking.getEventDuration());
        existingEventbooking.setEventNotes(updateEventbooking.getEventNotes());
        existingEventbooking.setEventStartTime(updateEventbooking.getEventStartTime());
        existingEventbooking.setBookingEmail(updateEventbooking.getBookingEmail());
        existingEventbooking.setName(updateEventbooking.getName());
        existingEventbooking.setBookingName(updateEventbooking.getBookingName());
        return existingEventbooking;
    }
}
