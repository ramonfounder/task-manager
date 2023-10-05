package me.thejramon.taskmanager.repository

import me.thejramon.taskmanager.domain.Priority
import me.thejramon.taskmanager.domain.Status
import me.thejramon.taskmanager.domain.Task
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private lateinit var taskRepository: TaskRepository

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Test
    fun `should save a new task`() {
        val task = Task(
            title = "drink water",
            description = "every day we should drink 3 glasses",
            priority = Priority.HIGH,
            status = Status.TODO
        )
        val savedTask = taskRepository.save(task)

        assertNotNull(savedTask.id)
        assertEquals(task.title, savedTask.title)
    }

    @Test
    fun `should retrieve a task by ID`() {
        val task = Task(
            title = "drink water",
            description = "every day we should drink 3 glasses",
            priority = Priority.HIGH,
            status = Status.TODO
        )
        val persistedTask = entityManager.persistFlushFind(task)

        val foundTask = taskRepository.findById(persistedTask.id!!)

        assertTrue(foundTask.isPresent)
        assertEquals(task.title, foundTask.get().title)
    }

    @Test
    fun `should return empty when retrieving a non-existing task`() {
        val foundTask = taskRepository.findById(99L)

        assertFalse(foundTask.isPresent)
    }

    @Test
    fun `should delete a task`() {
        val task = Task(
            title = "drink water",
            description = "every day we should drink 3 glasses",
            priority = Priority.HIGH,
            status = Status.TODO
        )
        val persistedTask = entityManager.persist(task)

        taskRepository.deleteById(persistedTask.id!!)
        val deletedTask = taskRepository.findById(persistedTask.id!!)

        assertFalse(deletedTask.isPresent)
    }

}
