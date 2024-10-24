package com.prgama.foodcourt_microservice.application.dto.request;

import com.prgama.foodcourt_microservice.application.constants.RequestConstants;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UpdateDishStatusRequest {
    @NotNull(message = RequestConstants.DISH_STATUS_MANDATORY_MESSAGE)
    private Boolean active;
}
