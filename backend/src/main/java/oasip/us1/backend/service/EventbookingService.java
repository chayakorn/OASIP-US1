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
        System.out.println( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.ofHours(7))).format(repository.getById(1).getEventStartTime()));

        try {
            Date a = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2022-05-23 13:30");
            System.out.println(a.toInstant());
        }catch (ParseException e){
            System.out.println(e.getMessage());
        }

        return listMapper.mapList(repository.findAll(),EventbookingDto.class,modelMapper) ;
    }
    public EventbookingDto getEventById(int id){
        return modelMapper.map(repository.getById(id),EventbookingDto.class) ;
    }
    public Eventbooking save(Eventbooking event){
//        Eventbooking event = modelMapper.map(newEventbooking, Eventbooking.class);
//        Eventbooking event = mapForInsert(newEventbooking);
//        repository.insertEvent(event.getEventCategoryId().getId(),event.getBookingName(),event.getBookingEmail(),Timestamp.from(event.getEventStartTime()).toString(),event.getEventDuration(),event.getEventNotes(),event.getName());
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventStartTime()));
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventStartTime().plus(event.getEventDuration(), ChronoUnit.MINUTES)));
        if(repository.findByEventStartTimeBetween(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventStartTime()),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventStartTime().plus(event.getEventDuration(), ChronoUnit.MINUTES)),event.getEventCategoryId().getId()).isEmpty()){
            repository.saveAndFlush(event);
        };
        return repository.saveAndFlush(event);
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
