package com.desafioQuality.desafioquality.apiTest.util;

import com.desafioQuality.desafioquality.dto.FlightDTO;
import com.desafioQuality.desafioquality.dto.HotelDTO;
import com.desafioQuality.desafioquality.dto.PersonDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

public class DataHandler {
    private static final String FLIGHT_FILE_PATH = "classpath:flights.json";
    private static final String HOTEL_FILE_PATH = "classpath:hotel.json";
    private static final String PEOPLE_FILE_PATH = "classpath:people.json";

    public static List<FlightDTO> loadFlightDB(){
        List<FlightDTO> flights = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();

            flights = mapper.readValue(ResourceUtils.getFile(FLIGHT_FILE_PATH), new TypeReference<>() {});

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flights;
    }

    public static List<HotelDTO> loadHotelDB(){
        List<HotelDTO> hotels = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();

            hotels = mapper.readValue(ResourceUtils.getFile(HOTEL_FILE_PATH), new TypeReference<>() {});

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hotels;
    }

    public static List<PersonDTO> loadPeopleDB(){
        List<PersonDTO> people = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();

            people = mapper.readValue(ResourceUtils.getFile(PEOPLE_FILE_PATH), new TypeReference<>() {});

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return people;
    }
}
