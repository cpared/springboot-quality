package com.desafioQuality.desafioquality.apiTest;


import com.desafioQuality.desafioquality.apiTest.util.DataHandler;
import com.desafioQuality.desafioquality.apiTest.util.DateFormatter;
import com.desafioQuality.desafioquality.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiFlightTest {

    private static final String URLPATH = "http://localhost:8080/api/v1/";
    private static final ObjectMapper mapper = new ObjectMapper();
    List<FlightDTO> flights = DataHandler.loadFlightDB();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCallTheApiWithInvalidPathAndGetNotFoundException() throws Exception {
        //ARRANGE
        String methodPath = "flig";

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testFilterFlightsByDateAndGetStatusCode200() throws Exception {
        //ARRANGE
        String methodPath = "flights";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateFrom", "16/04/2021");
        params.add("dateTo", "02/05/2021");
        params.add("origin", "Medellín");
        params.add("destination", "Puerto Iguazú");

        FlightDTO flight = new FlightDTO(
                "MEPI-9986",
                "Medellín",
                "Puerto Iguazú",
                "Business",
                41640.0,
                DateFormatter.format("17/04/2021"),
                DateFormatter.format("02/05/2021"));

        List<FlightDTO> expected = new ArrayList<>();
        expected.add(flight);

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .queryParams(params)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(content().json(mapper.writeValueAsString(expected)))
                .andExpect(status().isOk());
    }

    @Test
    void testFilterFlightsByInvalidDateFromAndGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "flights";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateFrom", "17/20/2021"); //invalid date
        params.add("dateTo", "02/05/2021");
        params.add("origin", "Medellín");
        params.add("destination", "Puerto Iguazú");

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .queryParams(params)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFilterFlightsByInvalidDateToAndGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "flights";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateFrom", "17/4/2021");
        params.add("dateTo", "02/25/2021"); //invalid date
        params.add("origin", "Medellín");
        params.add("destination", "Puerto Iguazú");

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .queryParams(params)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFilterFlightsByInvalidOriginAndGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "flights";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateFrom", "17/4/2021");
        params.add("dateTo", "02/25/2021");
        params.add("origin", "lalala");
        params.add("destination", "Puerto Iguazú");

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .queryParams(params)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFilterFlightsByInvalidDestinationAndGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "flights";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateFrom", "17/4/2021");
        params.add("dateTo", "02/25/2021");
        params.add("origin", "Medellín");
        params.add("destination", "lalala");

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .queryParams(params)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFlightReservationWithInvalidDuesGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "flight-reservation";
        PersonDTO person = new PersonDTO(
                123456789,
                "Pepito",
                "Gomez",
                DateFormatter.format("10/11/1982"),
                "arjonamiguel@gmail.com");
        List<PersonDTO> people = new ArrayList<>();
        people.add(person);

        PaymentMethodDTO payment = new PaymentMethodDTO(
                "CREDIT",
                "4916-5507-8557-8542",
                -2); //Invalid dues

        FlightReservationDTO reservation = new FlightReservationDTO(
                DateFormatter.format("10/02/2021"),
                DateFormatter.format("18/03/2021"),
                "Medellín",
                "Puerto Iguazú",
                "MEPI-9986",
                1,
                "Business",
                people,
                payment
        );

        FlightReservationRequestDTO request = new FlightReservationRequestDTO("arjonamiguel@gmail.com", reservation);

        //ACT //ASSERT
        MvcResult mvcResult = mockMvc.perform(post(URLPATH + methodPath)
                .content(mapper.writeValueAsString(request))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains(" default message [dues must be greater or equals than 1]] "));
    }
}
