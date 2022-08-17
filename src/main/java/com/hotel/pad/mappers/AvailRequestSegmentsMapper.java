package com.hotel.pad.mappers;

import com.hotel.pad.util.APIConstants;
import com.hotel.service.availability.HotelAvailabilityRequest;
import org.opentravel.ota._2003._05.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class AvailRequestSegmentsMapper {

    public OTAHotelAvailRQ.AvailRequestSegments mapAvailRequestSegments(HotelAvailabilityRequest request) {

        OTAHotelAvailRQ.AvailRequestSegments availRequestSegments = new OTAHotelAvailRQ.AvailRequestSegments();
        AvailRequestSegmentsType.AvailRequestSegment availRequestSegment = new AvailRequestSegmentsType.AvailRequestSegment();
        DateTimeSpanType dateTimeSpanType = new DateTimeSpanType();
        dateTimeSpanType.setStart(request.getStartDate());
        dateTimeSpanType.setEnd(request.getEndDate());

        availRequestSegment.setStayDateRange(dateTimeSpanType);

        ArrayOfAvailRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate availRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate = new ArrayOfAvailRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate();
        ArrayOfAvailRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate.RoomStayCandidate roomStayCandidate = new ArrayOfAvailRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate.RoomStayCandidate();

        roomStayCandidate.setQuantity(BigInteger.valueOf(request.getRoomCount()));

        GuestCountType guestCountType = new GuestCountType();
        GuestCountType.GuestCount guestCount = new GuestCountType.GuestCount();
        guestCount.setAgeQualifyingCode(APIConstants.AGE_QUALIFYING_CODE);
        guestCount.setCount(BigInteger.valueOf(request.getOccupancy()));
        guestCountType.getGuestCount().add(guestCount);

        roomStayCandidate.setGuestCounts(guestCountType);
        availRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate.getRoomStayCandidate().add(roomStayCandidate);


        availRequestSegment.setRoomStayCandidates(availRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate);

        HotelSearchCriteriaType hotelSearchCriteriaType = new HotelSearchCriteriaType();
        HotelSearchCriteriaType.Criterion criterion = new HotelSearchCriteriaType.Criterion();

        ItemSearchCriterionType.Position position = new ItemSearchCriterionType.Position();
        position.setLongitude(String.valueOf(request.getLongitude()));
        position.setLatitude(String.valueOf(request.getLatitude()));
        position.setAltitudeUnitOfMeasureCode(APIConstants.UNIT_OF_MEASURE_CODE);
        criterion.setPosition(position);

        ItemSearchCriterionType.HotelRef hotelRef= new ItemSearchCriterionType.HotelRef();
        criterion.getHotelRef().add(hotelRef);


        TPAExtensionsType tpaExtensionsType = new TPAExtensionsType();
        tpaExtensionsType.setDistance(BigInteger.valueOf(APIConstants.DISTANCE));
        tpaExtensionsType.setGeoCodeType(APIConstants.GEO_CODE_TYPE);
        tpaExtensionsType.setCountryCode(request.getCountryCode());
        criterion.setTPAExtensions(tpaExtensionsType);
        
        hotelSearchCriteriaType.getCriterion().add(criterion);

        availRequestSegment.setHotelSearchCriteria(hotelSearchCriteriaType);
        availRequestSegments.getAvailRequestSegment().add(availRequestSegment);

        return availRequestSegments;


    }
}
