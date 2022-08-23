package com.hotel.pad.mappers.response;

import com.hotel.service.common.Address;
import org.opentravel.ota._2003._05.AddressInfoType;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;

@Component
public class AddressMapper {

    public Address map(AddressInfoType addressInfoType) {
        Address.Builder addressBuilder = Address.newBuilder();
        safeSetProtoField(addressBuilder::setStreetAddress, addressInfoType.getAddressLine().stream()
                .map(Objects::toString)
                .collect(Collectors.joining("")));
        safeSetProtoField(addressBuilder::setCityName, addressInfoType.getCityName());
        safeSetProtoField(addressBuilder::setZipCode, addressInfoType.getPostalCode());
        safeSetProtoField(addressBuilder::setCountryName, addressInfoType.getCountryName().getValue());
        return addressBuilder.build();

    }

}
