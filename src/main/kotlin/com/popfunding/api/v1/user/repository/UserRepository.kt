package com.popfunding.api.v1.user.repository

import com.popfunding.api.v1.admin.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findFirstByUsername(username: String?): User?
}