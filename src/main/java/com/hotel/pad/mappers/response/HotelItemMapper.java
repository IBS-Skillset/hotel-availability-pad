package com.hotel.pad.mappers.response;

import com.hotel.pad.util.APIConstants;
import com.hotel.service.common.AvailableHotelItem;
import org.opentravel.ota._2003._05.BasicPropertyInfoType;
import org.opentravel.ota._2003._05.OTAHotelAvailRS;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static java.util.Objects.nonNull;

@Component
public class HotelItemMapper {

    @Autowired
    private AddressMapper addressMapper;

    public AvailableHotelItem map(OTAHotelAvailRS.RoomStays.RoomStay roomStay) {
        final BasicPropertyInfoType basicPropertyInfoType = roomStay.getBasicPropertyInfo();
        final TPAExtensionsType tpaExtensionsType = roomStay.getTPAExtensions();
        AvailableHotelItem.Builder hotelItemBuilder = AvailableHotelItem.newBuilder();
        safeSetProtoField(hotelItemBuilder::setHotelCode, basicPropertyInfoType.getHotelCode());
        safeSetProtoField(hotelItemBuilder::setHotelName, basicPropertyInfoType.getHotelName());
        safeSetProtoField(hotelItemBuilder::setHotelCategory, tpaExtensionsType.getStarRating());
        safeSetProtoField(hotelItemBuilder::setAddress, addressMapper.map(basicPropertyInfoType.getAddress()));
        safeSetProtoField(hotelItemBuilder::setCityCode, basicPropertyInfoType.getHotelCityCode());
        safeSetProtoField(hotelItemBuilder::setLatitude, Double.valueOf(basicPropertyInfoType.getPosition().getLatitude()));
        safeSetProtoField(hotelItemBuilder::setLongitude, Double.valueOf(basicPropertyInfoType.getPosition().getLongitude()));
        safeSetProtoField(hotelItemBuilder::setMinPrice, Double.valueOf(tpaExtensionsType.getMinRate().getAmount()));
        safeSetProtoField(hotelItemBuilder::setCurrencyCode, tpaExtensionsType.getMinRate().getCurrencyCode());
        if (nonNull(tpaExtensionsType.getBreakfastIncluded()) && tpaExtensionsType.getBreakfastIncluded().equals(APIConstants.TRUE)) {
            safeSetProtoField(hotelItemBuilder::setBreakfast, Boolean.TRUE);
        } else {
            safeSetProtoField(hotelItemBuilder::setBreakfast, Boolean.FALSE);
        }

        return hotelItemBuilder.build();

    }
}
