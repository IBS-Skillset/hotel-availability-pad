package com.hotel.pad.mappers.response;

import com.hotel.service.common.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.AddressInfoType;
import org.opentravel.ota._2003._05.CountryNameType;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AddressMapperTest {

    @InjectMocks
    private AddressMapper addressMapper;

    @Test
    public void map() {
        AddressInfoType addressInfoType = getAddress();
        Address address = addressMapper.map(addressInfoType);
        assertThat(address).isNotNull();
        assertThat(address.getStreetAddress()).isEqualTo("Hotel Paris, Street1, France");
    }

    private AddressInfoType getAddress() {
        AddressInfoType addressInfoType = new AddressInfoType();
        addressInfoType.getAddressLine().add("Hotel Paris, Street1, France");
        addressInfoType.setCityName("Paris");
        addressInfoType.setPostalCode("1234");
        CountryNameType countryNameType = new CountryNameType();
        countryNameType.setValue("France");
        addressInfoType.setCountryName(countryNameType);
        return addressInfoType;
    }
}
