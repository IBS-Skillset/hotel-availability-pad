package com.hotel.pad.mappers.response;

import com.hotel.pad.util.APIConstants;
import com.hotel.service.availability.HotelAvailabilityResponse;
import com.hotel.service.common.AvailableHotelItem;
import com.hotel.service.common.ResponseStatus;

import org.opentravel.ota._2003._05.OTAHotelAvailRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static java.util.Objects.nonNull;

@Component
public class HotelAvailabilityResponseMapper {

    @Autowired
    private HotelItemMapper hotelItemMapper;

    public HotelAvailabilityResponse map(OTAHotelAvailRS response) {
        HotelAvailabilityResponse.Builder hotelAvailabilityResponseBuilder = HotelAvailabilityResponse.newBuilder();
        if (nonNull(response.getSuccess()) && nonNull(response.getRoomStays())) {
            List<AvailableHotelItem> hotelItemList = response.getRoomStays().getRoomStay().stream()
                    .map(hotelItemMapper::map)
                    .collect(Collectors.toList());
            ResponseStatus.Builder reponseStatusBuilder = ResponseStatus.newBuilder();
            safeSetProtoField(reponseStatusBuilder::setStatus, APIConstants.SUCCESS);
            safeSetProtoField(hotelAvailabilityResponseBuilder::setResponseStatus, reponseStatusBuilder);
            hotelAvailabilityResponseBuilder.addAllHotelItem(hotelItemList);
        } else {
            ResponseStatus.Builder reponseStatusBuilder = ResponseStatus.newBuilder();
            safeSetProtoField(reponseStatusBuilder::setStatus, APIConstants.FAILURE);
            safeSetProtoField(reponseStatusBuilder::setErrorCode, response.getErrors().getError().get(0).getCode());
            safeSetProtoField(reponseStatusBuilder::setErrorMessage, response.getErrors().getError().get(0).getValue());
        }
        return hotelAvailabilityResponseBuilder.build();

    }
}
