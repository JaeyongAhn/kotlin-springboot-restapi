package com.popfunding.api.v1.admin.service

import com.popfunding.api.config.logger
import com.popfunding.api.v1.admin.dto.AdminChangePasswordDto
import com.popfunding.api.v1.admin.dto.AdminDto
import com.popfunding.api.v1.admin.dto.AdminSigninDto
import com.popfunding.api.v1.admin.entity.Admin
import com.popfunding.api.v1.admin.repository.AdminRepository
import com.popfunding.api.v1.advice.CUserLoginFailedException
import com.popfunding.api.v1.advice.CUserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class AdminService : UserDetailsService {
    val logger = logger()

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var adminRepository: AdminRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        return adminRepository.findFirstByUsername(username) ?: throw CUserNotFoundException()
    }

    @Transactional
    fun signup(adminDto: AdminDto): Boolean {
        adminRepository.save(Admin(adminDto, passwordEncoder.encode(adminDto.username + adminDto.password)))
        return true
    }

    fun signin(adminSigninDto: AdminSigninDto): Boolean {
        val admin: Admin =
            adminRepository.findFirstByUsername(adminSigninDto.username) ?: throw CUserLoginFailedException()
        if (!passwordEncoder.matches(adminSigninDto.username + adminSigninDto.password, admin.password)
        ) throw CUserLoginFailedException()

        return true
    }

    @Transactional
    fun changePassword(adminChangePasswordDto: AdminChangePasswordDto) {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val username: String = authentication.name
        val admin: Admin = adminRepository.findFirstByUsername(username) ?: throw CUserLoginFailedException()
        if (!passwordEncoder.matches(
                admin.username + adminChangePasswordDto.password,
                admin.password
            )
        ) throw CUserLoginFailedException()

        admin.setPassword(passwordEncoder.encode(admin.username + adminChangePasswordDto.new_password))

        adminRepository.save(admin)
    }

}