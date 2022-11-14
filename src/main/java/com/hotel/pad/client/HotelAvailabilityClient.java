package com.hotel.pad.client;

import com.hotel.pad.endpoint.DjocaEndpointFactory;
import com.hotel.pad.exception.HotelException;
import com.hotel.pad.util.APIConstants;
import com.hotel.service.availability.HotelAvailabilityRequest;
import lombok.extern.slf4j.Slf4j;
import org.opentravel.ota._2003._05.OTAHotelAvailRQ;
import org.springframework.http.ResponseEntity;
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
@Slf4j
public class HotelAvailabilityClient {

    private static final String SERVICE = "hotel-availability";

    public Object restClient(OTAHotelAvailRQ hotelAvailRQ, HotelAvailabilityRequest request) throws JAXBException {
        JAXBContext context= DjocaEndpointFactory.CONTEXT;
        final StringWriter requestWriter = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try {
            marshaller.marshal(hotelAvailRQ, requestWriter);
            RestTemplate restTemplate = new RestTemplate();
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestContext().getSupplierUrl());
            uriBuilder.path(SERVICE);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(uriBuilder.build().toUriString(),requestWriter.toString(), String.class);
            log.info(requestWriter.toString());
            log.info(responseEntity.getBody());
            return unmarshaller.unmarshal(new StringReader(responseEntity.getBody()));
        }
        catch (JAXBException b){
            log.info("JAXBException caught" +b);
            throw new HotelException(b.getMessage(),b.getErrorCode());
        }
        catch (Exception e)
        {
            log.info("Exception occured in request-response to Djoca" +e);
            throw new HotelException(e.getMessage(), APIConstants.SUPPLIER_SERVER_ERROR);
        }
    }

}
