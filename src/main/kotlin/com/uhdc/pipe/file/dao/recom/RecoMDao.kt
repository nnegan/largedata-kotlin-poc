package com.uhdc.pipe.file.dao.recom

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class RecoMDao(
    private val recoMRepository: RecoMRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)


    fun insertRecoMList(recoMList: List<RecoMEntity>){
        recoMRepository.insertRecoMList(recoMList)
    }

}