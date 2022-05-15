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
        if(!repository.findByEventStartTimeBetween(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventStartTime()),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventEndTime()),event.getEventCategoryId().getId()).isEmpty()) {
            return ResponseEntity.status(400).body("Overlapped time");
        }
        if(event.getEventStartTime().isBefore(Instant.now())){
            return ResponseEntity.status(400).body("EventStartTime is past");
        }
        if(event.getEventEndTime().isBefore(event.getEventStartTime())){
            return ResponseEntity.status(400).body("EventEndTime is before eventStartTime");
        }
        if(event.getBookingName() == null || event.getBookingEmail().length() == 0){
            return ResponseEntity.status(400).body("BookingName error null");
        }
        if(event.getBookingName().length() > 100){
            return ResponseEntity.status(400).body("BookingName over length");
        }
        if(event.getEventCategoryId() == null){
            return ResponseEntity.status(400).body("EventCategory is null");
        }
        if(!event.getBookingEmail().matches("^[0-z.!#$%&'*+/=?^_`{|}~-]+@[0-z-]+(.[0-z-]+)*$") ){
            return ResponseEntity.status(400).body("Email is not valid");
        }
        if (event.getBookingEmail() == null ){
            return ResponseEntity.status(400).body("Email is null");
        }
        if (event.getBookingEmail().length() > 100){
            return ResponseEntity.status(400).body("Email is over length");
        }
        if(event.getEventNotes().length()>500){
            return ResponseEntity.status(400).body("Eventnote over length");
        }
        System.out.println("Insert!");
        return ResponseEntity.status(201).body(repository.saveAndFlush(event));
    }
    public ResponseEntity update(Eventbooking updateEventbooking , int bookingid){
        Eventbooking event = repository.findById(bookingid).map(eventbooking1 -> mapEvent(eventbooking1,updateEventbooking,bookingid)).get();

        if(!repository.findByEventStartTimeBetweenForPut(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventStartTime()),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventEndTime()),event.getEventCategoryId().getId(),event.getId()).isEmpty()){
            return ResponseEntity.status(400).body("Overlapped time");
        }
        if(event.getEventStartTime().isBefore(Instant.now())){
            return ResponseEntity.status(400).body("EventStartTime is past");
        }
        if(event.getEventEndTime().isBefore(event.getEventStartTime())){
            return ResponseEntity.status(400).body("EventEndTime is before eventStartTime");
        }
        if(event.getEventNotes().length()>500){
            return ResponseEntity.status(400).body("Eventnote over length");
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
