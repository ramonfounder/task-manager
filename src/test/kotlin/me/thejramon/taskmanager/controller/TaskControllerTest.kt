package me.thejramon.taskmanager.controller

import me.thejramon.taskmanager.domain.Priority
import me.thejramon.taskmanager.domain.Status
import me.thejramon.taskmanager.dto.TaskDTO
import me.thejramon.taskmanager.mapper.toEntity
import me.thejramon.taskmanager.service.TaskService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.*

@WebMvcTest(TaskController::class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
class TaskControllerTest(@Autowired private val mockMvc: MockMvc) {

    @MockBean
    private lateinit var taskService: TaskService

    @Test
    fun `should create a new task`() {
        val taskDTO = TaskDTO(
            id = null,
            title = "Test Task",
            description = "Description of test task",
            priority = Priority.HIGH,
            status = Status.TODO,
            dueDate = null,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        given(taskService.save(any())).willReturn(taskDTO.toEntity())

        mockMvc.perform(
            post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{
                    "title": "Test Task",
                    "description": "Description of test task",
                    "priority": "HIGH",
                    "status": "TODO"
                }"""
                )
        )
            .andExpect(status().isOk)
            .andDo(
                document(
                    "create-task",
                    requestFields(
                        fieldWithPath("title").description("Title of the task"),
                        fieldWithPath("description").description("Description of the task"),
                        fieldWithPath("priority").description("Priority of the task"),
                        fieldWithPath("status").description("Status of the task")
                    ),
                    responseFields(
                        fieldWithPath("id").description("ID of the newly created task"),
                        fieldWithPath("title").description("Title of the task"),
                        fieldWithPath("description").description("Description of the task"),
                        fieldWithPath("priority").description("Priority of the task"),
                        fieldWithPath("status").description("Status of the task"),
                        fieldWithPath("dueDate").description("Due date of the task").optional(),
                        fieldWithPath("createdAt").description("Creation timestamp of the task"),
                        fieldWithPath("updatedAt").description("Last update timestamp of the task")
                    )
                )
            )
    }

    @Test
    fun `should retrieve a task by ID`() {
        val taskDTO = TaskDTO(
            id = 1L,
            title = "Test Task",
            description = "Description of test task",
            priority = Priority.HIGH,
            status = Status.TODO,
            dueDate = null,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        given(taskService.findById(1L)).willReturn(Optional.of(taskDTO.toEntity()))

        mockMvc.perform(get("/api/tasks/1"))
            .andExpect(status().isOk)
            .andDo(
                document(
                    "get-task",
                    responseFields(
                        fieldWithPath("id").description("ID of the task"),
                        fieldWithPath("title").description("Title of the task"),
                        fieldWithPath("description").description("Description of the task"),
                        fieldWithPath("priority").description("Priority of the task"),
                        fieldWithPath("status").description("Status of the task"),
                        fieldWithPath("dueDate").description("Due date of the task").optional(),
                        fieldWithPath("createdAt").description("Creation timestamp of the task"),
                        fieldWithPath("updatedAt").description("Last update timestamp of the task")
                    )
                )
            )
    }

    @Test
    fun `should return 404 when retrieving a non-existing task`() {
        given(taskService.findById(99L)).willReturn(Optional.empty())

        mockMvc.perform(get("/api/tasks/99"))
            .andExpect(status().isNotFound)
            .andDo(document("get-task-not-found"))
    }

}
