package com.popfunding.api.v1.admin.dto

import javax.validation.constraints.Size

data class AdminChangePasswordDto(
    @get:Size(min = 3, max = 20, message = "password는 3 ~ 20 글자 범위에서 입력해주세요")
    val password: String,
    @get:Size(min = 3, max = 20, message = "password는 3 ~ 20 글자 범위에서 입력해주세요")
    val new_password: String
)