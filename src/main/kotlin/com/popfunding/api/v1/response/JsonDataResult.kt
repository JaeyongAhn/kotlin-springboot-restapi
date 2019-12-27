package com.popfunding.api.v1.response

data class JsonDataResult<T>(
    val status: Int,
    val message: String,
    val data: T
)