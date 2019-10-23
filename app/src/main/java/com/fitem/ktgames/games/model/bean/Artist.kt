package com.fitem.ktgames.games.model.bean

import com.fitem.ktgames.games.ui.main.Constants
import java.io.Serializable

/**
 * Created by yonglong on 2016/11/23.
 */

data class Artist(
    var name: String,
    var id: Long,
    var artistId: String,
    var count: Int,
    var type: String = Constants.PLAYLIST_LOCAL_ID,
    var picUrl: String,
    var desc: String,
    var musicSize: Int = 0,
    var score: Int = 0,
    var albumSize: Int = 0,
    var songs: List<Music>
) : Serializable