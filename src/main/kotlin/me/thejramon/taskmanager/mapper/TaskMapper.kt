package me.thejramon.taskmanager.mapper

import me.thejramon.taskmanager.domain.Task
import me.thejramon.taskmanager.dto.TaskDTO

fun Task.toDTO() = TaskDTO(
    id = this.id,
    title = this.title,
    description = this.description,
    priority = this.priority,
    status = this.status,
    dueDate = this.dueDate,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun TaskDTO.toEntity(): Task {
    return Task(id, title, description, priority, status, dueDate)
}