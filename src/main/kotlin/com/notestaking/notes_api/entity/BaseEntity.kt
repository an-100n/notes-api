package com.notestaking.notes_api.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    var id: UUID? = null

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null

    @PrePersist
    fun onCreate() {
        this.createdAt = LocalDateTime.now()
    }

    @PreUpdate
    fun onUpdate() {
        this.updatedAt = LocalDateTime.now()
    }
}