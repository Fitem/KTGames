package com.fitem.ktgames.games.model

import com.fitem.ktgames.games.api.HostType
import com.fitem.ktgames.games.model.bean.HomeBean
import com.fitem.ktgames.games.net.RetrofitManager
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by LeiGuangwu on 2019-08-01.
 */
class HomeModel {

    /**
     * 获取首页 Banner 数据
     */
    fun requestHomeData(num:Int): Observable<HomeBean> {
        return RetrofitManager.getApiService(HostType.VIDEO_HOST).getFirstHomeData(num)
            .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多
     */
    fun loadMoreData(url:String): Observable<HomeBean> {

        return RetrofitManager.getApiService(HostType.VIDEO_HOST).getMoreHomeData(url)
            .compose(SchedulerUtils.ioToMain())
    }
}