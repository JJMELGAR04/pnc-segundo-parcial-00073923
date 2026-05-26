package com.uca.pncsegundoparcialgestiontareas.controllers;

import com.uca.pncsegundoparcialgestiontareas.common.Priority;
import com.uca.pncsegundoparcialgestiontareas.common.Status;
import com.uca.pncsegundoparcialgestiontareas.domain.dto.request.CreateTaskRequest;
import com.uca.pncsegundoparcialgestiontareas.domain.dto.request.UpdateTaskRequest;
import com.uca.pncsegundoparcialgestiontareas.domain.dto.response.GeneralResponse;
import com.uca.pncsegundoparcialgestiontareas.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    public ResponseEntity<GeneralResponse> buildResponse(
            String message, HttpStatus status, Object data) {

        String uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().build().getPath();

        return ResponseEntity
                .status(status)
                .body(GeneralResponse.builder()
                        .uri(uri)
                        .message(message)
                        .status(status.value())
                        .time(LocalDateTime.now())
                        .data(data)
                        .build()
                );
    }

    @PostMapping
    public ResponseEntity<GeneralResponse> createTask(
            @RequestBody @Valid CreateTaskRequest request) {
        return buildResponse(
                "Tarea creada exitosamente.",
                HttpStatus.CREATED,
                taskService.createTask(request)
        );
    }

    @GetMapping
    public ResponseEntity<GeneralResponse> getAllTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority) {
        return buildResponse(
                "Tareas obtenidas exitosamente.",
                HttpStatus.OK,
                taskService.getAllTasks(status, priority)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> getTaskById(@PathVariable Long id) {
        return buildResponse(
                "Tarea encontrada.",
                HttpStatus.OK,
                taskService.getTaskById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse> updateTask(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTaskRequest request) {
        return buildResponse(
                "Tarea actualizada exitosamente.",
                HttpStatus.OK,
                taskService.updateTask(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteTask(@PathVariable Long id) {
        return buildResponse(
                "Tarea eliminada exitosamente.",
                HttpStatus.OK,
                taskService.deleteTask(id)
        );
    }
}

