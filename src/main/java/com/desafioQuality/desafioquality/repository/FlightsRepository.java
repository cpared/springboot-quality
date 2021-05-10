package com.desafioQuality.desafioquality.repository;

import com.desafioQuality.desafioquality.dto.FlightDTO;
import com.desafioQuality.desafioquality.dto.HotelDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class FlightsRepository implements IFlightsRepository{
    private static final String FLIGHT_FILE_PATH = "classpath:flights.json";
    private HashMap<String, FlightDTO> flights;

    public FlightsRepository() {
        this.flights = loadFlightsDB();
    }

    @Override
    public List<FlightDTO> getAllFlights() {
        return new ArrayList<>(this.flights.values());
    }

    @Override
    public FlightDTO getFlightByCode(String flightNumber) {
        return this.flights.get(flightNumber);
    }

    @Override
    public boolean validateOriginAndDestination(String anOrigin, String aDestination) {
        for(FlightDTO flight: this.flights.values()){
            if(flight.getDestination().equalsIgnoreCase(aDestination) && flight.getOrigin().equalsIgnoreCase(anOrigin)) return true;
        }
        return false;
    }

    private HashMap<String, FlightDTO> loadFlightsDB(){
        List<FlightDTO> flights = new ArrayList<>();
        HashMap<String, FlightDTO> map = new HashMap<>();
        try {
            ObjectMapper mapper = new ObjectMapper();

            flights = mapper.readValue(ResourceUtils.getFile(FLIGHT_FILE_PATH), new TypeReference<>() {});

            for(FlightDTO flight: flights){
                map.put(flight.getFlightNumber(), flight);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }
}
