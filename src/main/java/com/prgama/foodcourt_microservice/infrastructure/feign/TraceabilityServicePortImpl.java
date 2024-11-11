package com.prgama.foodcourt_microservice.infrastructure.feign;

import com.prgama.foodcourt_microservice.application.dto.request.CreateTraceabilityRequest;
import com.prgama.foodcourt_microservice.application.dto.request.CreateStatusLogRequest;
import com.prgama.foodcourt_microservice.application.dto.request.UpdateTraceabilityRequest;
import com.prgama.foodcourt_microservice.domain.api.ITraceabilityServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TraceabilityServicePortImpl implements ITraceabilityServicePort {
    private final ITraceabilityFeign traceabilityFeign;

    @Override
    public void createTraceability(Long orderId, Long clientId, String clientEmail, LocalDateTime initialTime, String newStatus, Long restaurantId) {
        CreateStatusLogRequest createStatusLogRequest = new CreateStatusLogRequest(null, newStatus, initialTime);
        List<CreateStatusLogRequest> createStatusLogRequests = new ArrayList<>();
        createStatusLogRequests.add(createStatusLogRequest);
        CreateTraceabilityRequest request = new CreateTraceabilityRequest(orderId, clientId, clientEmail, initialTime, createStatusLogRequests, restaurantId);
        traceabilityFeign.createTraceability(request);
    }

    @Override
    public void updateTraceability(LocalDateTime finalTime, String lastStatus, String newStatus, Long employeeId, String employeeEmail, Long orderId) {
        CreateStatusLogRequest createStatusLogRequest = new CreateStatusLogRequest(lastStatus,newStatus, finalTime);
        List<CreateStatusLogRequest> createStatusLogRequests = new ArrayList<>();
        createStatusLogRequests.add(createStatusLogRequest);
        UpdateTraceabilityRequest request = new UpdateTraceabilityRequest(finalTime, createStatusLogRequests, employeeId, employeeEmail);
        traceabilityFeign.updateTraceability(orderId, request);
    }
}
