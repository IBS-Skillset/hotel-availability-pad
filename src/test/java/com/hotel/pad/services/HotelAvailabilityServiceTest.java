package com.hotel.pad.services;

import com.hotel.pad.client.HotelAvailabilityClient;
import com.hotel.pad.mappers.request.AvailRequestSegmentsMapper;
import com.hotel.pad.mappers.request.PosMapper;
import com.hotel.pad.mappers.response.HotelAvailabilityResponseMapper;
import com.hotel.service.availability.*;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.*;

import javax.xml.bind.JAXBException;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
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


    @After
    public void tearDown() {
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

    @Test(expected = Exception.class)
    public void testForException() {
        OTAHotelAvailRQ hotelAvailRQ = new OTAHotelAvailRQ();
        OTAHotelAvailRS hotelAvailRS = new OTAHotelAvailRS();
        HotelAvailabilityRequest request = HotelAvailabilityRequest.newBuilder().build();
    try {
            lenient().when(hotelAvailabilityClient.restClient(hotelAvailRQ,request)).thenReturn(hotelAvailRS);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        when(hotelAvailabilityResponseMapper.map(hotelAvailRS)).thenThrow(Exception.class);
        HotelAvailabilityResponse response = hotelAvailabilityService.getAvailableHotelItemsFromSupplier(request);

    }

}
