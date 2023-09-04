package com.uhdc.pipe.file.domain

const val LIST_SIZE = 100
const val BLOCK_SIZE = 100

const val FILE_PATH1 = "/Users/kimjihyung/source/uhdc/uhdc-pipe/entr_reco_m.csv"
const val FILE_PATH2 = "/Users/kimjihyung/source/uhdc/uhdc-pipe/entr_reco_d.csv"

class ProcessStatus{

    companion object {

        //static 메소드
        fun setStatus1(status: String)
        {
            FILE_PATH1_STATUS = status
        }

        fun setStatus2(status : String)
        {
            FILE_PATH2_STATUS = status
        }
        //static 변수
        var FILE_PATH1_STATUS : String = "READY"
        var FILE_PATH2_STATUS : String = "READY"
    }


}