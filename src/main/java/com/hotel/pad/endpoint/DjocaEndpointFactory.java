package com.hotel.pad.endpoint;

import com.hotel.service.availability.HotelAvailabilityRequest;
import org.opentravel.ota._2003._05.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class DjocaEndpointFactory {
    public static final JAXBContext CONTEXT;

    static {
        try {
            CONTEXT = JAXBContext.newInstance(
                    OTAHotelAvailRQ.class,
                    OTAHotelAvailRS.class);
        } catch (JAXBException e) {
            throw new IllegalStateException("Cannot initialize DJOCA services");
        }
    }
}
