package com.hotel.pad.mappers;

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
        dateTimeSpanType.setStart(String.valueOf(request.newBuilder().getStartDate()));
        dateTimeSpanType.setEnd(String.valueOf(request.newBuilder().getEndDate()));

        availRequestSegment.setStayDateRange(dateTimeSpanType);

        ArrayOfAvailRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate availRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate = new ArrayOfAvailRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate();
        ArrayOfAvailRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate.RoomStayCandidate roomStayCandidate = new ArrayOfAvailRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate.RoomStayCandidate();

        roomStayCandidate.setQuantity(BigInteger.valueOf(request.newBuilder().getRoomCount()));

        GuestCountType guestCountType = new GuestCountType();
        GuestCountType.GuestCount guestCount = new GuestCountType.GuestCount();
        guestCount.setAgeQualifyingCode("10");
        guestCount.setCount(BigInteger.valueOf(request.newBuilder().getOccupancy()));
        guestCountType.getGuestCount().add(guestCount);

        roomStayCandidate.setGuestCounts(guestCountType);
        availRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate.getRoomStayCandidate().add(roomStayCandidate);


        availRequestSegment.setRoomStayCandidates(availRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate);

        HotelSearchCriteriaType hotelSearchCriteriaType = new HotelSearchCriteriaType();
        HotelSearchCriteriaType.Criterion criterion = new HotelSearchCriteriaType.Criterion();

        criterion.getPosition().setLongitude(String.valueOf(request.newBuilder().getLongitude()));
        criterion.getPosition().setLatitude(String.valueOf(request.newBuilder().getLatitude()));
        criterion.getPosition().setAltitudeUnitOfMeasureCode("2");

        criterion.getTPAExtensions().setDistance(BigInteger.valueOf(3));
        criterion.getTPAExtensions().setGeoCodeType("DD");
        criterion.getTPAExtensions().setCountryCode(request.newBuilder().getCountryCode());

        hotelSearchCriteriaType.getCriterion().add(criterion);

        availRequestSegment.setHotelSearchCriteria(hotelSearchCriteriaType);
        availRequestSegments.getAvailRequestSegment().add(availRequestSegment);

        return availRequestSegments;


    }
}
