package me.thejramon.taskmanager.controller

import me.thejramon.taskmanager.dto.TaskDTO
import me.thejramon.taskmanager.mapper.toDTO
import me.thejramon.taskmanager.mapper.toEntity
import me.thejramon.taskmanager.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(@Autowired private val taskService: TaskService) {

    @GetMapping
    fun getAllTasks(): ResponseEntity<List<TaskDTO>> {
        val tasks = taskService.findAll().map { it.toDTO() }
        return ResponseEntity.ok(tasks)
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Long): ResponseEntity<TaskDTO> {
        val task = taskService.findById(id)
        return task.map { ResponseEntity.ok(it.toDTO()) }
            .orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/user/{userId}")
    fun getTasksByUserId(@PathVariable userId: Long): ResponseEntity<List<TaskDTO>> {
        val tasks = taskService.findByUserId(userId).map { it.toDTO() }
        return ResponseEntity.ok(tasks)
    }

    @PostMapping
    fun createTask(@RequestBody taskDTO: TaskDTO): ResponseEntity<TaskDTO> {
        val task = taskDTO.toEntity()
        val savedTask = taskService.save(task)
        return ResponseEntity.ok(savedTask.toDTO())
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<Void> {
        taskService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

}
