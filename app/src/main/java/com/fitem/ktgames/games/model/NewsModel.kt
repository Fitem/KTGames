package com.fitem.ktgames.games.model

import com.fitem.ktgames.games.api.ApiConstants
import com.fitem.ktgames.games.api.HostType
import com.fitem.ktgames.games.model.bean.News
import com.fitem.ktgames.games.net.RetrofitManager
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by LeiGuangwu on 2019-10-12.
 */
class NewsModel {

    fun requestNewsData(pg: Int): Observable<News> {
        return RetrofitManager.getApiService(HostType.NEWS_HOST)
            .getNewsList(ApiConstants.NEWS_GAMES_TYPE, pg, HostType.PAGE_SIZE)
            .compose(SchedulerUtils.ioToMain())
    }

}