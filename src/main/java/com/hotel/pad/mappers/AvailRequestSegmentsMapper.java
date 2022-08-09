package com.hotel.pad.mappers;

import com.hotel.service.availability.HotelAvailabilityRequest;
import org.opentravel.ota._2003._05.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class AvailRequestSegmentsMapper {

    public OTAHotelAvailRQ.AvailRequestSegments mapAvailRequestSegments() {

        OTAHotelAvailRQ.AvailRequestSegments availRequestSegments = new OTAHotelAvailRQ.AvailRequestSegments();
        AvailRequestSegmentsType.AvailRequestSegment availRequestSegment = new AvailRequestSegmentsType.AvailRequestSegment();
        DateTimeSpanType dateTimeSpanType = new DateTimeSpanType();
        dateTimeSpanType.setStart(String.valueOf(HotelAvailabilityRequest.newBuilder().getStartDate()));
        dateTimeSpanType.setEnd(String.valueOf(HotelAvailabilityRequest.newBuilder().getEndDate()));

        availRequestSegment.setStayDateRange(dateTimeSpanType);

        ArrayOfAvailRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate availRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate = new ArrayOfAvailRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate();
        ArrayOfAvailRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate.RoomStayCandidate roomStayCandidate = new ArrayOfAvailRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate.RoomStayCandidate();

        roomStayCandidate.setQuantity(BigInteger.valueOf(HotelAvailabilityRequest.newBuilder().getRoomCount()));

        GuestCountType guestCountType = new GuestCountType();
        GuestCountType.GuestCount guestCount = new GuestCountType.GuestCount();
        guestCount.setAgeQualifyingCode("10");
        guestCount.setCount(BigInteger.valueOf(HotelAvailabilityRequest.newBuilder().getOccupancy()));
        guestCountType.getGuestCount().add(guestCount);

        roomStayCandidate.setGuestCounts(guestCountType);
        availRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate.getRoomStayCandidate().add(roomStayCandidate);


        availRequestSegment.setRoomStayCandidates(availRequestSegmentsTypeAvailRequestSegmentRoomStayCandidate);

        HotelSearchCriteriaType hotelSearchCriteriaType = new HotelSearchCriteriaType();
        HotelSearchCriteriaType.Criterion criterion = new HotelSearchCriteriaType.Criterion();

        criterion.getPosition().setLongitude(String.valueOf(HotelAvailabilityRequest.newBuilder().getLongitude()));
        criterion.getPosition().setLatitude(String.valueOf(HotelAvailabilityRequest.newBuilder().getLatitude()));
        criterion.getPosition().setAltitudeUnitOfMeasureCode("2");

        criterion.getTPAExtensions().setDistance(BigInteger.valueOf(3));
        criterion.getTPAExtensions().setGeoCodeType("DD");
        criterion.getTPAExtensions().setCountryCode(HotelAvailabilityRequest.newBuilder().getCountryCode());

        hotelSearchCriteriaType.getCriterion().add(criterion);

        availRequestSegment.setHotelSearchCriteria(hotelSearchCriteriaType);
        availRequestSegments.getAvailRequestSegment().add(availRequestSegment);

        return availRequestSegments;


    }
}
