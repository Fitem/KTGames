package com.fitem.ktgames.games.presenter

import com.fitem.ktgames.games.api.HostType
import com.fitem.ktgames.games.contract.NewsContract
import com.fitem.ktgames.games.model.NewsModel
import com.hazz.kotlinmvp.base.BasePresenter

/**
 * Created by LeiGuangwu on 2019-08-01.
 */
class NewsPresenter : BasePresenter<NewsContract.View>(), NewsContract.Presenter {

    private var nextPage: Int = 0
    private val newsModel: NewsModel by lazy {
        NewsModel()
    }

    override fun requestNewsDataPresenter() {
        nextPage = 0
        loadMoreDataPresenter()
    }

    override fun loadMoreDataPresenter() {
        mRootView?.showLoading()
        val disposable = newsModel.requestNewsData(nextPage)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    if (nextPage == 0) {
                        setNewsData(it.gNewsList)
                    } else {
                        setMoreData(it.gNewsList)
                    }
                    nextPage += HostType.PAGE_SIZE
                }
            }, {
                mRootView?.showError(it.toString())

            })
        addSubscription(disposable)
    }

}