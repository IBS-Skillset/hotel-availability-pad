package com.hotel.pad.mappers.response;

import com.hotel.service.availability.HotelAvailabilityResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ErrorResponseMapperTest {
    @InjectMocks
    private ErrorResponseMapper errorResponseMapper;

    @Test
    public void testMapErrorResponse() {
        HotelAvailabilityResponse response = errorResponseMapper.mapErrorResponse("No avail","222");
        assertThat(response).isNotNull();
        assertThat(response.getResponseStatus().getStatus()).isEqualTo(0);
        assertThat(response.getResponseStatus().getErrorCode()).isEqualTo("222");

    }

}
