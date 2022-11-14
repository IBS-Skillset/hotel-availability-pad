package com.hotel.pad.mappers.response;

import com.hotel.pad.util.APIConstants;
import com.hotel.service.availability.HotelAvailabilityResponse;
import com.hotel.service.common.ResponseStatus;
import org.springframework.stereotype.Component;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;

@Component
public class ErrorResponseMapper {

    public HotelAvailabilityResponse mapErrorResponse (String message, String code) {
        HotelAvailabilityResponse.Builder hotelAvailabilityResponseBuilder = HotelAvailabilityResponse.newBuilder();
        ResponseStatus.Builder reponseStatusBuilder = ResponseStatus.newBuilder();
        safeSetProtoField(reponseStatusBuilder::setStatus, APIConstants.FAILURE);
        safeSetProtoField(reponseStatusBuilder::setErrorCode, code);
        safeSetProtoField(reponseStatusBuilder::setErrorMessage, message);
        safeSetProtoField(hotelAvailabilityResponseBuilder::setResponseStatus, reponseStatusBuilder);
        return hotelAvailabilityResponseBuilder.build();

    }
}
