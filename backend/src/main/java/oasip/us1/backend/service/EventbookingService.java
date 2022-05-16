package oasip.us1.backend.service;

import oasip.us1.backend.DTO.EventbookingDto;
import oasip.us1.backend.DTO.EventbookingInsertDto;
import oasip.us1.backend.entity.Eventbooking;
import oasip.us1.backend.repository.EventbookingRepository;
import oasip.us1.backend.repository.EventcategoryRepository;
import oasip.us1.backend.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Collection;
import java.util.Map;

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
    public ResponseEntity save(Eventbooking event, BindingResult bindingResult, WebRequest request){
        Map<Object,Object> errorBody = new HashMap<>();
        Map<Object,Object> fieldError = new HashMap<>();

        if(event.getEventStartTime().isBefore(Instant.now())){
            fieldError.put("eventStartTime","eventStartTime can't be past");
        }
        if(event.getEventEndTime().isBefore(event.getEventStartTime())){

            fieldError.put("eventEndTime","eventEndTime can not begin before eventStartTime");
        }
        if(!repository.findByEventStartTimeBetween(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventStartTime()),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventEndTime()),event.getEventCategoryId().getId()).isEmpty() ) {
            fieldError.put("TimeOverlap","your selected time is not available");
        }
        if(!bindingResult.hasErrors()){
        System.out.println("Insert!");
        return ResponseEntity.status(201).body(repository.saveAndFlush(event));
        }
        bindingResult.getAllErrors().forEach((error)->{
            fieldError.put(((FieldError)error).getField(),error.getDefaultMessage());

        });
        errorBody.put("timestamp",Instant.now().atZone(ZoneId.of("Asia/Bangkok")));
        errorBody.put("status",HttpStatus.BAD_REQUEST);
        errorBody.put("message","Validation failed");
        errorBody.put("fieldErrors",fieldError);
        errorBody.put("path", ((ServletWebRequest) request).getContextPath().toString());
        return new ResponseEntity(errorBody, HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity update(Eventbooking updateEventbooking , int bookingid, BindingResult bindingResult,WebRequest request ){
        Eventbooking event = repository.findById(bookingid).map(eventbooking1 -> mapEvent(eventbooking1,updateEventbooking,bookingid)).get();
        Map<Object,Object> errorBodyput = new HashMap<>();
        Map<Object,Object> fieldError = new HashMap<>();

        if(event.getEventStartTime().isBefore(Instant.now())){
            fieldError.put("eventStartTime","eventStartTime can't be past");
        }
        if(event.getEventEndTime().isBefore(event.getEventStartTime())){

            fieldError.put("eventEndTime","eventEndTime can not begin before eventStartTime");
        }
        if(!repository.findByEventStartTimeBetweenForPut(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventStartTime()),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventEndTime()),event.getEventCategoryId().getId(),event.getId()).isEmpty()){
            fieldError.put("TimeOverlap","your selected time is not available");
        }
        if (!bindingResult.hasErrors()){
        System.out.println("Insert!");
        return ResponseEntity.status(200).body(repository.saveAndFlush(event));
        }

        errorBodyput.put("timestamp",Instant.now().atZone(ZoneId.of("Asia/Bangkok")));
        errorBodyput.put("status",HttpStatus.BAD_REQUEST);
        errorBodyput.put("message","Validation failed");
        errorBodyput.put("fieldErrors",fieldError);
        errorBodyput.put("path", ((ServletWebRequest) request).getContextPath().toString());
        return new ResponseEntity(errorBodyput, HttpStatus.BAD_REQUEST);

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
