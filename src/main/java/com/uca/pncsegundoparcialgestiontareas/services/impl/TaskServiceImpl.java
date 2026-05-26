package com.uca.pncsegundoparcialgestiontareas.services.impl;

import com.uca.pncsegundoparcialgestiontareas.common.Priority;
import com.uca.pncsegundoparcialgestiontareas.common.Status;
import com.uca.pncsegundoparcialgestiontareas.common.mappers.TaskMapper;
import com.uca.pncsegundoparcialgestiontareas.domain.dto.request.CreateTaskRequest;
import com.uca.pncsegundoparcialgestiontareas.domain.dto.request.UpdateTaskRequest;
import com.uca.pncsegundoparcialgestiontareas.domain.dto.response.task.TaskResponse;
import com.uca.pncsegundoparcialgestiontareas.domain.entities.Task;
import com.uca.pncsegundoparcialgestiontareas.exceptions.BusinessRuleException;
import com.uca.pncsegundoparcialgestiontareas.exceptions.ResourceNotFoundException;
import com.uca.pncsegundoparcialgestiontareas.repositories.TaskRepository;
import com.uca.pncsegundoparcialgestiontareas.services.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {

        if (taskRepository.existsByTitleIgnoreCase(request.getTitle()))
            throw new BusinessRuleException("Ya existe esa tarea");


        if (!request.getDueDate().isAfter(LocalDate.now()))
            throw new BusinessRuleException("La fecha de vencimiento debe ser una por venir");


        if (request.getLoggedHours() != null &&
                request.getLoggedHours() > request.getEstimatedHours())
            throw new BusinessRuleException("Las horas registradas no pueden superar las horas estimadas");

        return taskMapper.toDto(
                taskRepository.save(taskMapper.toEntityCreate(request))
        );
    }

    @Override
    public List<TaskResponse> getAllTasks(Status status, Priority priority) {
        List<Task> tasks;

        if (status != null && priority != null)
            tasks = taskRepository.findByStatusAndPriority(status, priority);
        else if (status != null)
            tasks = taskRepository.findByStatus(status);
        else if (priority != null)
            tasks = taskRepository.findByPriority(priority);
        else
            tasks = taskRepository.findAll();

        if (tasks.isEmpty())
            throw new ResourceNotFoundException("No se encontraron tareas");

        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        return taskMapper.toDto(
                taskRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Tarea no encontrada con id: " + id))
        );
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long id, UpdateTaskRequest request) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tarea no encontrada con id: " + id));

        if (request.getTitle() != null &&
                taskRepository.existsByTitleIgnoreCaseAndIdNot(request.getTitle(), id))
            throw new BusinessRuleException("Ya existe una tarea con ese título");


        Integer newEstimated = request.getEstimatedHours() != null
                ? request.getEstimatedHours() : existing.getEstimatedHours();
        Integer newLogged = request.getLoggedHours() != null
                ? request.getLoggedHours() : existing.getLoggedHours();

        if (newLogged > newEstimated)
            throw new BusinessRuleException("Las horas registradas no pueden superar las horas estimadas.");

        return taskMapper.toDto(
                taskRepository.save(taskMapper.toEntityUpdate(request, existing))
        );
    }

    @Override
    @Transactional
    public TaskResponse deleteTask(Long id) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tarea no encontrada"));

        if (existing.getStatus() == Status.IN_PROGRESS ||
                existing.getStatus() == Status.REVIEW)
            throw new BusinessRuleException(
                    "No se puede eliminar una tarea con estado: " + existing.getStatus());

        TaskResponse response = taskMapper.toDto(existing);
        taskRepository.deleteById(id);
        return response;
    }
}
