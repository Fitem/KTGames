package com.fitem.ktgames.games.model.bean

import java.io.Serializable

/**
 * Created by Fitem on 2018/3/20.
 */

data class GNewsDetail(
    val template: String,
    val sourceinfo: SourceinfoBean,
    val shareLink: String,
    val source: String,
    val threadVote: Int,
    val title: String,
    var body: String,
    val tid: String,
    val isPicnews: Boolean,
    val advertiseType: String,
    val articleType: String,
    val digest: String,
    val statement: String,
    val ptime: String,
    val docid: String,
    val threadAgainst: Int,
    val isHasNext: Boolean,
    val articleTags: String,
    val dkeys: Any,
    val replyCount: Int,
    val voicecomment: String,
    val replyBoard: String,
    val category: String,
    val img: List<ImgBean>,
    val searchKw: List<SearchKwBean>,
    val topiclist_news: List<*>,
    val book: List<*>,
    val link: List<*>,
    val relative_sys: List<RelativeSysBean>,
    val boboList: List<*>,
    val relative_res: List<RelativeResBean>,
    val ydbaike: List<*>,
    val votes: List<*>,
    val topiclist: List<TopiclistBean>
) : Serializable {


    data class SourceinfoBean(
        val ename: String,
        val alias: String,
        val tname: String,
        val topic_icons: String,
        val tid: String
    ) : Serializable

    data class ImgBean(
        val ref: String,
        val src: String,
        val alt: String,
        val pixel: String
    ) : Serializable

    data class SearchKwBean(
        val weight: String,
        val keyword: String,
        val tag_source: Int
    ) : Serializable

    data class RelativeSysBean(
        val docID: String,
        val digest: String,
        val index: Int,
        val from: String,
        val href: String,
        val id: String,
        val imgsrc: String,
        val keyword: String,
        val recallBy: String,
        val title: String,
        val type: String,
        val ptime: String
    ) : Serializable

    data class RelativeResBean(
        val docID: String,
        val index: Int,
        val from: String,
        val href: String,
        val id: String,
        val imgsrc: String,
        val recallBy: String,
        val title: String,
        val type: String,
        val ptime: String
    ) : Serializable

    data class TopiclistBean(
        val ename: String,
        val isHasCover: Boolean,
        val tname: String,
        val alias: String,
        val subnum: String,
        val tid: String,
        val cid: String
    ) : Serializable
}
