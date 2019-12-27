package com.popfunding.api.v1.user.service

import com.popfunding.api.config.logger
import com.popfunding.api.v1.user.dto.UserDto
import com.popfunding.api.v1.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.popfunding.api.v1.user.entity.User
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService {
    val logger = logger()

    @Autowired
    lateinit var userRepository: UserRepository

    fun save(userDto: UserDto): Boolean {
        val user = User(userDto, mutableSetOf("ROLE_USER"))
        logger.info("${user.roles}")
        userRepository.save(user)
        return true
    }
}