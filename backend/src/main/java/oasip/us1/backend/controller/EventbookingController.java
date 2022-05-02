package oasip.us1.backend.controller;

import oasip.us1.backend.DTO.EventbookingDto;
import oasip.us1.backend.DTO.EventbookingInsertDto;
import oasip.us1.backend.entity.Eventbooking;
import oasip.us1.backend.service.EventbookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventbookingController {

    @Autowired
    private EventbookingService service;

    @GetMapping("")
    public List<EventbookingDto> getAllEvent(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin","*");
        return service.getAll();
    }

    @GetMapping("/{id}")
    private EventbookingDto getEventById(HttpServletResponse response,@PathVariable int id){
        response.addHeader("Access-Control-Allow-Origin","*");
        return service.getEventById(id);
    }

    @PostMapping("")
    private Eventbooking addEvent(HttpServletResponse response,@RequestBody Eventbooking newEventBooking){
        response.addHeader("Access-Control-Allow-Origin","*");
        return service.save(newEventBooking);
    }
    @PutMapping("/{id}")
    private Eventbooking update(HttpServletResponse response,@RequestBody EventbookingDto editEventBooking,@PathVariable int id){
        return service.update(editEventBooking,id);
    }
    @DeleteMapping("/{id}")
    private void delete(HttpServletResponse response,@PathVariable int id){
        service.delete(id);
    }
}
