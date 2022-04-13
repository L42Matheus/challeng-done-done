package net.intelie.challenges.service.impl;

import net.intelie.challenges.entity.Event;
import net.intelie.challenges.repository.EventRepository;
import net.intelie.challenges.service.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventStoreImpl implements EventStore {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event insert(Event event) {
        if(event == null){
            throw new RuntimeException("Event not null");
        }
        eventRepository.save(event);
        return event;
    }
    @Override
    public Optional<Event> get(Long id) {

        return Optional.ofNullable(eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found")));
    }

    @Override
    public List<Event> query(String type, long startTime, long endTime) {
        List<Event> events = eventRepository.findAllByType(type);

        events.forEach(event -> {
            if (event.getTimestamp() < startTime || event.getTimestamp() > endTime) {
                events.remove(event);
            }
        });

        return events;
    }

    @Override
    @Transactional
    public void removeAll(String type) {
        eventRepository.deleteAllByType(type);
    }


}
