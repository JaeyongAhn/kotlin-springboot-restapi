package com.popfunding.api.v1.user.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import com.popfunding.api.config.logger

@RestController
class UserController {
    val logger = logger()

    @GetMapping("/user/create")
    @ResponseBody
    fun userCreate(): String{
        logger.info("user create called")
        Thread.sleep(20000)
        return "hi"
    }
}