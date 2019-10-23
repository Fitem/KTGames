package com.fitem.ktgames.games.model.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by LeiGuangwu on 2019-10-23.
 */
data class PersonalizedInfo(
    @SerializedName("result")
    val result: List<PersonalizedItem>,
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("category")
    val category: Int = 0,
    @SerializedName("hasTaste")
    val hasTaste: Boolean = false
)

/**
 *推荐歌单 result数组中的一条数据
{
"id": 2774618537,
"type": 0,
"name": "深夜我没有睡着, 我只是藏进了歌里",
"copywriter": "编辑推荐：明天开始一切都会好的，祝你幸福！",
"picUrl": "https://p1.music.126.net/KnD58aUg88-4SSEo6XnQbg==/109951164107137763.jpg",
"canDislike": false,
"playCount": 3562901.2,
"trackCount": 45,
"highQuality": false,
"alg": "featured"


//推荐mv 新增字段
"duration": 282000
"subed": false,
"artists": [
{
"id": 99292,
"name": "Pentatonix"
}
],
"artistName": "Pentatonix",
"artistId": 99292,

},
 */
data class PersonalizedItem(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("copywriter")
    val copywriter: String = "",
    @SerializedName("picUrl")
    val picUrl: String = "",
    @SerializedName("canDislike")
    val canDislike: Boolean = false,
    @SerializedName("playCount")
    val playCount: Float = 0f,
    @SerializedName("trackCount")
    val trackCount: Int = 0,
    @SerializedName("highQuality")
    val highQuality: Boolean = false,

    @SerializedName("duration")
    val duration: Int = 0,
    @SerializedName("subed")
    val subed: Boolean = false,
    @SerializedName("artists")
    val artists: MutableList<ArtistsItem>?,
    @SerializedName("artistName")
    val artistName: String = "",
    @SerializedName("artistId")
    val artistId: Int = 0,

    @SerializedName("alg")
    val alg: String = ""
)

data class ArtistsItem(
    @SerializedName("picUrl")
    val picUrl: String = "",
    @SerializedName("img1v1Url")
    val imgVUrl: String = "",
    @SerializedName("briefDesc")
    val briefDesc: String = "",
    @SerializedName("musicSize")
    val musicSize: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("img1v1Id")
    val imgVId: Int = 0,
    @SerializedName("id")
    val id: String,
    @SerializedName("picId")
    val picId: Int = 0,
    @SerializedName("albumSize")
    val albumSize: Int = 0,
    @SerializedName("trans")
    val trans: String = ""
)
