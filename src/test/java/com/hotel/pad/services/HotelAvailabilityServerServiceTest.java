package com.hotel.pad.services;

import com.hotel.pad.exception.HotelException;
import com.hotel.pad.mappers.response.ErrorResponseMapper;
import com.hotel.service.availability.HotelAvailabilityRequest;
import com.hotel.service.availability.HotelAvailabilityResponse;
import com.hotel.service.common.ResponseStatus;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class HotelAvailabilityServerServiceTest {
    @InjectMocks
    private HotelAvailabilityServerService hotelAvailabilityServerService;
    @Mock
    private HotelAvailabilityService hotelAvailabilityService;
    @Mock
    private ErrorResponseMapper errorResponseMapper;

    @Test
    public void testHotelItem() {
        HotelAvailabilityRequest.Builder request = HotelAvailabilityRequest.newBuilder();
        request.setLatitude(1.234)
                .setLongitude(1.44)
                .build();
        HotelAvailabilityResponse response = HotelAvailabilityResponse.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(1).build()).build();
        StreamRecorder<HotelAvailabilityResponse> responseObserver = StreamRecorder.create();
        when(hotelAvailabilityService.getAvailableHotelItemsFromSupplier(request.build())).thenReturn(response);
        hotelAvailabilityServerService.getHotelItem(request.build(),responseObserver);
        List<HotelAvailabilityResponse> responseList = responseObserver.getValues();
        HotelAvailabilityResponse hotelAvailabilityResponse = responseList.get(0);
        assertThat(responseList).isNotEmpty();
        assertThat(hotelAvailabilityResponse).isNotNull();
        assertThat(response.getResponseStatus().getStatus()).isEqualTo(1);
        verify(hotelAvailabilityService, atLeast(1)).getAvailableHotelItemsFromSupplier(request.build());

    }

    @Test
    public void testHotelItemException() {
        HotelAvailabilityRequest.Builder request = HotelAvailabilityRequest.newBuilder();
        request.setLatitude(1.234)
                .setLongitude(1.44)
                .build();
        StreamRecorder<HotelAvailabilityResponse> responseObserver = StreamRecorder.create();
        when(hotelAvailabilityService.getAvailableHotelItemsFromSupplier(request.build())).thenThrow(HotelException.class);
        lenient().when(errorResponseMapper.mapErrorResponse(anyString(),anyString())).thenReturn(HotelAvailabilityResponse.newBuilder().build());
        hotelAvailabilityServerService.getHotelItem(request.build(),responseObserver);
        List<HotelAvailabilityResponse> responseList = responseObserver.getValues();
        HotelAvailabilityResponse hotelAvailabilityResponse = responseList.get(0);
        assertThat(responseList).isNotEmpty();
        assertThat(hotelAvailabilityResponse).isNull();

    }
}