package com.fitem.ktgames.games.model.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by LeiGuangwu on 2019-10-23.
 */

data class ArtistsInfo(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("list")
    val list: List
) : Serializable {

    data class List(
        @SerializedName("artists")
        val artists: kotlin.collections.List<ArtistInfo>,
        @SerializedName("updateTime")
        val updateTime: Long = 0,
        @SerializedName("type")
        val type: Int = 0
    ) : Serializable {

        data class ArtistInfo(
            @SerializedName("lastRank")
            val lastRank: Int = 0,
            @SerializedName("img1v1Url")
            val imgVUrl: String = "",
            @SerializedName("musicSize")
            val musicSize: Int = 0,
            @SerializedName("img1v1Id")
            val imgVId: Long = 0,
            @SerializedName("albumSize")
            val albumSize: Int = 0,
            @SerializedName("picUrl")
            val picUrl: String = "",
            @SerializedName("score")
            val score: Int = 0,
            @SerializedName("topicPerson")
            val topicPerson: Int = 0,
            @SerializedName("briefDesc")
            val briefDesc: String = "",
            @SerializedName("name")
            val name: String = "",
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("picId")
            val picId: Long = 0,
            @SerializedName("trans")
            val trans: String = ""
        ) : Serializable
    }

}