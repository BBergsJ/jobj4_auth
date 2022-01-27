package ru.job4j.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.job4j.Jobj4AuthApplication;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Jobj4AuthApplication.class)
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void whenFindAll() throws Exception {
        when(personRepository.findAll())
                .thenReturn(List.of(
                        Person.of(1, "LOGIN_1", "PASS_1"),
                        Person.of(2, "LOGIN_2", "PASS_2")));

        mockMvc.perform(get("/person/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].login", is("LOGIN_1")))
                .andExpect(jsonPath("$[0].password", is("PASS_1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].login", is("LOGIN_2")))
                .andExpect(jsonPath("$[1].password", is("PASS_2")));
        verify(personRepository).findAll();
    }

    @Test
    void whenFindById() throws Exception {
        when(personRepository.findById(anyInt()))
                .thenReturn(Optional.of(Person.of(1, "LOGIN_1", "PASS_1")));

        mockMvc.perform(get("/person/{id}", anyInt()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.login", is("LOGIN_1")))
                .andExpect(jsonPath("$.password", is("PASS_1")));
        verify(personRepository).findById(anyInt());
    }

    @Test
    void whenPostPerson() throws Exception {
        Person person = Person.of(1,  "LOGIN_1", "PASS_1");
        when(personRepository.save(any())).thenReturn(person);

        mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.login", is("LOGIN_1")))
                .andExpect(jsonPath("$.password", is("PASS_1")));
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void whenPutPerson() throws Exception {
        Person person = Person.of(1,  "LOGIN_1", "PASS_1");
        when(personRepository.save(any())).thenReturn(person);

        mockMvc.perform(put("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void whenDeletePerson() throws Exception {
        mockMvc.perform(delete("/person/{id}", anyInt()))
                .andExpect(status().is2xxSuccessful());
        verify(personRepository).delete(any(Person.class));
    }
}