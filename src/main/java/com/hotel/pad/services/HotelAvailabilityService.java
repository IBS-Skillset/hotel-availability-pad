package com.hotel.pad.services;


import com.hotel.pad.client.HotelAvailabilityClient;
import com.hotel.pad.mappers.AvailRequestSegmentsMapper;
import com.hotel.pad.mappers.PosMapper;
import lombok.extern.slf4j.Slf4j;
import org.opentravel.ota._2003._05.OTAHotelAvailRQ;
import org.opentravel.ota._2003._05.OTAHotelAvailRS;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HotelAvailabilityService {

    public void getAvailableHotelItemsFromSupplier(){
        HotelAvailabilityClient client = new HotelAvailabilityClient();
        OTAHotelAvailRQ hotelAvailRQ = new OTAHotelAvailRQ();
        PosMapper posMapper = new PosMapper();
        AvailRequestSegmentsMapper availRequestSegments = new AvailRequestSegmentsMapper();
        hotelAvailRQ.setPOS(posMapper.mapPOS());
        hotelAvailRQ.setAvailRequestSegments(availRequestSegments.mapAvailRequestSegments());
        try {
            OTAHotelAvailRS hotelAvailRS = client.restClient(hotelAvailRQ);
            if(hotelAvailRS.getErrors().getError() !=null){
                log.info("Successful OTA hotel Avail Response",hotelAvailRS);
            }
        } catch (Exception e) {
            log.error("Response is not received" + e);
        }

    }
}
