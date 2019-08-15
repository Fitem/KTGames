package com.fitem.ktgames.games.presenter

import com.fitem.ktgames.games.GirlsContract
import com.fitem.ktgames.games.module.GirlsModel
import com.hazz.kotlinmvp.base.BasePresenter

/**
 * Created by LeiGuangwu on 2019-08-01.
 */
class GirlsPresenter : BasePresenter<GirlsContract.View>(), GirlsContract.Presenter {

    private var nextPage: Int = 0

    private val girlsModel: GirlsModel by lazy {
        GirlsModel()
    }

    override fun requestGirlsFirstPagePresenter() {
        nextPage = 0
        requestGirlsNextPagePresenter()
    }

    override fun requestGirlsNextPagePresenter() {
        mRootView?.showLoading()
        val disposable = girlsModel.requestGrilsList(nextPage)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    setGirlsList(it.results, nextPage == 0)
                    nextPage++
                }
            }, {
                mRootView?.showError(it.toString())

            })
        addSubscription(disposable)
    }

}