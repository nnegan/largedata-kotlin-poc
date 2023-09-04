package com.uhdc.pipe.file.dao.recod

interface RecoDJdbcRespository{

    fun insertRecoDList(recoDList: List<RecoDEntity>)

    fun deleteRecoDList(entrNoList: List<Long>)
}