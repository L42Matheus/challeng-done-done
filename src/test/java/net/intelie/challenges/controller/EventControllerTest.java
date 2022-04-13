package net.intelie.challenges.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.intelie.challenges.entity.Event;
import net.intelie.challenges.service.impl.EventStoreImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class EventControllerTest {


    @MockBean
    private EventStoreImpl eventStoreService;
    @Autowired
    private MockMvc mockMvc;
    private Event event;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        event = new Event();
        event.setType("house");
        event.setTimestamp(1L);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void should_insert_with_success() throws Exception {

        when(eventStoreService.insert(Mockito.any()))
                .thenReturn(event);

        String json = objectMapper.writeValueAsString(event);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }
//    @Test
//    public void should_list_of_types_with_success() throws Exception {
//
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/events")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    public void should_delete_event_with_success() throws Exception {
//
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/events/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNoContent());
//    }
}