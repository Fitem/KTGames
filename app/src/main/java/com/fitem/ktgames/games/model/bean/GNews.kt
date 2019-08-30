package com.fitem.ktgames.games.model.bean

import java.io.Serializable

/**
 *
 * Created by Fitem on 2018/3/19.
 */

data class GNews(
    val template: String,
    val lmodify: String,
    val source: String,
    val postid: String,
    val title: String,
    val mtime: String,
    val hasImg: Int,
    val topic_background: String,
    val digest: String,
    val boardid: String,
    val alias: String,
    val hasAD: Int,
    val imgsrc: String,
    val ptime: String,
    val daynum: String,
    val hasHead: Int,
    val imgType: Int,
    val order: Int,
    val votecount: Int,
    val isHasCover: Boolean,
    val docid: String,
    val tname: String,
    val url_3w: String,
    val priority: Int,
    val url: String,
    val ename: String,
    val replyCount: Int,
    val ltitle: String,
    val isHasIcon: Boolean,
    val subtitle: String,
    val cid: String,
    val skipID: String,
    val specialID: String,
    val skipType: String,
    val length: Int,
    val videosource: String,
    val videoID: String,
    val tags: String,
    val wap_portal: List<WapPortalBean>,
    val wap_pluginfo: List<WapPluginfoBean>,
    val imgextra: List<ImgextraBean>
) : Serializable {

    data class WapPortalBean(
        val subtitle: String,
        val title: String,
        val imgsrc: String,
        val animation_icon: String,
        val url: String
    ) : Serializable

    data class WapPluginfoBean(
        val subtitle: String,
        val title: String,
        val imgsrc: String,
        val animation_icon: String,
        val url: String
    ) : Serializable

    data class ImgextraBean(val imgsrc: String) : Serializable
}
