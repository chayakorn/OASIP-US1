package oasip.us1.backend.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import oasip.us1.backend.DTO.EventPageDto;
import oasip.us1.backend.DTO.EventbookingDto;
import oasip.us1.backend.DTO.EventbookingInsertDto;
import oasip.us1.backend.DTO.EventbookingPutDto;
import oasip.us1.backend.entity.Eventbooking;
import oasip.us1.backend.entity.Eventcategory;
import oasip.us1.backend.repository.EventbookingRepository;
import oasip.us1.backend.repository.EventcategoryRepository;
import oasip.us1.backend.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
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

    @Data
    @RequiredArgsConstructor
    private static class Error{
        private final String timestamp;
        private final int status;
        private final String path;
        private final String message;
        private final Map<String,String> fieldErrors;
    }

    public EventPageDto getAllEventByCatId(int page, int pageSize, String sortBy, boolean isAsc, Collection<String> catid, String uap) {
        System.out.println(catid.size());
        if(uap.equals("p")){
            return modelMapper.map(repository.findByCategoryIdPast(catid,
                            PageRequest.of(page, pageSize, isAsc ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending()),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(Instant.now())),
                    EventPageDto.class);
        }
        if(uap.equals("u")){
            return modelMapper.map(repository.findByCategoryIdUpcomming(catid,
                            PageRequest.of(page, pageSize, isAsc ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending()),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(Instant.now())),
                    EventPageDto.class);
        }
            return modelMapper.map(repository.findByCategoryId(catid,
                            PageRequest.of(page, pageSize, isAsc ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending())),
                    EventPageDto.class);


    }

    public EventbookingDto getEventById(int id){
        return modelMapper.map(repository.getById(id),EventbookingDto.class) ;
    }

    public List<EventbookingDto> getEventByCatAndDate(int catid,String date,String offSet,boolean negative){

        if(negative){
            return listMapper.mapList(repository.findByEventStartTimeAndEventCategoryIdForMinus(catid,date,offSet),EventbookingDto.class,modelMapper);
        }
        return listMapper.mapList(repository.findByEventStartTimeAndEventCategoryIdForPlus(catid,date,offSet),EventbookingDto.class,modelMapper);
    }

    public EventPageDto getEventByDate(String date,String offSet,boolean negative,int page, int pageSize, String sortBy, boolean isAsc){
        if(negative){
            return modelMapper.map(repository.findByDateForMinus(date,offSet,PageRequest.of(page, pageSize, isAsc ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending())),EventPageDto.class);
        }
        return modelMapper.map(repository.findByDateForPlus(date,offSet,PageRequest.of(page, pageSize, isAsc ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending())),EventPageDto.class);
    }

    public ResponseEntity save(EventbookingInsertDto event, BindingResult bindingResult, WebRequest request){
        Map<String,String> fieldError = new HashMap<>();

        bindingResult.getAllErrors().forEach((error)->{
            fieldError.put(((FieldError)error).getField(),error.getDefaultMessage());
        });
        if(fieldError.size() == 0){
        if (event.getEventCategoryId() == null){
            fieldError.put("eventCategoryId","eventCategoryId can not be null");
        }else
        if(!repository.findByEventStartTimeBetween(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventStartTime().plus(1,ChronoUnit.SECONDS)),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(event.getEventStartTime().plus(event.getEventDuration(),ChronoUnit.MINUTES).minus(1,ChronoUnit.SECONDS)),event.getEventCategoryId()).isEmpty() && event.getEventCategoryId() != null) {
            fieldError.put("TimeOverlap","your selected time is not available");
        }
            if (fieldError.size() == 0) {
                System.out.println("Insert!");
                Eventbooking insertevent = mapEventForInsert(event);
                return ResponseEntity.status(201).body(repository.saveAndFlush(insertevent));
            }
        }
        Error errorBody = new Error(Instant.now().atZone(ZoneId.of("Asia/Bangkok")).toString(), HttpStatus.BAD_REQUEST.value(), ((ServletWebRequest) request).getRequest().getRequestURI(), "Validation failed", fieldError);
        return new ResponseEntity(errorBody, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity update(EventbookingPutDto updateEventbooking , int bookingid, BindingResult bindingResult, WebRequest request ){

        Eventbooking event = repository.findById(bookingid).get();

        Map<String,String> fieldError = new HashMap<>();
        if(updateEventbooking.getEventStartTime().isBefore(Instant.now())){
            fieldError.put("eventStartTime","eventStartTime can not be past");
        }
        if(!repository.findByEventStartTimeBetweenForPut(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(updateEventbooking.getEventStartTime()),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC)).format(updateEventbooking.getEventStartTime().plus(event.getEventDuration(),ChronoUnit.MINUTES)),event.getEventCategoryId().getId(),event.getId()).isEmpty()){
            fieldError.put("TimeOverlap","your selected time is not available");
        }
        if (updateEventbooking.getEventNotes().length() > 500){
            fieldError.put("eventNotes", "eventNotes must be between 0-500");
        }
        System.out.println(bindingResult.getAllErrors());
        if (fieldError.isEmpty()){
            event = repository.findById(bookingid).map(eventbooking1 -> mapEvent(eventbooking1,updateEventbooking,bookingid)).get();

            System.out.println("Insert!");
            return ResponseEntity.status(200).body(repository.saveAndFlush(event));
        }
        Error errorBody = new Error(Instant.now().atZone(ZoneId.of("Asia/Bangkok")).toString(), HttpStatus.BAD_REQUEST.value(), ((ServletWebRequest) request).getRequest().getRequestURI(), "Validation failed", fieldError);
        return new ResponseEntity(errorBody, HttpStatus.BAD_REQUEST);
    }

    public void delete(int id){
        repository.deleteById(id);
    }

    private Eventbooking mapEvent(Eventbooking existingEventbooking, EventbookingPutDto updateEventbooking, int bookingid){
        existingEventbooking.setId(bookingid);
        existingEventbooking.setEventNotes(updateEventbooking.getEventNotes());
        existingEventbooking.setEventStartTime(updateEventbooking.getEventStartTime());
        return existingEventbooking;
    }

    private Eventbooking mapEventForInsert(EventbookingInsertDto insertDto){
        Eventbooking eventbooking = new Eventbooking();
        eventbooking.setBookingName(insertDto.getBookingName());
        eventbooking.setEventDuration(insertDto.getEventDuration());
        eventbooking.setEventNotes(insertDto.getEventNotes());
        eventbooking.setName(insertDto.getName());
        eventbooking.setEventStartTime(insertDto.getEventStartTime());
        eventbooking.setEventCategoryId(eventcategoryRepository.findById(insertDto.getEventCategoryId()).get());
        eventbooking.setBookingEmail(insertDto.getBookingEmail());
        return eventbooking;
    }
}
