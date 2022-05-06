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
@RequestMapping("/api/event")
public class EventbookingController {

    @Autowired
    private EventbookingService service;

    @CrossOrigin
    @GetMapping("")
    public List<EventbookingDto> getAllEvent(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin","*");
        return service.getAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    private EventbookingDto getEventById(HttpServletResponse response,@PathVariable int id){
        response.addHeader("Access-Control-Allow-Origin","*");
        return service.getEventById(id);
    }

    @CrossOrigin
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    private Eventbooking addEvent(HttpServletResponse response,@RequestBody Eventbooking newEventBooking){
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Methods","POST");
        return service.save(newEventBooking,response);
    }
    @CrossOrigin
    @PutMapping("/{id}")
    private Eventbooking update(HttpServletResponse response,@RequestBody Eventbooking editEventBooking,@PathVariable int id){
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Methods","PUT");
        return service.update(editEventBooking,id,response);
    }
    @CrossOrigin
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void delete(HttpServletResponse response,@PathVariable int id){
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Methods","DELETE");
        service.delete(id);
    }
}
