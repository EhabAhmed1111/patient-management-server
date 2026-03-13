package org.ihab.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    // Implement gRPC methods here
    @Override
    public void createBillingAccount(BillingRequest request,
                                     // stream observer is a powerful concept that allow us to receive multiple request and send multiple response
                                     StreamObserver<BillingResponse> responseObserver) {
        log.info("createBillingAccount called with request: {}", request.toString());

        // Implement the logic to create a bill based on the request
        // For example, you can call the billing service to create a bill and then build the response



        // Build the response
        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12345")
                .setStatus("ACTIVE")
                .build();

        // this is observable pattern, we can send multiple response to the client, and the client can receive them one by one, and we can also send a response to the client when we have a new bill created, without the client having to request for it, this is useful for real-time updates
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
