package com.uca.pncsegundoparcialgestiontareas.domain.dto.request;

import com.uca.pncsegundoparcialgestiontareas.common.Priority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {

    @NotBlank(message = "El título no puede estar vacio")
    private String title;

    private String description;

    @NotNull(message = "La prioridad es obligatoria")
    private Priority priority;

    @NotNull(message = "Las horas estimadas es un campo obligatorio")
    @Min(value = 1, message = "Las horas estimadas deben ser al menos 1")
    private Integer estimatedHours;

    @Min(value = 0, message = "La horas ingresadas no pueden ser negativas")
    private Integer loggedHours;

    @NotNull(message = "Fecha de vencimiento es un campo obligatorio")
    @Future(message = "La fecha de vencimiento debe ser una fecha futura")
    private LocalDate dueDate;

    @NotBlank(message = "Debe ser asignado a alguien")
    private String assignedTo;
}
