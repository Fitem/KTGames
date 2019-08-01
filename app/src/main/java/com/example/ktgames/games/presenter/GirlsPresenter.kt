package com.example.ktgames.games.presenter

import com.example.ktgames.games.GirlsContract
import com.example.ktgames.games.module.GirlsModel
import com.hazz.kotlinmvp.base.BasePresenter

/**
 * Created by LeiGuangwu on 2019-08-01.
 */
class GirlsPresenter : BasePresenter<GirlsContract.View>(), GirlsContract.Presenter {

    private val girlsModel: GirlsModel by lazy {
        GirlsModel()
    }

    private var nextPage: Int? = null

    override fun requestGrilsListPresenter(pg: Int) {
        mRootView?.showLoading()
        val disposable = girlsModel.requestGrilsList(pg)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    nextPage = pg + 1
                    setGirlsList(it.results)
                }
            }, {
                mRootView?.apply {
                    showError(it.toString())
                }
            })
        addSubscription(disposable)
    }

}