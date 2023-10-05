package me.thejramon.taskmanager.repository

import me.thejramon.taskmanager.domain.Task
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, Long> {
    fun findByUserId(userId: Long): List<Task>
}
