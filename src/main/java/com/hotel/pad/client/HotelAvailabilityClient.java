package com.hotel.pad.client;

import com.hotel.service.availability.HotelAvailabilityRequest;
import org.opentravel.ota._2003._05.OTAHotelAvailRQ;
import org.opentravel.ota._2003._05.OTAHotelAvailRS;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class HotelAvailabilityClient {

    private final String service = "hotel-availability";

    public OTAHotelAvailRS restClient(OTAHotelAvailRQ hotelAvailRQ){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(HotelAvailabilityRequest.newBuilder().getRequestContext().getSupplierUrl());
        uriBuilder.path(service);
        return restTemplate.postForObject(uriBuilder.build().toUriString(),hotelAvailRQ,OTAHotelAvailRS.class);
    }

}
