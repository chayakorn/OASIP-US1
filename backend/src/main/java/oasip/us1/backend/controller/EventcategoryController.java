package oasip.us1.backend.controller;

import oasip.us1.backend.DTO.EventCategoryPutDto;
import oasip.us1.backend.entity.Eventcategory;
import oasip.us1.backend.service.EventcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/event-catagories")
@CrossOrigin
public class EventcategoryController {
    @Autowired
    private EventcategoryService service;

    @GetMapping("")
    public List<Eventcategory> getAllEventCategory(){
        return service.getAllEventCategory();
    }
    @GetMapping("/{id}")
    public Eventcategory getCategoryById(@PathVariable int id){
        return service.getEventCategoryById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody EventCategoryPutDto updateEventcategory, BindingResult result, @PathVariable int id, WebRequest request){
        return service.update(updateEventcategory,result,id, request);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id){
        return service.delete(id);
    }
}
