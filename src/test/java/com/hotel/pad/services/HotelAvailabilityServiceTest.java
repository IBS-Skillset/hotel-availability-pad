package com.hotel.pad.services;

import com.hotel.pad.client.HotelAvailabilityClient;
import com.hotel.pad.mappers.request.AvailRequestSegmentsMapper;
import com.hotel.pad.mappers.request.PosMapper;
import com.hotel.pad.mappers.response.HotelAvailabilityResponseMapper;
import com.hotel.service.availability.HotelAvailabilityRequest;

import com.hotel.service.availability.HotelAvailabilityResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.OTAHotelAvailRQ;
import org.opentravel.ota._2003._05.OTAHotelAvailRS;
import org.opentravel.ota._2003._05.ArrayOfSourceType;

import javax.xml.bind.JAXBException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.lenient;

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
    public void getAvailableHotelItemsFromSupplier() throws JAXBException {
        OTAHotelAvailRQ hotelAvailRQ = new OTAHotelAvailRQ();
        OTAHotelAvailRS hotelAvailRS = new OTAHotelAvailRS();
        HotelAvailabilityRequest request = HotelAvailabilityRequest.newBuilder().build();
        HotelAvailabilityResponse.Builder hotelAvailabilityResponse = HotelAvailabilityResponse.newBuilder();
        hotelAvailabilityService.getOTAHotelAvailRQ(request);
        when(posMapper.mapPOS(any())).thenReturn(mock(ArrayOfSourceType.class));
        when(availRequestSegmentsMapper.mapAvailRequestSegments(any())).thenReturn(mock(OTAHotelAvailRQ.AvailRequestSegments.class));
        when(hotelAvailabilityClient.restClient(hotelAvailRQ,request)).thenReturn(hotelAvailRS);
        hotelAvailabilityClient.restClient(hotelAvailRQ,request);
        lenient().when(hotelAvailabilityResponseMapper.map(hotelAvailRS)).thenReturn(hotelAvailabilityResponse.build());
        hotelAvailabilityResponseMapper.map(hotelAvailRS);
        HotelAvailabilityResponse response = hotelAvailabilityService.getAvailableHotelItemsFromSupplier(request);
        assertThat(response).isNull();
        verify(hotelAvailabilityClient,atLeast(1)).restClient(hotelAvailRQ,request);
        verify(hotelAvailabilityResponseMapper,atLeast(1)).map(hotelAvailRS);
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
        assertThat(response).isNull();
        verify(hotelAvailabilityResponseMapper,atLeast(1)).map(hotelAvailRS);
    }

    @Test(expected = Exception.class)
    public void getHotelAvailabilityFromSupplierError() throws JAXBException {
        OTAHotelAvailRQ hotelAvailRQ = new OTAHotelAvailRQ();
        HotelAvailabilityRequest request = HotelAvailabilityRequest.newBuilder().build();
        when(hotelAvailabilityClient.restClient(hotelAvailRQ, request)).thenThrow(JAXBException.class);
        hotelAvailabilityClient.restClient(hotelAvailRQ, request);
        HotelAvailabilityResponse response = hotelAvailabilityService.getAvailableHotelItemsFromSupplier(request);
        assertThat(response).isNull();
    }

}
