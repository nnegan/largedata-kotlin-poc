package com.uhdc.pipe.file.dao.recom

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Timestamp

@Entity
@Table(name = "entr_reco_m_tst")
class RecoMEntity(

    @Id
    val entrNo: Long,

    val custNo: String,
    val useYn: String,

    val createdAt: Timestamp? = null,
    val createdBy: String? = null,
    val modifiedAt: Timestamp? = null,
    val modifiedBy: String? = null,
)