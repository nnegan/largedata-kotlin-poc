package com.uhdc.pipe.file.service

import com.uhdc.pipe.file.dao.SampleDao
import com.uhdc.pipe.file.dao.SampleEntity
import com.uhdc.pipe.file.dao.recod.RecoDDao
import com.uhdc.pipe.file.dao.recod.RecoDEntity
import com.uhdc.pipe.file.dao.recod.RecoDEntityPK
import com.uhdc.pipe.file.dao.recom.RecoMDao
import com.uhdc.pipe.file.dao.recom.RecoMEntity
import com.uhdc.pipe.file.domain.*
import com.uhdc.pipe.file.domain.ProcessStatus.Companion.FILE_PATH1_STATUS
import com.uhdc.pipe.file.domain.ProcessStatus.Companion.FILE_PATH2_STATUS
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.io.*


// 참조 : https://backtony.github.io/jpa/2021-08-12-jpa-springdatajpa-2/


@Service
class FileServiceImpl(
    private val sampleDao: SampleDao,

    private val recoMFile: RecoMFile,
    private val recoDFile: RecoDFile,
    ): FileService {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun fileToDatabaseProcess(fileType: String): String {

        log.debug("Start time")

        val startTime1 = System.currentTimeMillis()
        log.debug("시작 시간 : {}", startTime1)

        var result = if ( fileType.equals("recom")){
            recoMFile.fileProcRecoM()
        }else if ( fileType.equals("recod")){

            if ( !FILE_PATH1_STATUS.equals("READY")) {
                return "already running"
            }
            //recoDFile.fileProcDeleteRecoD()
            recoDFile.fileProcInsertRecoD()


        } else {
            "Not Found"
        }

        log.debug("걸린 시간 : {}", System.currentTimeMillis() - startTime1)
        log.debug("totalMemory : {}", Runtime.getRuntime().totalMemory())
        log.debug("usedMemory  : {}", Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
        log.debug("End time")

        return result + " 걸린 시간 : " + ((System.currentTimeMillis() - startTime1)/60000) + " 분"
    }

    fun parallelProc(blockList: List <List<SampleEntity>>){
        blockList.parallelStream()
            .forEach { block ->
                try {
                    sampleDao.insertSampleList(block)
                } catch (e: DataIntegrityViolationException) { // 키 중복 예외 발생
                    // 로그 찍기
                    log.error("dup Error : {}", e)
                } catch (e: Exception) {
                    log.error("Error : {}", e)
                }
            }

    }

    fun parseSample(line: String): SampleEntity {
        val arr = line.split(",")
        //return SampleEntity(arr[0].toLong(), arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8])
        return SampleEntity(arr[0].toLong(), arr[1], arr[2])
        //return SampleEntity(arr[0].toLong(), arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20])
    }
}

@Service
class RecoMFile (
    private val recoMDao: RecoMDao,
    )
{
    private val log = LoggerFactory.getLogger(javaClass)

    fun fileProcRecoM(): String{

        if ( !FILE_PATH1_STATUS.equals("READY")) {
            return "already running"
        }
        ProcessStatus.setStatus1("RUNNING")

        var lineList = mutableListOf<RecoMEntity>()
        var blockList = mutableListOf<List<RecoMEntity>>()

        val file = File(FILE_PATH1)
        val br = BufferedReader(FileReader(file))

        var lineCount = 0

        try {
            br.forEachLine {
                lineCount++
                lineList.add(parseRecoM(it))

                if ( lineCount % LIST_SIZE == 0) {
                    blockList.add(lineList)
                    if (lineCount % (LIST_SIZE * BLOCK_SIZE) == 0) {
                        //log.debug("lineCount : {} , blockList : {}", lineCount, blockList.size)
                        this.parallelProcRecoM(blockList)
                        blockList = mutableListOf<List<RecoMEntity>>()
                    }

                    if ( lineCount % 100000 == 0 ){
                        logStamp(lineCount)
                    }
                    lineList = mutableListOf<RecoMEntity>()
                }
            }
            // 마지막으로 남은 파일 처리
            blockList.add(lineList)
            this.parallelProcRecoM(blockList)
        }catch(e: Exception){
            e.printStackTrace()
        }finally {
            br.close()
        }
        ProcessStatus.setStatus1("READY")
        return "SUCCESS"
    }


    fun parallelProcRecoM(blockList: List <List<RecoMEntity>>){
        blockList.parallelStream()
            .forEach { block ->
                try {
                    recoMDao.insertRecoMList(block)
                } catch (e: DataIntegrityViolationException) { // 키 중복 예외 발생
                    // 로그 찍기
                    log.error("dup Error : {}", e)
                } catch (e: Exception) {
                    log.error("Error : {}", e)
                }
            }

    }

    fun parseRecoM(line: String): RecoMEntity {
        val arr = line.split(",")
        return RecoMEntity(arr[0].toLong(), arr[1], arr[2])
    }

    fun logStamp(lineCount: Int){
        log.debug("lineCount : {}", lineCount)
        log.debug("totalMemory : {}", Runtime.getRuntime().totalMemory())
        log.debug("usedMemory  : {}", Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
    }

}

@Service
class RecoDFile (
    private val recoDDao: RecoDDao,
)
{
    private val log = LoggerFactory.getLogger(javaClass)

    fun fileProcInsertRecoD(): String{
        if ( !FILE_PATH2_STATUS.equals("READY")) {
            return "already running"
        }
        ProcessStatus.setStatus2("RUNNING")

        val file = File(FILE_PATH2)
        val br = BufferedReader(FileReader(file))

        var lineCount = 0

        var lineList = mutableListOf<RecoDEntity>()
        var blockList = mutableListOf<List<RecoDEntity>>()

        try {
            br.forEachLine {
                lineCount++
                lineList.add(parseRecoD(it))

                if ( lineCount % LIST_SIZE == 0) {
                    blockList.add(lineList)
                    if (lineCount % (LIST_SIZE * BLOCK_SIZE) == 0) {
                        //log.debug("lineCount : {} , blockList : {}", lineCount, blockList.size)
                        this.parallelInsertRecoD(blockList)
                        blockList = mutableListOf<List<RecoDEntity>>()
                    }

                    if ( lineCount % 100000 == 0 ){
                        logStamp(lineCount)
                    }
                    lineList = mutableListOf<RecoDEntity>()
                }
            }
            // 마지막으로 남은 파일 처리
            blockList.add(lineList)
            this.parallelInsertRecoD(blockList)
        }catch(e: Exception){
            e.printStackTrace()
        }finally {
            br.close()
        }
        ProcessStatus.setStatus2("READY")
        return "SUCCESS"
    }

    fun parallelInsertRecoD(blockList: List <List<RecoDEntity>>){
        blockList.parallelStream()
            .forEach { block ->
                try {
                    recoDDao.insertRecoDList(block)
                } catch (e: DataIntegrityViolationException) { // 키 중복 예외 발생
                    // 로그 찍기
                    log.error("dup Error : {}", e)
                } catch (e: Exception) {
                    log.error("Error : {}", e)
                }
            }

    }

    fun fileProcDeleteRecoD(): String{

        val file = File(FILE_PATH2)
        val br = BufferedReader(FileReader(file))

        var lineCount = 0

        var entrNoList = mutableListOf<Long>()
        var entrNoBlockList = mutableListOf<List<Long>>()

        try {
            br.forEachLine {
                lineCount++
                entrNoList.add(parseRecoDDelete(it))

                if ( lineCount % LIST_SIZE == 0) {
                    entrNoBlockList.add(entrNoList)
                    if (lineCount % (LIST_SIZE * BLOCK_SIZE) == 0) {
                        log.debug("lineCount : {} , blockList : {}", lineCount, entrNoBlockList.size)
                        this.parallelDeleteRecoD(entrNoBlockList)
                        entrNoBlockList = mutableListOf<List<Long>>()
                    }

                    if ( lineCount % 100000 == 0 ){
                        log.debug("Delete")
                        logStamp(lineCount)
                    }
                    entrNoList = mutableListOf<Long>()
                }
            }
            // 마지막으로 남은 파일 처리
            entrNoBlockList.add(entrNoList)
            this.parallelDeleteRecoD(entrNoBlockList)
        }catch(e: Exception){
            e.printStackTrace()
        }finally {
            br.close()
        }

        ProcessStatus.setStatus2("READY")
        return "SUCCESS"
    }

    fun parallelDeleteRecoD(blockList: List <List<Long>>){
        blockList.parallelStream()
            .forEach { block ->
                try {
                    recoDDao.deleteRecoDList(block)
                } catch (e: DataIntegrityViolationException) { // 키 중복 예외 발생
                    // 로그 찍기
                    log.error("dup Error : {}", e)
                } catch (e: Exception) {
                    log.error("Error : {}", e)
                }
            }

    }


    fun parseRecoD(line: String): RecoDEntity {
        val arr = line.split(",")
        return RecoDEntity(RecoDEntityPK(arr[0].toLong(), arr[1], arr[2], arr[3]), arr[4], arr[5], arr[6], arr[7], arr[8])
    }

    fun parseRecoDDelete(line: String): Long {
        val arr = line.split(",")
        return arr[0].toLong()
        //return RecoDEntity(RecoDEntityPK(arr[0].toLong(), arr[1], arr[2], arr[3]), arr[4], arr[5], arr[6], arr[7], arr[8])
    }

    fun logStamp(lineCount: Int){
        log.debug("lineCount : {}", lineCount)
        log.debug("totalMemory : {}", Runtime.getRuntime().totalMemory())
        log.debug("usedMemory  : {}", Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
    }

}

