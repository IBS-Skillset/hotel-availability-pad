package com.hotel.pad.services;

import com.hotel.service.availability.HotelAvailabilityRequest;
import com.hotel.service.availability.HotelAvailabilityResponse;
import com.hotel.service.availability.HotelServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
public class HotelAvailabilityServerService extends HotelServiceGrpc.HotelServiceImplBase {
    private final HotelAvailabilityService hotelAvailabilityService;

    public HotelAvailabilityServerService(HotelAvailabilityService hotelAvailabilityService) {
        this.hotelAvailabilityService = hotelAvailabilityService;
    }


    @Override
    public void getHotelItem(HotelAvailabilityRequest request, StreamObserver<HotelAvailabilityResponse> responseObserver){
        hotelAvailabilityService.getAvailableHotelItemsFromSupplier(request);
        HotelAvailabilityResponse.Builder hotelresp = HotelAvailabilityResponse.newBuilder();
        log.info(request.toString());
        responseObserver.onNext(hotelresp.build());
        responseObserver.onCompleted();

    }



}
