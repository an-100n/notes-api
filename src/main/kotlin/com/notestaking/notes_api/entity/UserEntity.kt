package com.notestaking.notes_api.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(

    @Column(nullable = false, length = 50, unique = true)
    var username: String = "",

    @Column(nullable = false, length = 50, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String = ""

) : BaseEntity()


//@Entity
//@Table(name = "users")
//class UserEntity (
//
//    @Column(nullable = false, length = 50, unique = true)
//    var username: String,
//    @Column(nullable = false, length = 50, unique = true)
//    var email: String,
//    @Column(nullable = false)
//    var password: String
//
//    ): BaseEntity()

