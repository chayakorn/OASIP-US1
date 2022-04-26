package oasip.us1.backend.service;

import oasip.us1.backend.entity.Eventcategory;
import oasip.us1.backend.repository.EventcategoryRepository;
import oasip.us1.backend.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventcategoryService {
    @Autowired
    private EventcategoryRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Eventcategory> getAllEventCategory(){
        return repository.findAll();
    }
    public Eventcategory getEventCategoryById(int id){
        return repository.findById(id).get();
    }
    public Eventcategory update(Eventcategory updateEventCategory , int id){
        Eventcategory event = repository.findById(id).get();
        modelMapper.map(updateEventCategory,event);
        return repository.saveAndFlush(event);
    }
}
