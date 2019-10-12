package com.fitem.ktgames.games.model

import com.fitem.ktgames.games.api.HostType
import com.fitem.ktgames.games.model.bean.GNewsDetail
import com.fitem.ktgames.games.net.RetrofitManager
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by LeiGuangwu on 2019-10-12.
 */
class NewsDetailModel {

    fun requestNewsDetailData(newsId: String): Observable<GNewsDetail> {
        return RetrofitManager.getApiService(HostType.NEWS_HOST).getNewsDetail(newsId)
            .map { it[newsId] }
            .compose(SchedulerUtils.ioToMain())
    }
}