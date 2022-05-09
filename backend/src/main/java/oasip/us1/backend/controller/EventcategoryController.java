package oasip.us1.backend.controller;

import oasip.us1.backend.entity.Eventcategory;
import oasip.us1.backend.service.EventcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/us1/api/eventcat")
public class EventcategoryController {
    @Autowired
    private EventcategoryService service;

    @GetMapping("")
    public List<Eventcategory> getAllEventCategory(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin","*");
        return service.getAllEventCategory();
    }
    @GetMapping("/{id}")
    public Eventcategory getCategoryById(HttpServletResponse response,@PathVariable int id){
        response.addHeader("Access-Control-Allow-Origin","*");
        return service.getEventCategoryById(id);
    }
    @PutMapping("/{id}")
    public Eventcategory update(HttpServletResponse response,@RequestBody Eventcategory updateEventcategory, @PathVariable int id){
        response.addHeader("Access-Control-Allow-Origin","*");
        return service.update(updateEventcategory,id);
    }
}
