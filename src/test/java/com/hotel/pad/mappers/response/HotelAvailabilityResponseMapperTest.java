package com.hotel.pad.mappers.response;

import com.hotel.service.availability.HotelAvailabilityResponse;
import com.hotel.service.common.AvailableHotelItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.ErrorType;
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.OTAHotelAvailRS;
import org.opentravel.ota._2003._05.SuccessType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@RunWith(MockitoJUnitRunner.class)
public class HotelAvailabilityResponseMapperTest {
    @Mock
    private HotelItemMapper hotelItemMapper;
    @InjectMocks
    private HotelAvailabilityResponseMapper hotelAvailabilityResponseMapper;

    @Test
    public void map() {
        OTAHotelAvailRS response = getResponse();
        AvailableHotelItem.Builder hotelItem = AvailableHotelItem.newBuilder();
        when(hotelItemMapper.map(response.getRoomStays().getRoomStay().get(0))).thenReturn(hotelItem.build());
        HotelAvailabilityResponse availResponse = hotelAvailabilityResponseMapper.map(response);
        assertThat(availResponse).isNotNull();
        assertThat(availResponse.getResponseStatus().getStatus()).isEqualTo(1);
        assertThat(availResponse.getHotelItemList()).isNotEmpty();
        verify(hotelItemMapper, atLeast(1)).map(response.getRoomStays().getRoomStay().get(0));
    }

    @Test
    public void testErrorCase() {
        OTAHotelAvailRS response = new OTAHotelAvailRS();
        ErrorsType errorsType = new ErrorsType();
        ErrorType errorType = new ErrorType();
        errorType.setCode("1234");
        errorType.setValue("No hotels found");
        errorsType.getError().add(errorType);
        response.setErrors(errorsType);
        HotelAvailabilityResponse availResponse = hotelAvailabilityResponseMapper.map(response);
        assertThat(availResponse).isNotNull();
        assertThat(availResponse.getResponseStatus().getStatus()).isEqualTo(0);

    }

    private OTAHotelAvailRS getResponse() {
        OTAHotelAvailRS response = new OTAHotelAvailRS();
        SuccessType successType = new SuccessType();
        response.setSuccess(successType);
        OTAHotelAvailRS.RoomStays roomStays = new OTAHotelAvailRS.RoomStays();
        OTAHotelAvailRS.RoomStays.RoomStay roomStay = new OTAHotelAvailRS.RoomStays.RoomStay();
        roomStays.getRoomStay().add(roomStay);
        response.setRoomStays(roomStays);
        return response;
    }
}