package com.fitem.ktgames.games.ui.news.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.fitem.ktgames.R
import com.fitem.ktgames.common.base.BaseActivity
import com.fitem.ktgames.games.contract.NewsDetailContract
import com.fitem.ktgames.games.model.bean.GNewsDetail
import com.fitem.ktgames.games.presenter.NewsDetailPresenter
import com.fitem.ktgames.games.ui.main.Constants
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_news_detail.*
import org.jetbrains.annotations.NotNull

/**
 * Created by LeiGuangwu on 2019-10-14.
 */
class NewsDetailActivity : BaseActivity(), NewsDetailContract.View {

    private val mPresenter by lazy { NewsDetailPresenter() }
    private var mNewsId = ""
    private var mTitle = ""

    init {
        mPresenter.attachView(this)
    }

    companion object {
        fun actionActivity(@NotNull context: Context, @NotNull newsId: String, @NotNull title: String) {
            val bundle = Bundle()
            bundle.putString(Constants.NEWS_ID, newsId)
            bundle.putString(Constants.TITLE, title)
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_news_detail
    }

    override fun parseArgument() {
        val bundle = intent.extras
        bundle?.let {
            mNewsId = it.getString(Constants.NEWS_ID, "")
            mTitle = it.getString(Constants.TITLE, "")
        }
    }

    @SuppressLint("RestrictedApi")
    override fun initView() {
        initStatusBar()
        mTvTitle.text = mTitle
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initStatusBar() {
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white))
        BarUtils.setStatusBarLightMode(this, true)
        BarUtils.addMarginTopEqualStatusBarHeight(mAppBarLayout)
    }

    override fun initListener() {

    }

    override fun initData() {
        mPresenter.requestNewsDetailDataPresenter(mNewsId)
    }

    override fun setNewsDetail(detail: GNewsDetail) {
        RichText.initCacheDir(this)
        RichText.from(detail.body).into(mTvContent)
    }

    override fun showError(msg: String) {
        ToastUtils.showShort(msg)
    }

    override fun showLoading() {
        ToastUtils.showShort("网络请求中。。。")
    }

    override fun dismissLoading() {
        ToastUtils.showShort("请求结束")
    }

    /**
     * 防暴力点击
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
