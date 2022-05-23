package oasip.us1.backend.controller;

import oasip.us1.backend.DTO.*;
import oasip.us1.backend.service.EventbookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/events")

public class EventbookingController {

    @Autowired
    private EventbookingService service;

    @GetMapping("")
    private EventPageDto getAllEventByCatId(@RequestParam(defaultValue = "a") String uap,
                                            @RequestParam(defaultValue = "[1,2,3,4,5]") Collection<String> catid, @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "12") int pageSize,
                                            @RequestParam(defaultValue = "eventStartTime") String sortBy,
                                            @RequestParam(defaultValue = "true") boolean isAsc) {
        return service.getAllEventByCatId( page, pageSize, sortBy, isAsc,catid,uap);
    }

    @GetMapping("/{id}")
    private ResponseEntity getEventById(@PathVariable int id, WebRequest request) {
        return service.getEventById(id,request);
    }

    @GetMapping("/{id}/categories")
    private ResponseEntity getCategoryByEventId(@PathVariable int id, WebRequest request) {
        return service.getCategoryByEventId(id,request);
    }

    @GetMapping("/by-date-and-cat")
    private ResponseEntity getAllEventByCatAndStartTime(@RequestParam(defaultValue = "1") int catid,@RequestParam String date,@RequestParam(defaultValue = "07:00") String offSet,@RequestParam(defaultValue = "false") boolean negative,WebRequest request){
        return service.getEventByCatAndDate(catid,date,offSet,negative,request);
    }

    @GetMapping("/by-date")
    private ResponseEntity getAllByDate(@RequestParam String date,@RequestParam(defaultValue = "07:00") String offSet,@RequestParam(defaultValue = "false") boolean negative,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "12") int pageSize,
                                      @RequestParam(defaultValue = "eventStartTime") String sortBy,
                                      @RequestParam(defaultValue = "true") boolean isAsc,
                                      WebRequest request){
        return service.getEventByDate(date,offSet,negative,page,pageSize,sortBy,isAsc,request);
    }
    @PostMapping("")
    private ResponseEntity addEvent(@Valid @RequestBody EventbookingInsertDto newEventBooking, BindingResult result, WebRequest request) {
        return service.save(newEventBooking, result, request);
    }

    @PutMapping("/{id}")
    private ResponseEntity update( @RequestBody EventbookingPutDto editEventBooking, @PathVariable int id, WebRequest request) {
        return service.update(editEventBooking, id, request);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity delete(@PathVariable int id, WebRequest request) {
       return service.delete(id,request);
    }
}
