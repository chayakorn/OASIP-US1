package oasip.us1.backend.service;

import oasip.us1.backend.DTO.EventbookingDto;
import oasip.us1.backend.DTO.EventbookingInsertDto;
import oasip.us1.backend.entity.Eventbooking;
import oasip.us1.backend.repository.EventbookingRepository;
import oasip.us1.backend.repository.EventcategoryRepository;
import oasip.us1.backend.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Collection;

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

    public List<EventbookingDto> getAll()  {
        return listMapper.mapList(repository.findAll(),EventbookingDto.class,modelMapper) ;
    }
    public EventbookingDto getEventById(int id){
        return modelMapper.map(repository.getById(id),EventbookingDto.class) ;
    }
    public ResponseEntity save(Eventbooking event){
        if(!repository.findByEventStartTimeBetween(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventStartTime()),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventEndTime()),event.getEventCategoryId().getId()).isEmpty()
                || event.getEventStartTime().isBefore(Instant.now())
                || event.getEventEndTime().isBefore(event.getEventStartTime())
                || event.getBookingName() == null
                || event.getEventCategoryId() == null
                || event.getBookingName().length() > 100
                || !event.getBookingEmail().matches("^[0-z.!#$%&'*+/=?^_`{|}~-]+@[0-z-]+(.[0-z-]+)*$")
                || event.getBookingEmail() == null
                || event.getEventNotes().length()>500) {
            return ResponseEntity.status(422).body("Overlapped time or value is invalid");
        }
        System.out.println("Insert!");
        return ResponseEntity.status(201).body(repository.saveAndFlush(event));
    }
    public ResponseEntity update(Eventbooking updateEventbooking , int bookingid){
        Eventbooking event = repository.findById(bookingid).map(eventbooking1 -> mapEvent(eventbooking1,updateEventbooking,bookingid)).get();

        if(!repository.findByEventStartTimeBetweenForPut(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventStartTime()),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventEndTime()),event.getEventCategoryId().getId(),event.getId()).isEmpty()
                || event.getEventStartTime().isBefore(Instant.now())
                || event.getEventEndTime().isBefore(event.getEventStartTime())
                || event.getEventNotes().length()>500){
            return ResponseEntity.status(400).body("Overlapped time");

        }
        System.out.println("Insert!");
        return ResponseEntity.status(200).body(repository.saveAndFlush(event));
    }

    public void delete(int id){
        repository.deleteById(id);
    }
    private Eventbooking mapEvent(Eventbooking existingEventbooking, Eventbooking updateEventbooking, int bookingid){
        existingEventbooking.setId(bookingid);
        existingEventbooking.setEventNotes(updateEventbooking.getEventNotes());
        existingEventbooking.setEventStartTime(updateEventbooking.getEventStartTime());
        existingEventbooking.setEventEndTime(updateEventbooking.getEventEndTime());
        return existingEventbooking;
    }
    private Eventbooking mapForInsert(EventbookingInsertDto newEventbooking){
        Eventbooking event = new Eventbooking();
        event.setBookingName(newEventbooking.getBookingName());
        event.setBookingEmail(newEventbooking.getBookingEmail());
        event.setEventStartTime(newEventbooking.getEventStartTime());
        event.setEventCategoryId(eventcategoryRepository.findById(newEventbooking.getEventCategoryId()).get());
        event.setEventDuration(newEventbooking.getEventDuration());
        event.setEventNotes(newEventbooking.getEventNotes());
        event.setName(newEventbooking.getName());
        return event;
    }
}
