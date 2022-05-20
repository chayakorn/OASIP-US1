package oasip.us1.backend.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import oasip.us1.backend.DTO.EventCategoryPutDto;
import oasip.us1.backend.entity.Eventcategory;
import oasip.us1.backend.repository.EventcategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class EventcategoryService {
    @Autowired
    private EventcategoryRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    @Data
    @RequiredArgsConstructor
    private static class Error{
        private final String timestamp;
        private final int status;
        private final String path;
        private final String message;
        private final Map<String,String> fieldErrors;

    }
    public List<Eventcategory> getAllEventCategory(){
        return repository.findAll();
    }
    public Eventcategory getEventCategoryById(int id){
        return repository.findById(id).get();
    }
    public ResponseEntity update(EventCategoryPutDto updateEventCategory, BindingResult result, int id , WebRequest request){
        Map<String,String> fieldError = new HashMap<>();
        result.getAllErrors().forEach((err)->{
            fieldError.put(((FieldError) err).getField(),err.getDefaultMessage());
        });
        repository.findAll().forEach((eventcategory)->{
            if(eventcategory.getEventCategoryName().toLowerCase().trim().equals(updateEventCategory.getEventCategoryName().toLowerCase().trim()) && eventcategory.getId() != id){
                fieldError.put("eventCategoryName","eventCategoryName must be unique");
            }
        });
        if (fieldError.size()> 0){
            Error errorBody = new Error(Instant.now().atZone(ZoneId.of("Asia/Bangkok")).toString(), HttpStatus.BAD_REQUEST.value(), ((ServletWebRequest) request).getRequest().getRequestURI(), "Validation failed", fieldError);
            return new ResponseEntity(errorBody,HttpStatus.BAD_REQUEST);
        }
        Eventcategory event = repository.findById(id).get();
        modelMapper.map(updateEventCategory,event);
        return new ResponseEntity(repository.saveAndFlush(event),HttpStatus.ACCEPTED);
    }
    public ResponseEntity delete(int id){
        repository.deleteById(id);
        return new ResponseEntity("Deleted id "+id, HttpStatus.ACCEPTED);
    }
}
