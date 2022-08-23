package com.hotel.pad.mappers.response;

import com.hotel.service.common.Address;
import com.hotel.service.common.AvailableHotelItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.AddressInfoType;
import org.opentravel.ota._2003._05.BasicPropertyInfoType;
import org.opentravel.ota._2003._05.OTAHotelAvailRS;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.Rate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HotelItemMapperTest {

    @InjectMocks
    private HotelItemMapper hotelItemMapper;

    @Mock
    private AddressMapper addressMapper;

    @Test
    public void testMap() {
        OTAHotelAvailRS.RoomStays.RoomStay roomStay = getRoomStay();
        Address.Builder address = Address.newBuilder();
        when(addressMapper.map(roomStay.getBasicPropertyInfo().getAddress())).thenReturn(address.build());
        AvailableHotelItem response = hotelItemMapper.map(roomStay);
        assertThat(response).isNotNull();
        assertThat(response.getLatitude()).isEqualTo(1.234);
        assertThat(response.getMinPrice()).isEqualTo(127.0);
        assertThat(response.getLongitude()).isEqualTo(2.678);
        assertThat(response.getBreakfast()).isFalse();
        verify(addressMapper,atLeast(1)).map(any());
    }

    @Test
    public void testMapWithBreakfast() {
        OTAHotelAvailRS.RoomStays.RoomStay roomStay = getRoomStay();
        Address.Builder address = Address.newBuilder();
        when(addressMapper.map(roomStay.getBasicPropertyInfo().getAddress())).thenReturn(address.build());
        roomStay.getTPAExtensions().setBreakfastIncluded("true");
        AvailableHotelItem response = hotelItemMapper.map(roomStay);
        assertThat(response).isNotNull();
        assertThat(response.getBreakfast()).isTrue();
        verify(addressMapper,atLeast(1)).map(any());

    }

    private OTAHotelAvailRS.RoomStays.RoomStay getRoomStay() {
        OTAHotelAvailRS.RoomStays.RoomStay roomStay = new OTAHotelAvailRS.RoomStays.RoomStay();
        BasicPropertyInfoType basicPropertyInfoType = new BasicPropertyInfoType();
        basicPropertyInfoType.setHotelCode("1234");
        basicPropertyInfoType.setHotelName("Hotel Paris");
        AddressInfoType addressInfoType = new AddressInfoType();
        basicPropertyInfoType.setAddress(addressInfoType);
        basicPropertyInfoType.setHotelCityCode("FR");
        BasicPropertyInfoType.Position position = new BasicPropertyInfoType.Position();
        position.setLatitude("1.234");
        position.setLongitude("2.678");
        basicPropertyInfoType.setPosition(position);
        TPAExtensionsType tpaExtensionsType = new TPAExtensionsType();
        tpaExtensionsType.setStarRating(4);
        Rate rate = new Rate();
        rate.setCurrencyCode("EUR");
        rate.setAmount("127");
        tpaExtensionsType.setMinRate(rate);
        roomStay.setTPAExtensions(tpaExtensionsType);
        roomStay.setBasicPropertyInfo(basicPropertyInfoType);

        return roomStay;
    }

}
