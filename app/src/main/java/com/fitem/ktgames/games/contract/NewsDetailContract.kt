package com.fitem.ktgames.games.contract

import com.fitem.ktgames.games.model.bean.GNewsDetail
import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter

/**
 * Created by LeiGuangwu on 2019-10-12.
 */
interface NewsDetailContract {

    interface View : IBaseView {

        fun setNewsDetail(detail: GNewsDetail)

        fun showError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {

        fun requestNewsDetailDataPresenter(newsId: String)
    }
}