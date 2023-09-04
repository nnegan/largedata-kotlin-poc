package com.uhdc.pipe.file.dao

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "sample")
class SampleEntity(

    /*@Id
    val entrNo: Long,

    val custNo: String,
    val inspCd: String,

    val recoSeq: String,

    val templateCd: String,
    val variableValue: String,
    val customYn: String,
    val customComment: String,
    val useYn: String,*/

    @Id
    val entrNo: Long,

    val custNo: String,
    val useYn: String,

/*    @Id
    val sampleId: Long,

    val sampleSecondId: String,
    val code: String,
    val meta1: String,
    val meta2: String,
    val meta3: String,
    val meta4: String,
    val meta5: String,
    val meta6: String,
    val meta7: String,
    val meta8: String,
    val meta9: String,
    val meta10: String,
    val meta11: String,
    val meta12: String,
    val meta13: String,
    val meta14: String,
    val meta15: String,
    val meta16: String,
    val meta17: String,
    val meta18: String,*/

)