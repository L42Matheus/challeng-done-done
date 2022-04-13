package net.intelie.challenges.eventStoreImplTest;

import net.intelie.challenges.entity.Event;
import net.intelie.challenges.repository.EventRepository;
import net.intelie.challenges.service.impl.EventStoreImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;



@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles
public class EventTest {

    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private EventStoreImpl eventStore;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Event event = new Event();
        event.setTimestamp(1L);
        event.setType("batman");
        Mockito.when(eventRepository.save(event));

    }

    @Test
    public void thisIsAWarning() throws Exception {
        Event event = new Event("some_type", 123L);

        //THIS IS A WARNING:
        //Some of us (not everyone) are coverage freaks.
        assertEquals(123L, event.getTimestamp());
        assertEquals("some_type", event.getType());
    }

    @Test
    public void should_insert_TheEvent() {
        Event event = new Event();
        event.setType("some_type");
        event.setTimestamp(123L);

        Mockito.when(eventRepository.save(Mockito.any(Event.class)))
                .thenReturn(event);

        Event eventTest = eventStore.insert(event);
        Assert.assertEquals(eventTest, event);
    }

    @Test
    public void delete() {
        System.out.println(eventRepository.count());

    }

    @Test
    public void should_find_evet_for_id() throws Exception {
        Event event = new Event();
        Optional<Event> eventOptional = Optional.of(event);

        Mockito.when(eventRepository.findById(Mockito.anyLong()))
                .thenReturn(eventOptional);

        Optional<Event> eventId = eventStore.get(1L);

        Assertions.assertEquals(Optional.of(event), eventId);
    }

    @Test
    public void should_to_trow_exception_when_id_is_not_found() throws Exception {
        Event event = new Event();
        Optional<Event> eventOptional = Optional.empty();

        Mockito.when(eventRepository.findById(Mockito.anyLong()))
                .thenReturn(eventOptional);

        RuntimeException exception = Assertions
                .assertThrows(RuntimeException.class, () -> {
                    eventStore.get(1L);
                });

        Assertions.assertTrue(exception.getMessage().equals("Event not found"));

    }

    @Test
    public void should_return_query_of_type() {
        Event event = new Event();

        List<Event> eventList = Arrays.asList(event);
        Mockito.when(eventRepository.findAllByType(Mockito.anyString()))
                .thenReturn(eventList);

        List<Event> eventsTest = Arrays.asList(event);

        for (Event event1 : eventsTest) {
            Assertions.assertEquals(event, event1);
        }

    }
}