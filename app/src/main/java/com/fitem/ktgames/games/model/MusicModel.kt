package com.fitem.ktgames.games.model

import com.fitem.ktgames.games.api.HostType
import com.fitem.ktgames.games.model.bean.ArtistsInfo
import com.fitem.ktgames.games.model.bean.BannerResult
import com.fitem.ktgames.games.model.bean.PersonalizedInfo
import com.fitem.ktgames.games.model.bean.RadioData
import com.fitem.ktgames.games.net.RetrofitManager
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by LeiGuangwu on 2019-08-01.
 */
class MusicModel {

    fun requestTopArtists(): Observable<ArtistsInfo> {
        return RetrofitManager.getApiService(HostType.NETEASE_MUSIC_HOST)
            .getTopArtists(HostType.PAGE_SIZE, 0)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestRadioChannels(): Observable<RadioData> {
        return RetrofitManager.getApiService(HostType.BASE_BAIDU_MUSIC_URL).getRadioChannels()
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestBannerView(): Observable<BannerResult> {
        return RetrofitManager.getApiService(HostType.NETEASE_MUSIC_HOST).getBanner()
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestPersonalizedPlaylist(): Observable<PersonalizedInfo> {
        return RetrofitManager.getApiService(HostType.NETEASE_MUSIC_HOST).getersonalizedPlaylist()
            .compose(SchedulerUtils.ioToMain())
    }
}