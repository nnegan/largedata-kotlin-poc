package com.uhdc.pipe.file.dao.recom

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecoMRepository: JpaRepository<RecoMEntity, String>, RecoMJdbcRespository {
}