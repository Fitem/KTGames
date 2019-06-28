package com.example.ktgames.games.net.exception

/**
 * Created by LeiGuangwu on 2019-06-28.
 */
class ApiException : RuntimeException {
    private var code: Int? = null

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))
}