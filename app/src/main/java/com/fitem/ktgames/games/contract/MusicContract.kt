package com.fitem.ktgames.games.contract

import com.fitem.ktgames.games.model.bean.ArtistsInfo
import com.fitem.ktgames.games.model.bean.BannerBean
import com.fitem.ktgames.games.model.bean.PersonalizedItem
import com.fitem.ktgames.games.model.bean.ResultItem
import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter

/**
 * Created by LeiGuangwu on 2019-08-01.
 */
interface MusicContract {

    interface View : IBaseView {
        /* 设置歌手 */
        fun setTopArtists(artists: List<ArtistsInfo.List.ArtistInfo>)

        fun setRadioChannels(channels: List<ResultItem>)

        fun setBannerView(banners: List<BannerBean>)

        fun setPersonalizedPlaylist(playList: List<PersonalizedItem>)

        fun showError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {

        fun requestTopArtistsPresenter()

        fun requestRadioChannelsPresenter()

        fun requestBannerViewPresenter()

        fun requestPersonalizedPlaylistPresenter()
    }
}