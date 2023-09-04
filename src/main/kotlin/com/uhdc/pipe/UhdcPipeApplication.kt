package com.uhdc.pipe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class UhdcPipeApplication

fun main(args: Array<String>) {
    runApplication<UhdcPipeApplication>(*args)
}
