package com.uhdc.pipe.file.controller

import com.uhdc.pipe.file.service.FileService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/pipe/file/v1"])
class FileController(
    private val fileService: FileService,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/{fileType}")
    fun fileToDatabaseProcess(@PathVariable fileType: String): String{
        return fileService.fileToDatabaseProcess(fileType);
    }

    @GetMapping("/stat")
    fun stat(){
        log.debug("totalMemory : {}", Runtime.getRuntime().totalMemory())
        log.debug("usedMemory  : {}", Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
    }
}