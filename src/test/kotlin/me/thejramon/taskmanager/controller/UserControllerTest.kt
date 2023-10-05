package me.thejramon.taskmanager.controller

import me.thejramon.taskmanager.dto.UserDTO
import me.thejramon.taskmanager.mapper.toEntity
import me.thejramon.taskmanager.service.UserService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
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
import java.util.*

@WebMvcTest(UserController::class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
class UserControllerTest(@Autowired private val mockMvc: MockMvc) {

    @MockBean
    private lateinit var userService: UserService

    @Test
    fun `should register a new user`() {
        val userDTO = UserDTO(id = null, username = "Alice")
        given(userService.save(any())).willReturn(userDTO.toEntity())

        mockMvc.perform(
            post("/api/users").contentType(MediaType.APPLICATION_JSON)
                .content("""{"username": "Alice", "password": "securePass123"}""")
        ).andExpect(status().isOk).andDo(
            document(
                "register-user", requestFields(
                    fieldWithPath("username").description("Username for the new user"),
                    fieldWithPath("password").description("Password for the new user")
                ), responseFields(
                    fieldWithPath("id").description("ID of the newly created user"),
                    fieldWithPath("username").description("Username of the newly created user")
                )
            )
        )
    }

    @Test
    fun `should retrieve a user by ID`() {
        val userDTO = UserDTO(id = 1L, username = "Alice")
        given(userService.findById(1L)).willReturn(Optional.of(userDTO.toEntity()))

        mockMvc.perform(get("/api/users/1")).andExpect(status().isOk).andDo(
            document(
                "get-user", responseFields(
                    fieldWithPath("id").description("ID of the user"),
                    fieldWithPath("username").description("Username of the user")
                )
            )
        )
    }



    @Test
    fun `should return 404 when retrieving a non-existing user`() {
        given(userService.findById(99L)).willReturn(Optional.empty())

        mockMvc.perform(get("/api/users/99")).andExpect(status().isNotFound).andDo(document("get-user-not-found"))
    }
}
