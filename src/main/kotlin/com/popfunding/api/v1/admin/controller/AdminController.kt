package com.popfunding.api.v1.admin.controller

import com.popfunding.api.config.security.JwtTokenProvider
import com.popfunding.api.v1.admin.dto.AdminChangePasswordDto
import com.popfunding.api.v1.admin.dto.AdminDto
import com.popfunding.api.v1.admin.dto.AdminSigninDto
import com.popfunding.api.v1.admin.service.AdminService
import com.popfunding.api.v1.advice.CValidationException
import com.popfunding.api.v1.response.JsonDataResult
import com.popfunding.api.v1.response.JsonResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/admin")
class AdminController {
    @Autowired
    lateinit var adminService: AdminService

    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider

    @PostMapping("/signup")
    @ResponseBody
    fun signup(@RequestBody @Valid adminDto: AdminDto, bindingResult: BindingResult): JsonResult {
        if (bindingResult.hasErrors()) {
            throw CValidationException(bindingResult.allErrors)
        }

        adminService.signup(adminDto)

        return JsonResult(0, adminDto.username)
    }

    @PostMapping("/signin")
    @ResponseBody
    fun signin(@RequestBody @Valid adminSigninDto: AdminSigninDto, bindingResult: BindingResult): JsonDataResult<String> {
        if (bindingResult.hasErrors()) {
            throw CValidationException(bindingResult.allErrors)
        }

        adminService.signin(adminSigninDto)

        return JsonDataResult<String>(0, "", jwtTokenProvider.createToken(adminSigninDto.username, "ADMIN"))
    }

    @PostMapping("/change_password")
    @ResponseBody
    fun changePassword(@RequestBody @Valid adminChangePasswordDto: AdminChangePasswordDto, bindingResult: BindingResult): JsonResult {
        if (bindingResult.hasErrors()) {
            throw CValidationException(bindingResult.allErrors)
        }

        adminService.changePassword(adminChangePasswordDto)

        return JsonResult(0, adminChangePasswordDto.password)
    }
}