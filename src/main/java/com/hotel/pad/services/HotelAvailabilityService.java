package com.hotel.pad.services;


import com.hotel.pad.client.HotelAvailabilityClient;
import com.hotel.pad.mappers.request.AvailRequestSegmentsMapper;
import com.hotel.pad.mappers.request.PosMapper;
import com.hotel.pad.mappers.response.HotelAvailabilityResponseMapper;
import com.hotel.service.availability.HotelAvailabilityRequest;
import com.hotel.service.availability.HotelAvailabilityResponse;
import lombok.extern.slf4j.Slf4j;
import org.opentravel.ota._2003._05.OTAHotelAvailRQ;
import org.opentravel.ota._2003._05.OTAHotelAvailRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class HotelAvailabilityService {

    @Autowired
    private PosMapper posMapper;
    @Autowired
    private AvailRequestSegmentsMapper availRequestSegmentsMapper;
    @Autowired
    private HotelAvailabilityClient hotelAvailabilityClient;
    @Autowired
    private HotelAvailabilityResponseMapper hotelAvailabilityResponseMapper;

    public HotelAvailabilityResponse getAvailableHotelItemsFromSupplier(HotelAvailabilityRequest request){
        OTAHotelAvailRQ hotelAvailRQ = new OTAHotelAvailRQ();
        hotelAvailRQ.setPOS(posMapper.mapPOS(request));
        hotelAvailRQ.setAvailRequestSegments(availRequestSegmentsMapper.mapAvailRequestSegments(request));
        OTAHotelAvailRS hotelAvailRS = new OTAHotelAvailRS();
        try {
            Object response = hotelAvailabilityClient.restClient(hotelAvailRQ, request);
            if (Objects.nonNull(response)) {
                hotelAvailRS = (OTAHotelAvailRS) response;
            }
            log.info("Successful OTA hotel Avail Response", hotelAvailRS);
            return hotelAvailabilityResponseMapper.map(hotelAvailRS);
        } catch (Exception e) {
            log.info("Error while retrieving the HotelAvail Response" + e);
        }
        return null;
    }
}
