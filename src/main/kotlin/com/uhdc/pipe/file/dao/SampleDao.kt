package com.uhdc.pipe.file.dao

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SampleDao(
    private val sampleRepository: SampleRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)


    fun insertSampleList(sampleList: List<SampleEntity>){
        //log.debug("Thread Name : {}, Start", Thread.currentThread().name)
       // Thread.sleep(1000)
        //log.debug("Thread Name : {}, End", Thread.currentThread().name)
        sampleRepository.insertSampleList(sampleList)
    }

    fun saveAll(sampleList: List<SampleEntity>){
        sampleRepository.saveAll(sampleList)
    }

}