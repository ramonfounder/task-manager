package me.thejramon.taskmanager.service


import me.thejramon.taskmanager.domain.User
import me.thejramon.taskmanager.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(@Autowired val userRepository: UserRepository) {

    fun findAll(): List<User> = userRepository.findAll()

    fun findById(id: Long): Optional<User> = userRepository.findById(id)

    fun findByUsername(username: String): Optional<User> = userRepository.findByUsername(username)

    fun save(user: User): User = userRepository.save(user)

    fun deleteById(id: Long) = userRepository.deleteById(id)
}
