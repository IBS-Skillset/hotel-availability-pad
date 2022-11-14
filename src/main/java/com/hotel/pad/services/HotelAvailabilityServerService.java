package com.hotel.pad.services;

import com.hotel.pad.exception.HotelException;
import com.hotel.pad.mappers.response.ErrorResponseMapper;
import com.hotel.service.availability.HotelAvailabilityRequest;
import com.hotel.service.availability.HotelAvailabilityResponse;
import com.hotel.service.availability.HotelServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Objects;



@GrpcService
@Slf4j
public class HotelAvailabilityServerService extends HotelServiceGrpc.HotelServiceImplBase {
    @Autowired
    private HotelAvailabilityService hotelAvailabilityService;

    @Autowired
    private ErrorResponseMapper errorResponseMapper;

    @Override
    public void getHotelItem(HotelAvailabilityRequest request, StreamObserver<HotelAvailabilityResponse> responseObserver) {
        log.info(request.toString());
        HotelAvailabilityResponse response = null;
        try {
            response = hotelAvailabilityService.getAvailableHotelItemsFromSupplier(request);
            if (Objects.nonNull(response)) {
                log.info(response.toString());
            }
        }catch (HotelException e) {
            response = errorResponseMapper.mapErrorResponse(e.getMessage(),e.getCode());
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


}
