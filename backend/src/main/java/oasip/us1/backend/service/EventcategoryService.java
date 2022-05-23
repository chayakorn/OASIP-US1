package oasip.us1.backend.service;

import oasip.us1.backend.DTO.ErrorDTO;
import oasip.us1.backend.DTO.EventCategoryDto;
import oasip.us1.backend.DTO.EventCategoryPutDto;
import oasip.us1.backend.DTO.EventbookingDto;
import oasip.us1.backend.entity.Eventcategory;
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
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class EventcategoryService {
    @Autowired
    private EventcategoryRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EventbookingRepository eventbookingRepository;
    @Autowired
    private ListMapper listMapper;


    public List<Eventcategory> getAllEventCategory(){
        return repository.findAll();
    }
    public ResponseEntity getEventCategoryById(int id,WebRequest request){
        Map<String,String> fieldError = new HashMap<>();
        if (repository.findById(id).isEmpty()){
            fieldError.put("categoryId","categoryNotfound");
            ErrorDTO errorDTO = new ErrorDTO(Instant.now().atZone(ZoneId.of("Asia/Bangkok")).toString(), HttpStatus.NOT_FOUND.value(), ((ServletWebRequest) request).getRequest().getRequestURI(), "Validation failed", fieldError);
            return new ResponseEntity(errorDTO,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(modelMapper.map(repository.findById(id).get(), EventCategoryDto.class),HttpStatus.OK);
    }
    public ResponseEntity getEventByCategoryId(int id,WebRequest request){
        Map<String,String> fieldError = new HashMap<>();
        if (repository.findById(id).isEmpty()){
            fieldError.put("categoryId","categoryNotfound");
            ErrorDTO errorDTO = new ErrorDTO(Instant.now().atZone(ZoneId.of("Asia/Bangkok")).toString(), HttpStatus.NOT_FOUND.value(), ((ServletWebRequest) request).getRequest().getRequestURI(), "Validation failed", fieldError);
            return new ResponseEntity(errorDTO,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(listMapper.mapList(eventbookingRepository.findEventbookingByEventCategoryId(repository.getById(id)),EventbookingDto.class,modelMapper),HttpStatus.OK);
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
            ErrorDTO errorBody = new ErrorDTO(Instant.now().atZone(ZoneId.of("Asia/Bangkok")).toString(), HttpStatus.BAD_REQUEST.value(), ((ServletWebRequest) request).getRequest().getRequestURI(), "Validation failed", fieldError);
            return new ResponseEntity(errorBody,HttpStatus.BAD_REQUEST);
        }
        Eventcategory event = repository.findById(id).get();
        modelMapper.map(updateEventCategory,event);
        return new ResponseEntity(modelMapper.map(repository.saveAndFlush(event),EventCategoryDto.class),HttpStatus.OK);
    }
    public ResponseEntity delete(int id,WebRequest request){
        Map<String,String> fieldError = new HashMap<>();
        if (repository.findById(id).isEmpty()){
            fieldError.put("categoryId","categoryNotfound");
            ErrorDTO errorDTO = new ErrorDTO(Instant.now().atZone(ZoneId.of("Asia/Bangkok")).toString(), HttpStatus.NOT_FOUND.value(), ((ServletWebRequest) request).getRequest().getRequestURI(), "Validation failed", fieldError);
            return new ResponseEntity(errorDTO,HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity("Deleted id "+id, HttpStatus.OK);
    }
}
