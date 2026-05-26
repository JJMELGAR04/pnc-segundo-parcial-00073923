package com.uca.pncsegundoparcialgestiontareas.services;

import com.uca.pncsegundoparcialgestiontareas.common.Priority;
import com.uca.pncsegundoparcialgestiontareas.common.Status;
import com.uca.pncsegundoparcialgestiontareas.domain.dto.request.CreateTaskRequest;
import com.uca.pncsegundoparcialgestiontareas.domain.dto.request.UpdateTaskRequest;
import com.uca.pncsegundoparcialgestiontareas.domain.dto.response.task.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(CreateTaskRequest request);
    List<TaskResponse> getAllTasks(Status status, Priority priority);
    TaskResponse getTaskById(Long id);
    TaskResponse updateTask(Long id, UpdateTaskRequest request);
    TaskResponse deleteTask(Long id);
}
