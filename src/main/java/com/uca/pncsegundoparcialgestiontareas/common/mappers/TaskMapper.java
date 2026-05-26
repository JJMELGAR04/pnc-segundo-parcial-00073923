package com.uca.pncsegundoparcialgestiontareas.common.mappers;

import com.uca.pncsegundoparcialgestiontareas.common.Status;
import com.uca.pncsegundoparcialgestiontareas.domain.dto.request.CreateTaskRequest;
import com.uca.pncsegundoparcialgestiontareas.domain.dto.request.UpdateTaskRequest;
import com.uca.pncsegundoparcialgestiontareas.domain.dto.response.task.TaskResponse;
import com.uca.pncsegundoparcialgestiontareas.domain.entities.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntityCreate(CreateTaskRequest request) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(Status.PENDING)
                .priority(request.getPriority())
                .estimatedHours(request.getEstimatedHours())
                .loggedHours(request.getLoggedHours() != null ? request.getLoggedHours() : 0)
                .dueDate(request.getDueDate())
                .assignedTo(request.getAssignedTo())
                .active(true)
                .build();
    }

    public Task toEntityUpdate(UpdateTaskRequest request, Task existing) {
        if (request.getTitle() != null) existing.setTitle(request.getTitle());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getPriority() != null) existing.setPriority(request.getPriority());
        if (request.getEstimatedHours() != null) existing.setEstimatedHours(request.getEstimatedHours());
        if (request.getLoggedHours() != null) existing.setLoggedHours(request.getLoggedHours());
        if (request.getDueDate() != null) existing.setDueDate(request.getDueDate());
        if (request.getAssignedTo() != null) existing.setAssignedTo(request.getAssignedTo());
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
            existing.setActive(
                    request.getStatus() != Status.DONE &&
                            request.getStatus() != Status.CANCELLED
            );
        }
        return existing;
    }

    public TaskResponse toDto(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .estimatedHours(task.getEstimatedHours())
                .loggedHours(task.getLoggedHours())
                .dueDate(task.getDueDate())
                .assignedTo(task.getAssignedTo())
                .active(task.getActive())
                .build();
    }
}
