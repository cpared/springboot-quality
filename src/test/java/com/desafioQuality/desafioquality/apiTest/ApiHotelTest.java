package com.desafioQuality.desafioquality.apiTest;


import com.desafioQuality.desafioquality.apiTest.util.DataHandler;
import com.desafioQuality.desafioquality.apiTest.util.DateFormatter;
import com.desafioQuality.desafioquality.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
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
public class ApiHotelTest {
    private static final String URLPATH = "http://localhost:8080/api/v1/";
    private static final ObjectMapper mapper = new ObjectMapper();
    List<HotelDTO> hotels = DataHandler.loadHotelDB();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCallTheApiWithInvalidPathAndGetNotFoundException() throws Exception {
        //ARRANGE
        String methodPath = "hote";

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllTheHotels() throws Exception {
        //ARRANGE
        String methodPath = "hotels";

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(content().json(mapper.writeValueAsString(hotels)))
                .andExpect(status().isOk());
    }

    @Test
    void testFilterHotelsByDateAndGetStatusCode200() throws Exception {
        //ARRANGE
        String methodPath = "hotels";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateFrom", "10/02/2021");
        params.add("dateTo", "20/04/2021");
        params.add("destination", "Medellín");

        HotelDTO centralPlaza = new HotelDTO(
                "CP-0004",
                "Central Plaza",
                DateFormatter.format("01/03/2021"),
                DateFormatter.format("17/04/2021"),
                "Múltiple",
                "Medellín",
                8600.0,
                false);

        HotelDTO centralPlaza2 = new HotelDTO(
                "CP-0002",
                "Central Plaza 2",
                DateFormatter.format("10/02/2021"),
                DateFormatter.format("20/03/2021"),
                "Doble",
                "Medellín",
                6400.0,
                false);

        List<HotelDTO> expected = new ArrayList<>();
        expected.add(centralPlaza);
        expected.add(centralPlaza2);

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .queryParams(params)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(content().json(mapper.writeValueAsString(expected)))
                .andExpect(status().isOk());
    }

    @Test
    void testFilterHotelsWithAnInvalidDateFromAndGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "hotels";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateFrom", "01/15/2021"); //Invalid date
        params.add("dateTo", "17/04/2021");
        params.add("destination", "Medellín");

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .queryParams(params)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFilterHotelsWithAnInvalidDateToAndGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "hotels";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateFrom", "01/10/2021");
        params.add("dateTo", "17/20/2021"); //Invalid date
        params.add("destination", "Medellín");

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .queryParams(params)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFilterHotelsWithPastDateFromAndGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "hotels";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateFrom", "01/01/2021"); //Invalid date
        params.add("dateTo", "17/20/2021");
        params.add("destination", "Medellín");

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .queryParams(params)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFilterHotelsWithPastDateToAndGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "hotels";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateFrom", "01/10/2021");
        params.add("dateTo", "17/20/2020"); //Invalid date
        params.add("destination", "Medellín");

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .queryParams(params)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFilterHotelsWithEmptyDestinationFromAndGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "hotels";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateFrom", "01/15/2021"); //Invalid date
        params.add("dateTo", "17/04/2021");
        params.add("destination", null);

        //ACT //ASSERT
        mockMvc.perform(get(URLPATH + methodPath)
                .queryParams(params)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testBookingWithNullUserNameGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "booking";
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
                "1234-1234-1234-1234",
                6);

        BookingDTO booking = new BookingDTO(
                DateFormatter.format("10/02/2021"),
                DateFormatter.format("18/03/2021"),
                "Buenos Aires",
                "HB-0001",
                1,
                "Single",
                people,
                payment);

        BookingRequestDTO request = new BookingRequestDTO(null, booking);

        //ACT //ASSERT
        mockMvc.perform(post(URLPATH + methodPath)
                .content(mapper.writeValueAsString(request))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testBookingWithInvalidEmailUserNameGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "booking";
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
                6);

        BookingDTO booking = new BookingDTO(
                DateFormatter.format("10/02/2021"),
                DateFormatter.format("18/03/2021"),
                "Buenos Aires",
                "HB-0001",
                1,
                "Single",
                people,
                payment);

        BookingRequestDTO request = new BookingRequestDTO("arjonamiguel", booking);


        //ACT //ASSERT
        mockMvc.perform(post(URLPATH + methodPath)
                .content(mapper.writeValueAsString(request))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testBookingWithNullBookingDTOGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "booking";
        BookingRequestDTO request = new BookingRequestDTO("arjonamiguel@gmail.com", null);


        //ACT //ASSERT
        mockMvc.perform(post(URLPATH + methodPath)
                .content(mapper.writeValueAsString(request))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testBookingWithInvalidCreditCardGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "booking";
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
                "1234-1234-1234-1234", //Invalid credit card. I validate with Luhn's Algorithm
                6);

        BookingDTO booking = new BookingDTO(
                DateFormatter.format("10/02/2021"),
                DateFormatter.format("18/03/2021"),
                "Buenos Aires",
                "HB-0001",
                1,
                "Single",
                people,
                payment);

        BookingRequestDTO request = new BookingRequestDTO("arjonamiguel@gmail.com", booking);


        //ACT //ASSERT
        mockMvc.perform(post(URLPATH + methodPath)
                .content(mapper.writeValueAsString(request))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testBookingWithInvalidDuesGetBadRequest() throws Exception {
        //ARRANGE
        String methodPath = "booking";
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

        BookingDTO booking = new BookingDTO(
                DateFormatter.format("10/02/2021"),
                DateFormatter.format("18/03/2021"),
                "Buenos Aires",
                "HB-0001",
                1,
                "Single",
                people,
                payment);

        BookingRequestDTO request = new BookingRequestDTO("arjonamiguel@gmail.com", booking);


        //ACT //ASSERT
        mockMvc.perform(post(URLPATH + methodPath)
                .content(mapper.writeValueAsString(request))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
