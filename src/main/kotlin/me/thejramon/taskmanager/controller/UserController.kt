package me.thejramon.taskmanager.controller

import me.thejramon.taskmanager.dto.UserDTO
import me.thejramon.taskmanager.mapper.toDTO
import me.thejramon.taskmanager.mapper.toEntity
import me.thejramon.taskmanager.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(@Autowired private val userService: UserService) {


    @PostMapping
    fun registerUser(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val savedUser = userService.save(userDTO.toEntity())
        return ResponseEntity.ok(savedUser.toDTO())
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDTO>> {
        val users = userService.findAll().map { it.toDTO() }
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDTO> {
        val user = userService.findById(id)
        return user.map { ResponseEntity.ok(it.toDTO()) }.orElse(ResponseEntity.notFound().build())
    }

}
