package me.thejramon.taskmanager.service

import me.thejramon.taskmanager.domain.Priority
import me.thejramon.taskmanager.domain.Status
import me.thejramon.taskmanager.domain.Task
import me.thejramon.taskmanager.repository.TaskRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class TaskServiceTest {

    @Mock
    private lateinit var taskRepository: TaskRepository

    @InjectMocks
    private lateinit var taskService: TaskService

    @Test
    fun `should create a new task`() {
        val task = Task(
            title = "drink water",
            description = "every day we should drink 3 glasses",
            priority = Priority.HIGH,
            status = Status.TODO
        )
        `when`(taskRepository.save(any())).thenReturn(task)

        val result = taskService.save(task)
        assertEquals(task, result)
    }

    @Test
    fun `should retrieve a task by ID`() {
        val task = Task(
            id = 1L,
            title = "drink water",
            description = "every day we should drink 3 glasses",
            priority = Priority.HIGH,
            status = Status.TODO
        )
        `when`(taskRepository.findById(1L)).thenReturn(Optional.of(task))

        val result = taskService.findById(1L)

        assertTrue(result.isPresent)
        assertEquals(task, result.get())
    }

    @Test
    fun `should return empty when retrieving a non-existing task`() {
        `when`(taskRepository.findById(99L)).thenReturn(Optional.empty())

        val result = taskService.findById(99L)
        assertFalse(result.isPresent)
    }

    @Test
    fun `should delete a task`() {
        val idToDelete = 1L
        doNothing().`when`(taskRepository).deleteById(idToDelete)

        taskService.deleteById(idToDelete)
        verify(taskRepository, times(1)).deleteById(idToDelete)
    }

}
