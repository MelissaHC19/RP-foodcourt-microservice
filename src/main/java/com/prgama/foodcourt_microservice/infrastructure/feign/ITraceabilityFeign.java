package com.prgama.foodcourt_microservice.infrastructure.feign;

import com.prgama.foodcourt_microservice.application.dto.request.CreateTraceabilityRequest;
import com.prgama.foodcourt_microservice.application.dto.request.UpdateTraceabilityRequest;
import com.prgama.foodcourt_microservice.infrastructure.constants.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = FeignConstants.FEIGN_CLIENT_NAME_TRACEABILITY, url = FeignConstants.FEIGN_CLIENT_URL_TRACEABILITY)
public interface ITraceabilityFeign {
    @PostMapping("/create")
    ResponseEntity<Void> createTraceability(@RequestBody CreateTraceabilityRequest createTraceabilityRequest);

    @PutMapping("/{orderId}")
    ResponseEntity<Void> updateTraceability(@PathVariable Long orderId, @RequestBody UpdateTraceabilityRequest updateTraceabilityRequest);
}
