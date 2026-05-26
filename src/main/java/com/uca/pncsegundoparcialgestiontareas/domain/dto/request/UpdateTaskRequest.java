package com.uca.pncsegundoparcialgestiontareas.domain.dto.request;

import com.uca.pncsegundoparcialgestiontareas.common.Priority;
import com.uca.pncsegundoparcialgestiontareas.common.Status;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequest {

    private String title;
    private String description;
    private Status status;
    private Priority priority;

    @Min(value = 1, message = "Las horas estimadas deben ser al menos 1")
    private Integer estimatedHours;

    @Min(value = 0, message = "Las horas registradas no pueden ser negativas")
    private Integer loggedHours;

    private LocalDate dueDate;
    private String assignedTo;

}
