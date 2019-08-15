package com.fitem.ktgames.games

import com.fitem.ktgames.games.module.bean.Girls
import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter

/**
 * Created by LeiGuangwu on 2019-08-01.
 */
interface GirlsContract {

    interface View : IBaseView {
        /* 设置图片 */
        fun setGirlsList(list: List<Girls.ResultsBean>, isFirst: Boolean)

        fun showError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {
        /* 请求相关的图片 */
        fun requestGirlsFirstPagePresenter()

        fun requestGirlsNextPagePresenter()
    }
}