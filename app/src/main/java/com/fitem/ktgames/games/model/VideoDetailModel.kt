package com.fitem.ktgames.games.model

import com.fitem.ktgames.games.api.HostType
import com.fitem.ktgames.games.model.bean.HomeBean
import com.fitem.ktgames.games.net.RetrofitManager
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by LeiGuangwu on 2019-08-01.
 * desc:
 */
class VideoDetailModel {

    fun requestRelatedData(id:Long):Observable<HomeBean.Issue>{

        return RetrofitManager.getApiService(HostType.VIDEO_HOST).getRelatedData(id)
                .compose(SchedulerUtils.ioToMain())
    }

}