package com.fitem.ktgames.games.contract

import com.fitem.ktgames.games.model.bean.GNews
import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter

/**
 * Created by LeiGuangwu on 2019-10-12.
 */
interface NewsContract {

    interface View : IBaseView {

        fun setNewsData(newsList : List<GNews>)

        fun setMoreData(newsList : List<GNews>)

        fun showError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {

        fun requestNewsDataPresenter()

        fun loadMoreDataPresenter()
    }
}