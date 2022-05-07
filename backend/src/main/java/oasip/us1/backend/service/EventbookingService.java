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

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
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

    public List<EventbookingDto> getAll()  {
        return listMapper.mapList(repository.findAll(),EventbookingDto.class,modelMapper) ;
    }
    public EventbookingDto getEventById(int id){
        return modelMapper.map(repository.getById(id),EventbookingDto.class) ;
    }
    public Eventbooking save(Eventbooking event , HttpServletResponse response){
        if(repository.findByEventStartTimeBetween(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.ofHours(7))).format(event.getEventStartTime()),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.ofHours(7))).format(event.getEventEndTime()),event.getEventCategoryId().getId()).isEmpty()){
            System.out.println("Insert!");
            return repository.saveAndFlush(event);
        }else{
            response.setStatus(422);
        }
        return new Eventbooking();
    }
    public Eventbooking update(Eventbooking updateEventbooking , int bookingid, HttpServletResponse response){
        Eventbooking event = repository.findById(bookingid).map(eventbooking1 -> mapEvent(eventbooking1,updateEventbooking,bookingid)).get();
        if(repository.findByEventStartTimeBetweenForPut(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.ofHours(7))).format(event.getEventStartTime()),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.ofHours(7))).format(event.getEventEndTime()),event.getEventCategoryId().getId(),event.getId()).isEmpty()){
            System.out.println("Insert!");
            return repository.saveAndFlush(event);
        }else{
            response.setStatus(422);
        }
       return new Eventbooking();
    }

    public void delete(int id){
        repository.deleteById(id);
    }
    private Eventbooking mapEvent(Eventbooking existingEventbooking, Eventbooking updateEventbooking, int bookingid){
        existingEventbooking.setId(bookingid);
        existingEventbooking.setEventCategoryId(updateEventbooking.getEventCategoryId());
        existingEventbooking.setEventDuration(updateEventbooking.getEventDuration());
        existingEventbooking.setEventNotes(updateEventbooking.getEventNotes());
        existingEventbooking.setEventStartTime(updateEventbooking.getEventStartTime());
        existingEventbooking.setEventEndTime(updateEventbooking.getEventEndTime());
        existingEventbooking.setBookingEmail(updateEventbooking.getBookingEmail());
        existingEventbooking.setName(updateEventbooking.getName());
        existingEventbooking.setBookingName(updateEventbooking.getBookingName());
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
