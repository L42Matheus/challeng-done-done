package net.intelie.challenges.controller;

import net.intelie.challenges.entity.Event;
import net.intelie.challenges.service.impl.EventStoreImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventStoreImpl eventStore;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event insert(@RequestBody Event event){
      return eventStore.insert(event);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam String type){
        eventStore.removeAll(type);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Event> get(@PathVariable Long id){
        return eventStore.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Event> get(@RequestParam String type,@RequestParam long startTime, @RequestParam long endTime){
        return eventStore.query(type, startTime, endTime);
    }
}
