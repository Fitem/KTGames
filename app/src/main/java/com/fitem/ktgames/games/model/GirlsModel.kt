package com.fitem.ktgames.games.model

import com.fitem.ktgames.games.api.HostType
import com.fitem.ktgames.games.model.bean.Girls
import com.fitem.ktgames.games.net.RetrofitManager
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by LeiGuangwu on 2019-08-01.
 */
class GirlsModel {

    fun requestGrilsList(pg: Int): Observable<Girls> {

        return RetrofitManager.getApiService(HostType.GRILS_HOST).getGrilsPic(HostType.PAGE_SIZE, pg)
            .compose(SchedulerUtils.ioToMain())
    }
}