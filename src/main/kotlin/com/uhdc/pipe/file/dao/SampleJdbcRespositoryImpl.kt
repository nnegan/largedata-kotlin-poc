package com.uhdc.pipe.file.dao

import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.PreparedStatement
import java.sql.SQLException


class SampleJdbcRespositoryImpl (
    private val jdbcTemplate: JdbcTemplate
): SampleJdbcRespository {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun insertSampleList(sampleList: List<SampleEntity>) {
        //log.debug("jdbcTemplate.fetchSize : {}", jdbcTemplate.fetchSize)
        //sampleList.chunked(100).map {
            jdbcTemplate!!.batchUpdate("" +
                    //      "insert into sample (sample_id, sample_second_id, code, meta1, meta2, meta3, meta4, meta5, meta6, meta7, meta8, meta9, meta10, meta11, meta12, meta13, meta14, meta15, meta16, meta17, meta18) values " +
                    //    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            "insert into entr_reco_m (entr_no, cust_no, use_yn, created_at, created_by, modified_at, modified_by) values " +
                    "(?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)",
//                    "insert into entr_reco_d (entr_no, cust_no, insp_cd, reco_seq, template_cd, variable_value,  custom_yn, custom_comment,   use_yn, created_at, created_by, modified_at, modified_by) values " +
  //                  "(?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)",
                object : BatchPreparedStatementSetter {
                    @Throws(SQLException::class)
                    override fun setValues(ps: PreparedStatement, i: Int) {
                        /*ps.setLong(1, sampleList[i].entrNo)
                        ps.setString(2, sampleList[i].custNo)
                        ps.setString(3, sampleList[i].inspCd)
                        ps.setString(4, sampleList[i].recoSeq)

                        ps.setString(5, sampleList[i].templateCd)
                        ps.setString(6, sampleList[i].variableValue)
                        ps.setString(7, sampleList[i].customYn)
                        ps.setString(8, sampleList[i].customComment)
                        ps.setString(9, sampleList[i].useYn)
                        ps.setString(10, "CREATE")
                        ps.setString(11, "UPDATE")*/


                    ps.setLong(1, sampleList[i].entrNo)
                    ps.setString(2, sampleList[i].custNo)
                    ps.setString(3, sampleList[i].useYn)
                    ps.setString(4, "CREATE")
                    ps.setString(5, "UPDATE")

                        /*
                    ps.setString(1, sampleList[i].sampleId.toString())
                    ps.setString(2, sampleList[i].sampleSecondId)
                    ps.setString(3, sampleList[i].code)

                    ps.setString(4, sampleList[i].meta1)
                    ps.setString(5, sampleList[i].meta2)
                    ps.setString(6, sampleList[i].meta3)
                    ps.setString(7, sampleList[i].meta4)
                    ps.setString(8, sampleList[i].meta5)
                    ps.setString(9, sampleList[i].meta6)
                    ps.setString(10, sampleList[i].meta7)
                    ps.setString(11, sampleList[i].meta8)
                    ps.setString(12, sampleList[i].meta9)
                    ps.setString(13, sampleList[i].meta10)
                    ps.setString(14, sampleList[i].meta11)
                    ps.setString(15, sampleList[i].meta12)
                    ps.setString(16, sampleList[i].meta13)
                    ps.setString(17, sampleList[i].meta14)
                    ps.setString(18, sampleList[i].meta15)
                    ps.setString(19, sampleList[i].meta16)
                    ps.setString(20, sampleList[i].meta17)
                    ps.setString(21, sampleList[i].meta18)
*/
                    }

                    override fun getBatchSize(): Int {
                        //log.debug("sampleList.size : {}", sampleList.size)
                        return sampleList.size
                    }
                })
       // }
        //log.debug("jdbcTemplate.maxRows : {}", jdbcTemplate.maxRows)

        //log.debug("End insertSampleList ")

    }

}