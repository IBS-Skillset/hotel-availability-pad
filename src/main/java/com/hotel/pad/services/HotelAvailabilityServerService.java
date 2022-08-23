package com.hotel.pad.services;

import com.hotel.service.availability.HotelAvailabilityRequest;
import com.hotel.service.availability.HotelAvailabilityResponse;
import com.hotel.service.availability.HotelServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;


@GrpcService
@Slf4j
public class HotelAvailabilityServerService extends HotelServiceGrpc.HotelServiceImplBase {
    @Autowired
    private HotelAvailabilityService hotelAvailabilityService;


    @Override
    public void getHotelItem(HotelAvailabilityRequest request, StreamObserver<HotelAvailabilityResponse> responseObserver){
        log.info(request.toString());
        HotelAvailabilityResponse response = hotelAvailabilityService.getAvailableHotelItemsFromSupplier(request);
        log.info(response.toString());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
