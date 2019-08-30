package com.fitem.ktgames.games.model.bean

import java.io.Serializable

/**
 * Created by Fitem on 2018/3/22.
 */

data class LiveDetail(
    val enable: Int,
    val game_type: String,
    val is_followed: Int,
    val live_id: String,
    val live_img: String,
    val live_name: String,
    val live_nickname: String,
    val live_online: Long,
    val live_title: String,
    val live_type: String,
    val live_userimg: String,
    val stream_list: List<StreamListBean>
) : Serializable {

    data class StreamListBean(
        val type: String,
        val url: String
    ) : Serializable
}
