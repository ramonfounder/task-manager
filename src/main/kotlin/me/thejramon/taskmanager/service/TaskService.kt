package me.thejramon.taskmanager.service

import me.thejramon.taskmanager.domain.Task
import me.thejramon.taskmanager.repository.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskService(@Autowired val taskRepository: TaskRepository) {

    fun findAll(): List<Task> = taskRepository.findAll()

    fun findById(id: Long): Optional<Task> = taskRepository.findById(id)

    fun findByUserId(userId: Long): List<Task> = taskRepository.findByUserId(userId)

    fun save(task: Task): Task = taskRepository.save(task)

    fun deleteById(id: Long) = taskRepository.deleteById(id)
}
