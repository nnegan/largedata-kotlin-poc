package com.uhdc.pipe.file.dao.recod

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecoDRepository: JpaRepository<RecoDEntity, String>, RecoDJdbcRespository {
}