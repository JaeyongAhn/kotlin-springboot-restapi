package com.popfunding.api.v1.user.service

import com.popfunding.api.config.logger
import com.popfunding.api.v1.admin.entity.User
import com.popfunding.api.v1.advice.CUserNotFoundException
import com.popfunding.api.v1.user.dto.UserDto
import com.popfunding.api.v1.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService : UserDetailsService {
    val logger = logger()

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    override fun loadUserByUsername(username: String?): UserDetails {
        return userRepository.findFirstByUsername(username) ?: throw CUserNotFoundException()
    }

    @Transactional
    fun signup(userDto: UserDto): Boolean {
        userRepository.save(User(userDto, passwordEncoder.encode(userDto.username + userDto.password)))
        return true
    }
}