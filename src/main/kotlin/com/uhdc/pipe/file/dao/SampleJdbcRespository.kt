package com.uhdc.pipe.file.dao

interface SampleJdbcRespository{

    fun insertSampleList(sampleList: List<SampleEntity>)

}