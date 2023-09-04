package com.uhdc.pipe.file.dao.recom

import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.PreparedStatement
import java.sql.SQLException


class RecoMJdbcRespositoryImpl (
    private val jdbcTemplate: JdbcTemplate
): RecoMJdbcRespository {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun insertRecoMList(recoMList: List<RecoMEntity>) {
        jdbcTemplate!!.batchUpdate("" +
            "insert into entr_reco_m_tst (entr_no, cust_no, use_yn, created_at, created_by, modified_at, modified_by) values " +
                    "(?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)",
            object : BatchPreparedStatementSetter {
                @Throws(SQLException::class)
                override fun setValues(ps: PreparedStatement, i: Int) {
                    ps.setLong(1, recoMList[i].entrNo)
                    ps.setString(2, recoMList[i].custNo)
                    ps.setString(3, recoMList[i].useYn)
                    ps.setString(4, "CREATE")
                    ps.setString(5, "UPDATE")

                }

                override fun getBatchSize(): Int {
                    return recoMList.size
                }
            }
        )
    }

}