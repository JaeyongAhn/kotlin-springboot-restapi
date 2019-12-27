package com.popfunding.api.v1.user.service

import com.popfunding.api.v1.advice.CUserNotFoundException
import com.popfunding.api.v1.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl : UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        return userRepository.findByUsername(username) ?: throw CUserNotFoundException()
    }
}