package com.desafioQuality.desafioquality.repository;

import com.desafioQuality.desafioquality.dto.HotelDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class HotelRepository implements IHotelRepository{
    private static final String HOTEL_FILE_PATH = "classpath:hotel.json";
    private HashMap<String, HotelDTO> hotels;
    private HashMap<String, Integer> roomType = new HashMap<String, Integer>();

    public HotelRepository() {
        roomType.put("Single", 1);
        roomType.put("Double", 2);
        roomType.put("Triple", 3);
        roomType.put("Múltiple", 4);
        hotels = loadHotelDB();
    }

    @Override
    public List<HotelDTO> getAllTheHotels(){
        return new ArrayList<>(this.hotels.values()) ;
    }

    @Override
    public HotelDTO getHotelByCode(String code) {
        return this.hotels.get(code);
    }

    @Override
    public Integer getHotelRooms(HotelDTO hotel){
        return this.roomType.get(hotel.getRoomType());
    }

    @Override
    public boolean isValidDestination(String aDestination){
        for(HotelDTO hotel: this.hotels.values()){
            if(hotel.getDestination().equalsIgnoreCase(aDestination)) return true;
        }
        return false;
    }

    private HashMap<String, HotelDTO> loadHotelDB(){
        List<HotelDTO> hotels = new ArrayList<>();
        HashMap<String, HotelDTO> map = new HashMap<>();
        try {
            ObjectMapper mapper = new ObjectMapper();

            hotels = mapper.readValue(ResourceUtils.getFile(HOTEL_FILE_PATH), new TypeReference<>() {});

            for(HotelDTO hotel: hotels){
                map.put(hotel.getHotelCode(), hotel);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }
}
