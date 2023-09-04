package com.uhdc.pipe.file.dao.recod

import jakarta.persistence.*
import java.io.Serializable
import java.sql.Timestamp

@Entity
@Table(name = "entr_reco_d_tst")
class RecoDEntity(

    @EmbeddedId
    val recoDEntityPK: RecoDEntityPK,

    val templateCd: String,
    val variableValue: String,
    val customYn: String,
    val customComment: String,
    val useYn: String,

    val createdAt: Timestamp? = null,
    val createdBy: String? = null,
    val modifiedAt: Timestamp? = null,
    val modifiedBy: String? = null,

)

@Embeddable
data class RecoDEntityPK(
    @Column()
    val entrNo: Long,
    val custNo: String,
    val inspCd: String,
    val recoSeq: String,
): Serializable