package com.fitem.ktgames.games.net

/**
 * Created by LeiGuangwu on 2019-06-28.
 */
class BaseResponse<T>(
    val code: Int,
    val msg: String,
    val data: T
)