package com.example.ktgames.games.module.bean

import java.io.Serializable

/**
 * Created by Fitem on 2018/3/21.
 */

data class LiveItem(
    val enable: Int,
    val game_type: String,
    val live_id: String,
    val live_img: String,
    val live_name: String,
    val live_nickname: String,
    val live_online: Long,
    val live_title: String,
    val live_type: String,
    val live_userimg: String,
    val show_type: String
) : Serializable