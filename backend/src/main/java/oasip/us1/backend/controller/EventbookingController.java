package oasip.us1.backend.controller;

import oasip.us1.backend.DTO.EventPageDto;
import oasip.us1.backend.DTO.EventbookingDto;
import oasip.us1.backend.DTO.EventbookingInsertDto;
import oasip.us1.backend.DTO.EventbookingPutDto;
import oasip.us1.backend.service.EventbookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/event")

public class EventbookingController {

    @Autowired
    private EventbookingService service;

    @GetMapping("")
    public EventPageDto getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int pageSize,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "true") boolean isAsc) {
        return service.getAllProduct(page, pageSize, sortBy,isAsc);
    }

    @GetMapping("/{id}")
    private EventbookingDto getEventById(@PathVariable int id) {
        return service.getEventById(id);
    }

    @GetMapping("/byCat")
    private EventPageDto getAllEventByCatId(
            @RequestBody Map<String,Collection<String>> catid, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int pageSize,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(defaultValue = "true") boolean isAsc) {
        return service.getAllEventByCatId( page, pageSize, sortBy, isAsc,catid);
    }
    @GetMapping("/byDateAndCat")
    private List<EventbookingDto> getAllEventByCatAndStartTime(@RequestParam(defaultValue = "1") int catid,@RequestParam String date){
        return service.getEventByCatAndDate(catid,date);

    }

    @PostMapping("")
    private ResponseEntity addEvent(@Valid @RequestBody EventbookingInsertDto newEventBooking, BindingResult result, WebRequest request) {
        return service.save(newEventBooking, result, request);
    }

    @PutMapping("/{id}")
    private ResponseEntity update(@RequestBody EventbookingPutDto editEventBooking, @PathVariable int id, BindingResult result, WebRequest request) {
        System.out.println("what");

        return service.update(editEventBooking, id, result, request);
    }


    @DeleteMapping("/{id}")
    private void delete(@PathVariable int id) {
        service.delete(id);
    }
}
