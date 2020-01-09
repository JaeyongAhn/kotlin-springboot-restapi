package com.popfunding.api.init

import com.popfunding.api.config.logger
import com.popfunding.api.v1.admin.dto.AdminDto
import com.popfunding.api.v1.admin.entity.Admin
import com.popfunding.api.v1.admin.repository.AdminRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class InitComponent : CommandLineRunner {

    val logger = logger()

    @Value("\${spring.jpa.hibernate.ddl-auto}")
    lateinit var ddl_auto: String

    @Autowired
    lateinit var adminRepository: AdminRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    override fun run(vararg args: String?) {
        if (ddl_auto == "create") {
            for (i in 1..100) {
                val adminDto: AdminDto = AdminDto("admin_${i}", "1234")
                adminRepository.save(Admin(adminDto, passwordEncoder.encode(adminDto.username + adminDto.password)))
            }
        }
    }
}