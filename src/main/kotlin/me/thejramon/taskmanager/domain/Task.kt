package me.thejramon.taskmanager.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var title: String,

    var description: String,

    @Enumerated(EnumType.STRING)
    var priority: Priority,

    @Enumerated(EnumType.STRING)
    var status: Status,

    var dueDate: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)


enum class Priority {
    HIGH, MEDIUM, LOW
}

enum class Status {
    TODO, IN_PROGRESS, DONE
}