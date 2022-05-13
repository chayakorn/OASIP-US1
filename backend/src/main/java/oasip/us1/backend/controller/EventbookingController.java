package oasip.us1.backend.controller;

import oasip.us1.backend.DTO.EventbookingDto;
import oasip.us1.backend.DTO.EventbookingInsertDto;
import oasip.us1.backend.entity.Eventbooking;
import oasip.us1.backend.service.EventbookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity addEvent(@RequestBody Eventbooking newEventBooking){
        return service.save(newEventBooking);
    }

    @PutMapping("/{id}")
    private ResponseEntity update(@RequestBody Eventbooking editEventBooking,@PathVariable int id){
        return service.update(editEventBooking,id);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable int id){
        service.delete(id);
    }
}
