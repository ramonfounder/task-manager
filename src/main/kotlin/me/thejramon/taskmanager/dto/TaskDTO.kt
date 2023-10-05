package me.thejramon.taskmanager.dto

import me.thejramon.taskmanager.domain.Priority
import me.thejramon.taskmanager.domain.Status
import java.time.LocalDateTime

data class TaskDTO(
    val id: Long?,
    val title: String,
    val description: String,
    val priority: Priority,
    val status: Status,
    val dueDate: LocalDateTime?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
