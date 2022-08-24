package com.hotel.pad.mappers.request;

import com.hotel.service.availability.HotelAvailabilityRequest;
import com.hotel.service.common.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.ArrayOfSourceType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class PosMapperTest {

    @InjectMocks
    public PosMapper posMapper;

    @Test
    public void testMapPos() {
        HotelAvailabilityRequest hotelAvailabilityRequest = getAvailabilityRequest();
        ArrayOfSourceType response = posMapper.mapPOS(hotelAvailabilityRequest);
        assertThat(response).isNotNull();
        assertThat(response.getSource().get(0).getRequestorID().getID()).isEqualTo("A");
        assertThat(response.getSource().get(0).getRequestorID().getMessagePassword()).isEqualTo("B");


    }

    public HotelAvailabilityRequest getAvailabilityRequest() {
        HotelAvailabilityRequest.Builder requestBuilder = HotelAvailabilityRequest.newBuilder();
        Context.Builder context = Context.newBuilder();
        requestBuilder.setRequestContext(context.setSupplierRequestorId("A")
                .setSupplierCredential("B")
                .build())
                .setLanguageCode("C");
        return requestBuilder.build();

    }


}