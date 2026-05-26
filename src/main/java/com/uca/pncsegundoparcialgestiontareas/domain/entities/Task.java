package com.uca.pncsegundoparcialgestiontareas.domain.entities;

import com.uca.pncsegundoparcialgestiontareas.common.Priority;
import com.uca.pncsegundoparcialgestiontareas.common.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "task")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @Column(name = "estimated_hours", nullable = false)
    private Integer estimatedHours;

    @Column(name = "logged_hours")
    private Integer loggedHours;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "assigned_to", nullable = false)
    private String assignedTo;

    @Column(name = "active", nullable = false)
    private Boolean active;

}
