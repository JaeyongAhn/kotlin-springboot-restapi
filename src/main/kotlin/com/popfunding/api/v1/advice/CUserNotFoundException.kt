package com.popfunding.api.v1.advice

import java.lang.RuntimeException

class CUserNotFoundException : RuntimeException {
    constructor()
    constructor(msg: String)
    constructor(msg: String, t: Throwable)
}