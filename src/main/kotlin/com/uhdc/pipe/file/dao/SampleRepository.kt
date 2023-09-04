package com.uhdc.pipe.file.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SampleRepository: JpaRepository<SampleEntity, String>, SampleJdbcRespository {
}