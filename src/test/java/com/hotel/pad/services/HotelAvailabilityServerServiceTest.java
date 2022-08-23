package com.hotel.pad.services;

import com.hotel.service.availability.HotelAvailabilityRequest;
import com.hotel.service.availability.HotelAvailabilityResponse;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class HotelAvailabilityServerServiceTest {
    @InjectMocks
    private HotelAvailabilityServerService hotelAvailabilityServerService;
    @Mock
    private HotelAvailabilityService hotelAvailabilityService;

    @Test
    public void testHotelItem() {
        HotelAvailabilityRequest.Builder request = HotelAvailabilityRequest.newBuilder();
        request.setLatitude(1.234)
                .setLongitude(1.44)
                .build();
        StreamRecorder<HotelAvailabilityResponse> responseObserver = StreamRecorder.create();
        hotelAvailabilityServerService.getHotelItem(request.build(),responseObserver);
        List<HotelAvailabilityResponse> responseList = responseObserver.getValues();
        HotelAvailabilityResponse hotelAvailabilityResponse = responseList.get(0);
        assertThat(responseList).isNotEmpty();
        assertThat(hotelAvailabilityResponse).isNull();

    }
}