package com.popfunding.api.v1.admin.controller

import com.popfunding.api.config.MessageLoader
import com.popfunding.api.config.security.JwtTokenProvider
import com.popfunding.api.v1.admin.dto.AdminChangePasswordDto
import com.popfunding.api.v1.admin.dto.AdminDto
import com.popfunding.api.v1.admin.dto.AdminSigninDto
import com.popfunding.api.v1.admin.service.AdminService
import com.popfunding.api.v1.advice.CValidationException
import com.popfunding.api.v1.response.JsonDataResponse
import com.popfunding.api.v1.response.JsonResponse
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

    @Autowired
    lateinit var messageLoader: MessageLoader

    @PostMapping("/signup")
    @ResponseBody
    fun signup(@RequestBody @Valid adminDto: AdminDto, bindingResult: BindingResult): JsonResponse {
        if (bindingResult.hasErrors()) {
            throw CValidationException(bindingResult.allErrors)
        }

        adminService.signup(adminDto)

        return JsonResponse(0, messageLoader.getMessage("success.msg"))
    }

    @PostMapping("/signin")
    @ResponseBody
    fun login(@RequestBody @Valid adminSigninDto: AdminSigninDto, bindingResult: BindingResult): JsonDataResponse<String> {
        if (bindingResult.hasErrors()) {
            throw CValidationException(bindingResult.allErrors)
        }

        adminService.login(adminSigninDto)

        return JsonDataResponse<String>(0, "", jwtTokenProvider.createToken(adminSigninDto.username, "ADMIN"))
    }

    @PostMapping("/change_password")
    @ResponseBody
    fun changePassword(@RequestBody @Valid adminChangePasswordDto: AdminChangePasswordDto, bindingResult: BindingResult): JsonResponse {
        if (bindingResult.hasErrors()) {
            throw CValidationException(bindingResult.allErrors)
        }

        adminService.changePassword(adminChangePasswordDto)

        return JsonResponse(0, adminChangePasswordDto.password)
    }
}