package me.thejramon.taskmanager.mapper

import me.thejramon.taskmanager.domain.User
import me.thejramon.taskmanager.dto.UserDTO

fun User.toDTO() = UserDTO(
    id = this.id, username = this.username
)


fun UserDTO.toEntity(): User {
    return User(id, username, "")
}

