package me.thejramon.taskmanager.repository

import me.thejramon.taskmanager.domain.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Test
    fun `should save a new user`() {
        val user = User(
            username = "thejramon", password = "something"
        )
        val savedUser = userRepository.save(user)

        assertNotNull(savedUser.id)
        assertEquals(user.username, savedUser.username)
    }

    @Test
    fun `should retrieve a user by ID`() {
        val user = User(
            username = "thejramon", password = "something"
        )
        val persistedUser = entityManager.persistFlushFind(user)

        val foundUser = userRepository.findById(persistedUser.id!!)

        assertTrue(foundUser.isPresent)
        assertEquals(user.username, foundUser.get().username)
    }

    @Test
    fun `should return empty when retrieving a non-existing user`() {
        val foundUser = userRepository.findById(99L)

        assertFalse(foundUser.isPresent)
    }

    @Test
    fun `should delete a user`() {
        val user = User(
            username = "thejramon", password = "something"
        )
        val persistedUser = entityManager.persist(user)
        userRepository.deleteById(persistedUser.id!!)
        val deletedUser = userRepository.findById(persistedUser.id!!)

        assertFalse(deletedUser.isPresent)
    }
}
