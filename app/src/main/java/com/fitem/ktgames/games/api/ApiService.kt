package com.fitem.ktgames.games.api

import androidx.collection.ArrayMap
import com.fitem.ktgames.games.module.bean.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by LeiGuangwu on 2019-06-27.
 */
interface ApiService {

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
}