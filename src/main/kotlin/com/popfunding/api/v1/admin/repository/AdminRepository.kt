package com.popfunding.api.v1.admin.repository

import com.popfunding.api.v1.admin.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Long> {
    fun findByUsername(username: String?): Admin?
    fun findFirstByUsername(username: String?): Admin?
}