package com.popfunding.api.v1.response

data class JsonListResult<T>(
    val status: Int,
    val message: String,
    val list: List<T>
)