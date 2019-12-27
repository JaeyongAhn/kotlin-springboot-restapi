package com.popfunding.api.v1.user.controller

import com.popfunding.api.v1.advice.CValidationException
import com.popfunding.api.v1.response.JsonResult
import com.popfunding.api.v1.user.dto.UserDto
import com.popfunding.api.v1.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class LoginController {

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/signup")
    @ResponseBody
    fun signup(@RequestBody @Valid userDto: UserDto, bindingResult: BindingResult): JsonResult {
        if (bindingResult.hasErrors()) {
            throw CValidationException(bindingResult.allErrors)
        }

        userService.save(userDto)

        return JsonResult(0, userDto.username)
    }
}