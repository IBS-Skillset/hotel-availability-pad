package com.hotel.pad.client;

import com.hotel.pad.endpoint.DJocaRestService;
import com.hotel.pad.endpoint.DjocaEndpointFactory;
import com.hotel.service.availability.HotelAvailabilityRequest;
import org.opentravel.ota._2003._05.OTAHotelAvailRQ;
import org.opentravel.ota._2003._05.OTAHotelAvailRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class HotelAvailabilityClient {

    private final String service = "hotel-availability";
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelAvailabilityClient.class);
    private static final Logger ACCESS_LOG = LoggerFactory.getLogger("ACCESS_LOG");

    public Object restClient(OTAHotelAvailRQ hotelAvailRQ, HotelAvailabilityRequest request) throws JAXBException {
        /*
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestContext().getSupplierUrl());
        uriBuilder.path(service);
        return restTemplate.postForObject(uriBuilder.build().toUriString(),hotelAvailRQ,OTAHotelAvailRS.class);*/
        JAXBContext context= DjocaEndpointFactory.CONTEXT;
        final String endpointUrl=request.getRequestContext().getSupplierUrl()+"/"+service;
        final StringWriter requestWriter = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try {
            marshaller.marshal(hotelAvailRQ, requestWriter);
            DJocaRestService djocaRestService = new DJocaRestService();
            ACCESS_LOG.info(requestWriter.toString());
            final String responseInStr = djocaRestService.send(requestWriter.toString(), endpointUrl);
            ACCESS_LOG.info(responseInStr);
            return unmarshaller.unmarshal(new StringReader(responseInStr));
        }
        catch (JAXBException b){
            LOGGER.info("JAXBException caught" +b);
            throw b;
        }
        catch (Exception e)
        {
            LOGGER.info("Exception occured in request-response to Djoca" +e);
            throw e;
        }
    }

}
