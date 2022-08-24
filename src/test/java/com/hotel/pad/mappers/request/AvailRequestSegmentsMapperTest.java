package com.hotel.pad.mappers.request;

import com.hotel.service.availability.HotelAvailabilityRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.OTAHotelAvailRQ;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class AvailRequestSegmentsMapperTest {

    @InjectMocks
    public AvailRequestSegmentsMapper availRequestSegmentsMapper;

    @Test
    public void testMapAvailRequestSegments() {
        HotelAvailabilityRequest hotelAvailabilityRequest = getAvailabilityRequest();
        OTAHotelAvailRQ.AvailRequestSegments response = availRequestSegmentsMapper.mapAvailRequestSegments(hotelAvailabilityRequest);
        assertThat(response).isNotNull();
        assertThat(response.getAvailRequestSegment().get(0).getStayDateRange().getStart()).isEqualTo("12 aug");
        assertThat(response.getAvailRequestSegment().get(0).getStayDateRange().getEnd()).isEqualTo("15 sep");


    }

    public HotelAvailabilityRequest getAvailabilityRequest() {
        HotelAvailabilityRequest.Builder requestBuilder = HotelAvailabilityRequest.newBuilder();
        requestBuilder.setStartDate("12 aug")
                .setEndDate("15 sep")
                .setRoomCount(1)
                .setOccupancy(10)
                .setLatitude(111111.00)
                .setLongitude(222222.00)
                .setCountryCode("ABC");

        return requestBuilder.build();

    }

}
