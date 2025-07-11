package com.notestaking.notes_api.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "folders",
    uniqueConstraints = [UniqueConstraint(columnNames = ["folder_name", "user_id"])]
)
class FolderEntity(

    @Column(nullable = false, length = 150)
    var folderName: String = "",

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity? = null,

    @OneToMany(mappedBy = "folder", cascade = [CascadeType.ALL], orphanRemoval = true)
    var notes: MutableList<NoteEntity> = mutableListOf()

) : BaseEntity()


//@Entity
//@Table(name = "folders", uniqueConstraints = [UniqueConstraint(columnNames = ["folder_name", "user_id"])])
// class FolderEntity(
//    @Column(nullable = false, length = 150)
//    var folderName : String,
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    var user: UserEntity,
//
//    @OneToMany(mappedBy = "folder", cascade = [CascadeType.ALL], orphanRemoval = true)
//    var notes: MutableList<NoteEntity> = mutableListOf()
//): BaseEntity()