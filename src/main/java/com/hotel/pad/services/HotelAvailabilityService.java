package com.hotel.pad.services;


import com.hotel.pad.client.HotelAvailabilityClient;
import com.hotel.pad.mappers.AvailRequestSegmentsMapper;
import com.hotel.pad.mappers.PosMapper;
import com.hotel.service.availability.HotelAvailabilityRequest;
import lombok.extern.slf4j.Slf4j;
import org.opentravel.ota._2003._05.OTAHotelAvailRQ;
import org.opentravel.ota._2003._05.OTAHotelAvailRS;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class HotelAvailabilityService {

    private final PosMapper posMapper;
    private final AvailRequestSegmentsMapper availRequestSegmentsMapper;
    private final HotelAvailabilityClient hotelAvailabilityClient;


    public HotelAvailabilityService(PosMapper posMapper, AvailRequestSegmentsMapper availRequestSegmentsMapper, HotelAvailabilityClient hotelAvailabilityClient) {
        this.posMapper = posMapper;
        this.availRequestSegmentsMapper = availRequestSegmentsMapper;
        this.hotelAvailabilityClient = hotelAvailabilityClient;
    }


    public void getAvailableHotelItemsFromSupplier(HotelAvailabilityRequest request){
        OTAHotelAvailRQ hotelAvailRQ = new OTAHotelAvailRQ();
        hotelAvailRQ.setPOS(posMapper.mapPOS(request));
        hotelAvailRQ.setAvailRequestSegments(availRequestSegmentsMapper.mapAvailRequestSegments(request));
        OTAHotelAvailRS hotelAvailRS = new OTAHotelAvailRS();
        try {
          Object response =  hotelAvailabilityClient.restClient(hotelAvailRQ,request);
            if(Objects.nonNull(response)){
                hotelAvailRS = (OTAHotelAvailRS) response;
            }
                log.info("Successful OTA hotel Avail Response",hotelAvailRS);
        } catch (Exception e) {
            log.info("Error while retrieving the HotelAvail Response" +e);
        }
    }
}
