package com.hotel.pad.mappers;

import com.hotel.service.availability.HotelAvailabilityRequest;
import org.opentravel.ota._2003._05.ArrayOfSourceType;
import org.opentravel.ota._2003._05.SourceType;

public class PosMapper {

    public  ArrayOfSourceType mapPOS(){
        ArrayOfSourceType arrayOfSourceType = new ArrayOfSourceType();
        SourceType sourceType = new SourceType();
        SourceType.RequestorID requestorID= new SourceType.RequestorID();

        requestorID.setID(HotelAvailabilityRequest.newBuilder().getRequestContext().getSupplierRequestorId());
        requestorID.setMessagePassword(HotelAvailabilityRequest.newBuilder().getRequestContext().getSupplierCredential());
        requestorID.setLanguageCode(HotelAvailabilityRequest.newBuilder().getLanguageCode());
        requestorID.setType("1");

        sourceType.setRequestorID(requestorID);
        arrayOfSourceType.getSource().add(sourceType);
        return arrayOfSourceType;

    }

}
