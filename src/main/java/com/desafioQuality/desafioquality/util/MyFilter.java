package com.desafioQuality.desafioquality.util;

import com.desafioQuality.desafioquality.dto.FlightDTO;
import com.desafioQuality.desafioquality.dto.IFiltered;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MyFilter {

    public static List<? extends IFiltered> filterByDateFrom(Date dateFrom, List<? extends IFiltered> flights){
        return flights
                .stream()
                .filter(h -> h.getDateFrom().after(dateFrom) || h.getDateFrom().equals(dateFrom))
                .collect(Collectors.toList());
    }
    
    public static List<? extends IFiltered> filterByDateTo(Date dateTo, List<? extends IFiltered> flights){
        return flights
                .stream()
                .filter(h -> h.getDateTo().before(dateTo) || h.getDateTo().equals(dateTo))
                .collect(Collectors.toList());
    }

    public static List<? extends IFiltered> filterByDestination(String destination, List<? extends IFiltered> flights){
        return flights
                .stream()
                .filter(h -> h.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }

    public static List<FlightDTO> filterByOrigin(String origin, List<FlightDTO> flights){
        return flights
                .stream()
                .filter(h -> h.getOrigin().equalsIgnoreCase(origin))
                .collect(Collectors.toList());
    }
}
