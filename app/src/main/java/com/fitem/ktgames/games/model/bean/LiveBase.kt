package com.fitem.ktgames.games.model.bean

import java.io.Serializable

/**
 * Created by Fitem on 2018/3/21.
 */

data class LiveBase<T>(
    val msg: String,
    val result: T,
    val status: String
) : Serializable
