package com.fitem.ktgames.games.presenter

import android.annotation.SuppressLint
import com.blankj.utilcode.util.LogUtils
import com.fitem.ktgames.games.app.AppConstants
import com.fitem.ktgames.games.contract.NewsDetailContract
import com.fitem.ktgames.games.model.NewsDetailModel
import com.fitem.ktgames.games.model.bean.GNewsDetail
import com.hazz.kotlinmvp.base.BasePresenter

/**
 * Created by LeiGuangwu on 2019-08-01.
 */
class NewsDetailPresenter : BasePresenter<NewsDetailContract.View>(), NewsDetailContract.Presenter {

    private val newsModel: NewsDetailModel by lazy {
        NewsDetailModel()
    }

    @SuppressLint("CheckResult")
    override fun requestNewsDetailDataPresenter(newsId: String) {
        val disposable = newsModel.requestNewsDetailData(newsId)
            .doOnNext { handleRichTextWithImg(it) }
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    setNewsDetail(it)
                }
            }, {
                mRootView?.showError(it.toString())
            })
        addSubscription(disposable)
    }

    /**
     * 处理富文本包含图片的情况
     *
     * @param data 原始数据
     */
    private fun handleRichTextWithImg(data: GNewsDetail) {
        val list = data.img
        if (!list.isNullOrEmpty()) {
            var body = data.body
            for (bean in list) {
                val ref = bean.ref
                val src = bean.src
                val img = AppConstants.HTML_IMG_TEMPALE.replace("http", src)
                body = body.replace(ref.toRegex(), img)
            }
            data.body = body
            LogUtils.d(body)
        }
    }

}