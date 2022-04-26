package oasip.us1.backend.controller;

import oasip.us1.backend.entity.Eventcategory;
import oasip.us1.backend.service.EventcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventcat")
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
    public Eventcategory update(@RequestBody Eventcategory updateEventcategory, @PathVariable int id){
        return service.update(updateEventcategory,id);
    }
}
