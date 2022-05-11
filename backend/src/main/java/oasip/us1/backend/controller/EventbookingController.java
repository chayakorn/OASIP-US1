package oasip.us1.backend.controller;

import oasip.us1.backend.DTO.EventbookingDto;
import oasip.us1.backend.DTO.EventbookingInsertDto;
import oasip.us1.backend.entity.Eventbooking;
import oasip.us1.backend.service.EventbookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<EventbookingDto> getAllEvent(HttpServletResponse response){
        return service.getAll();
    }

    @GetMapping("/{id}")
    private EventbookingDto getEventById(HttpServletResponse response,@PathVariable int id){
        return service.getEventById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    private Eventbooking addEvent(HttpServletResponse response,@RequestBody Eventbooking newEventBooking){
        return service.save(newEventBooking,response);
    }

    @PutMapping("/{id}")
    private Eventbooking update(HttpServletResponse response,@RequestBody Eventbooking editEventBooking,@PathVariable int id){
        return service.update(editEventBooking,id,response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void delete(HttpServletResponse response,@PathVariable int id){
        service.delete(id);
    }
}
