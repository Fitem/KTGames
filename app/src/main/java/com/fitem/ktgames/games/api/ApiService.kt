package com.fitem.ktgames.games.api

import androidx.collection.ArrayMap
import com.fitem.ktgames.games.api.ApiConstants.MUSIC_GET_BILLCATEGORY
import com.fitem.ktgames.games.api.ApiConstants.MUSIC_GET_CATEGORY_LIST
import com.fitem.ktgames.games.api.ApiConstants.MUSIC_V1_TING
import com.fitem.ktgames.games.model.bean.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by LeiGuangwu on 2019-06-27.
 */
interface ApiService {

    /* 新闻 */
    @GET("list/{explore_id}/{offset}-{limit}.html")
    fun getNewsList(
        @Path("explore_id") explore_id: String,
        @Path("offset") offset: Int,
        @Path("limit") limit: Int
    ): Observable<News>

    @GET("{news_id}/full.html")
    fun getNewsDetail(
        @Path("news_id") news_id: String
    ): Observable<ArrayMap<String, GNewsDetail>>

    /* 美女图片 */
    @GET("{page_size}/{page}")
    fun getGrilsPic(
        @Path("page_size") ps: Int,
        @Path("page") pg: Int
    ): Observable<Girls>

    @GET("list")
    fun getLiveList(
        @Query("live_type") liveType: String,
        @Query("game_type") gameType: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Observable<LiveBase<List<LiveItem>>>

    @GET("detail")
    fun getLiveDetail(
        @Query("live_type") liveType: String,
        @Query("live_id") liveId: String,
        @Query("game_type") gameType: String
    ): Observable<LiveBase<LiveDetail>>

    /* 视频 */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Observable<HomeBean>

    /**
     * 根据 nextPageUrl 请求数据下一页数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>

    @GET("v4/video/related?")
    fun getRelatedData(@Query("id") id: Long): Observable<HomeBean.Issue>

    /* 音乐 */
    /**
     * 获取音乐榜单
     */
    @GET("$MUSIC_V1_TING?method=$MUSIC_GET_BILLCATEGORY")
    fun getBillPlaylist(): Observable<BaiduList>

    /**
     * 获取歌手榜单
     */
    @GET("/toplist/artist")
    fun getTopArtists(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<ArtistsInfo>

    /**
     * 获取电台列表
     */
    @GET("$MUSIC_V1_TING?version=5.6.5.0&method=$MUSIC_GET_CATEGORY_LIST")
    fun getRadioChannels(): Observable<RadioData>

    /**
     * Banner
     */
    @GET("banner")
    fun getBanner(): Observable<BannerResult>

    /**
     * 获取推荐歌单
     */
    @GET("/personalized")
    fun getersonalizedPlaylist(): Observable<PersonalizedInfo>

}