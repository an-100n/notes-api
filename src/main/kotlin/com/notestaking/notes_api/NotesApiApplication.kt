package com.notestaking.notes_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import io.github.cdimascio.dotenv.Dotenv


@SpringBootApplication
class NotesApiApplication

fun main(args: Array<String>) {

    val dotenv = Dotenv.configure()
        .filename(".env")
        .ignoreIfMissing()
        .load()

    dotenv.entries().forEach { (key, value) ->
        if (System.getProperty(key) == null) {
            System.setProperty(key, value)
        }
    }
    runApplication<NotesApiApplication>(*args)
}

