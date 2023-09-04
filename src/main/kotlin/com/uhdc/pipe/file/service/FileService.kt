package com.uhdc.pipe.file.service

interface FileService {

    fun fileToDatabaseProcess(fileType: String): String
}