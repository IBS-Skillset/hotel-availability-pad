package com.hotel.pad.services;

import com.hotel.pad.client.HotelAvailabilityClient;
import com.hotel.pad.mappers.request.AvailRequestSegmentsMapper;
import com.hotel.pad.mappers.request.PosMapper;
import com.hotel.pad.mappers.response.HotelAvailabilityResponseMapper;
import com.hotel.service.availability.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.*;

import javax.xml.bind.JAXBException;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelAvailabilityServiceTest {

    @Mock
    private PosMapper posMapper;
    @Mock
    private AvailRequestSegmentsMapper availRequestSegmentsMapper;
    @Mock
    private  HotelAvailabilityClient hotelAvailabilityClient;
    @InjectMocks
    private HotelAvailabilityService hotelAvailabilityService;
    @Mock
    private HotelAvailabilityResponseMapper hotelAvailabilityResponseMapper;


    @AfterEach
    void tearDown() {
        posMapper = null;
        availRequestSegmentsMapper = null;
        hotelAvailabilityClient = null;
        hotelAvailabilityResponseMapper = null;
        hotelAvailabilityService = null;
    }

    @Test
    public void getAvailableHotelItemsFromSupplier() {
        OTAHotelAvailRQ hotelAvailRQ = new OTAHotelAvailRQ();
        OTAHotelAvailRS hotelAvailRS = new OTAHotelAvailRS();
        HotelAvailabilityRequest request = HotelAvailabilityRequest.newBuilder().build();
        when(posMapper.mapPOS(any())).thenReturn(mock(ArrayOfSourceType.class));
        when(availRequestSegmentsMapper.mapAvailRequestSegments(any())).thenReturn(mock(OTAHotelAvailRQ.AvailRequestSegments.class));
        try {
           lenient().when(hotelAvailabilityClient.restClient(hotelAvailRQ,request)).thenReturn(hotelAvailRS);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        lenient().when(hotelAvailabilityResponseMapper.map(hotelAvailRS)).thenReturn(HotelAvailabilityResponse.newBuilder().build());
        HotelAvailabilityResponse response = hotelAvailabilityService.getAvailableHotelItemsFromSupplier(request);
        Assertions.assertThat(response).isNull();

    }
}
