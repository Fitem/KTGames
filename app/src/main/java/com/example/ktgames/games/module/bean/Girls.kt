package com.example.ktgames.games.module.bean

import java.io.Serializable

/**
 * Created by Fitem on 2018/3/21.
 */

data class Girls(
    val isError: Boolean,
    val results: List<ResultsBean>
) : Serializable {

    data class ResultsBean(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val isUsed: Boolean,
        val who: String
    ) : Serializable
}
