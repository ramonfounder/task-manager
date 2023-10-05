package me.thejramon.taskmanager.service

import me.thejramon.taskmanager.domain.User
import me.thejramon.taskmanager.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userService: UserService

    @Test
    fun `should register a new user`() {
        val user = User(
            username = "thejramon", password = "something"
        )
        `when`(userRepository.save(any())).thenReturn(user)

        val result = userService.save(user)

        assertEquals(user, result)
    }

    @Test
    fun `should retrieve a user by ID`() {
        val user = User(
            username = "thejramon", password = "something"
        )
        `when`(userRepository.findById(1L)).thenReturn(Optional.of(user))

        val result = userService.findById(1L)

        assertTrue(result.isPresent)
        assertEquals(user, result.get())
    }

    @Test
    fun `should return empty when retrieving a non-existing user`() {
        `when`(userRepository.findById(99L)).thenReturn(Optional.empty())

        val result = userService.findById(99L)

        assertFalse(result.isPresent)
    }

    @Test
    fun `should delete a user`() {
        val idToDelete = 1L
        doNothing().`when`(userRepository).deleteById(idToDelete)

        userService.deleteById(idToDelete)

        verify(userRepository, times(1)).deleteById(idToDelete)
    }

}
