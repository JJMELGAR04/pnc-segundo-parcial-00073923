package com.uca.pncsegundoparcialgestiontareas.repositories;

import com.uca.pncsegundoparcialgestiontareas.common.Priority;
import com.uca.pncsegundoparcialgestiontareas.common.Status;
import com.uca.pncsegundoparcialgestiontareas.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByTitleIgnoreCase(String title);
    boolean existsByTitleIgnoreCaseAndIdNot(String title, Long id);
    List<Task> findByStatus(Status status);
    List<Task> findByPriority(Priority priority);
    List<Task> findByStatusAndPriority(Status status, Priority priority);
}
