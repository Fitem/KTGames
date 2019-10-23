package com.fitem.ktgames.games.model.bean

import com.fitem.ktgames.games.ui.main.Constants
import java.io.Serializable

/**
 * Created by LeiGuangwu on 2019-10-23.
 */
data class Playlist(
    val id: Long,
    val pid: String,
    val name: String,
    val total: Long,
    val updateDate: Long,
    val updateFrequency: String,
    val date: Long,
    val des: String,
    val order: String,
    val coverUrl: String,
    val type: String = Constants.PLAYLIST_LOCAL_ID,
    val playCount: Long,
    val musicList: List<Music>
) : Serializable