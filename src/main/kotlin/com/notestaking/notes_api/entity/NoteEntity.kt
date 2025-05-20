package com.notestaking.notes_api.entity

import jakarta.persistence.*

@Entity
@Table(name = "notes")
class NoteEntity(

    @Column
    var noteTitle: String,

    @Column(columnDefinition = "TEXT")
    var noteBody: String,

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    var owner: UserEntity,

    @ManyToOne
    @JoinColumn(name = "folder_id")
    var folder: FolderEntity

): BaseEntity()