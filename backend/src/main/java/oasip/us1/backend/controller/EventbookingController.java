package oasip.us1.backend.controller;

import oasip.us1.backend.DTO.EventbookingDto;
import oasip.us1.backend.DTO.EventbookingPutDto;
import oasip.us1.backend.entity.Eventbooking;
import oasip.us1.backend.service.EventbookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/event")

public class EventbookingController {

    @Autowired
    private EventbookingService service;

    @GetMapping("")
    public List<EventbookingDto> getAllEvent(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    private EventbookingDto getEventById(@PathVariable int id){
        return service.getEventById(id);
    }

    @PutMapping("/{id}")
    private ResponseEntity update( @RequestBody EventbookingPutDto editEventBooking, @PathVariable int id, BindingResult result, WebRequest request){
        System.out.println("what");
        return service.update(editEventBooking,id,result,request);
    }
    @PostMapping("")
    private ResponseEntity addEvent(@Valid @RequestBody Eventbooking newEventBooking, BindingResult result, WebRequest request){
        return service.save(newEventBooking,result,request);
    }



    @DeleteMapping("/{id}")
    private void delete(@PathVariable int id){
        service.delete(id);
    }
}
