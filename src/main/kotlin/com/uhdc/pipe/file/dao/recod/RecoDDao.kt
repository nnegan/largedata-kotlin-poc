package com.uhdc.pipe.file.dao.recod

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class RecoDDao(
    private val recoDRepository: RecoDRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)


    fun insertRecoDList(recoDList: List<RecoDEntity>){
        recoDRepository.insertRecoDList(recoDList)
    }

    fun deleteRecoDList(entrNoList: List<Long>){
        recoDRepository.deleteRecoDList(entrNoList)
    }


}