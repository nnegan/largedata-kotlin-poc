package com.uhdc.pipe.file.dao.recod

import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.PreparedStatement
import java.sql.SQLException


class RecoDJdbcRespositoryImpl (
    private val jdbcTemplate: JdbcTemplate
): RecoDJdbcRespository {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun insertRecoDList(recoDList: List<RecoDEntity>) {
        jdbcTemplate!!.batchUpdate("" +
                "insert into entr_reco_d_tst (id, entr_no, cust_no, insp_cd, reco_seq, template_cd, variable_value,  custom_yn, custom_comment,   use_yn, created_at, created_by, modified_at, modified_by) values " +
                "(0, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)",
            object : BatchPreparedStatementSetter {
                @Throws(SQLException::class)
                override fun setValues(ps: PreparedStatement, i: Int) {
                    ps.setLong(1, recoDList[i].recoDEntityPK.entrNo)
                    ps.setString(2, recoDList[i].recoDEntityPK.custNo)
                    ps.setString(3, recoDList[i].recoDEntityPK.inspCd)
                    ps.setString(4, recoDList[i].recoDEntityPK.recoSeq)

                    ps.setString(5, recoDList[i].templateCd)
                    ps.setString(6, recoDList[i].variableValue)
                    ps.setString(7, recoDList[i].customYn)
                    ps.setString(8, recoDList[i].customComment)
                    ps.setString(9, recoDList[i].useYn)
                    ps.setString(10, "CREATE")
                    ps.setString(11, "UPDATE")
                }

                override fun getBatchSize(): Int {
                    return recoDList.size
                }
            }
        )
    }

    override fun deleteRecoDList(entrNoList: List<Long>) {
        jdbcTemplate!!.batchUpdate("delete from entr_reco_d_tst where  " +
                " entr_no =  ?",
            object : BatchPreparedStatementSetter {
                @Throws(SQLException::class)
                override fun setValues(ps: PreparedStatement, i: Int) {
                    ps.setLong(1, entrNoList[i])
//                    ps.setString(2, recoDList[i].recoDEntityPK.custNo)
//                    ps.setString(3, recoDList[i].recoDEntityPK.inspCd)
//                    ps.setString(4, recoDList[i].recoDEntityPK.recoSeq)
                }
                override fun getBatchSize(): Int {
                    return entrNoList.size
                }
            }
        )
    }

}