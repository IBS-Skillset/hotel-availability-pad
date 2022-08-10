package com.hotel.pad.mappers;

import com.hotel.service.availability.HotelAvailabilityRequest;
import org.opentravel.ota._2003._05.ArrayOfSourceType;
import org.opentravel.ota._2003._05.SourceType;
import org.springframework.stereotype.Component;

@Component
public class PosMapper {

    public  ArrayOfSourceType mapPOS(HotelAvailabilityRequest request){
        ArrayOfSourceType arrayOfSourceType = new ArrayOfSourceType();
        SourceType sourceType = new SourceType();
        SourceType.RequestorID requestorID= new SourceType.RequestorID();

        requestorID.setID(request.newBuilder().getRequestContext().getSupplierRequestorId());
        requestorID.setMessagePassword(request.newBuilder().getRequestContext().getSupplierCredential());
        requestorID.setLanguageCode(request.newBuilder().getLanguageCode());
        requestorID.setType("1");

        sourceType.setRequestorID(requestorID);
        arrayOfSourceType.getSource().add(sourceType);
        return arrayOfSourceType;

    }

}
