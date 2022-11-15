package com.hotel.pad.mappers.response;

import com.hotel.pad.util.APIConstants;
import com.hotel.service.availability.HotelAvailabilityResponse;
import com.hotel.service.common.AvailableHotelItem;
import com.hotel.service.common.ResponseStatus;

import org.opentravel.ota._2003._05.OTAHotelAvailRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static java.util.Objects.nonNull;

@Component
public class HotelAvailabilityResponseMapper {

    @Autowired
    private HotelItemMapper hotelItemMapper;

    @Autowired
    private ErrorResponseMapper errorResponseMapper;

    public HotelAvailabilityResponse map(OTAHotelAvailRS response) {
        HotelAvailabilityResponse.Builder hotelAvailabilityResponseBuilder = HotelAvailabilityResponse.newBuilder();
        if (nonNull(response.getSuccess()) && nonNull(response.getRoomStays())) {
            List<AvailableHotelItem> sortedHotelItemList = response.getRoomStays().getRoomStay().stream()
                    .filter(Objects::nonNull)
                    .map(hotelItemMapper::map)
                    .sorted(Comparator.comparingDouble(hotelItem -> hotelItem.getMinPrice()))
                    .collect(Collectors.toList());
            ResponseStatus.Builder responseStatusBuilder = ResponseStatus.newBuilder();
            safeSetProtoField(responseStatusBuilder::setStatus, APIConstants.SUCCESS);
            safeSetProtoField(hotelAvailabilityResponseBuilder::setResponseStatus, responseStatusBuilder);
            hotelAvailabilityResponseBuilder.addAllHotelItem(sortedHotelItemList);
            return hotelAvailabilityResponseBuilder.build();
        } else {
            return errorResponseMapper.mapErrorResponse(response.getErrors().getError().get(0).getValue(),
                    response.getErrors().getError().get(0).getCode());
            }


    }
}
