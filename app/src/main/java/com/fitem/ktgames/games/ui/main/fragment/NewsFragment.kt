package com.fitem.ktgames.games.ui.main.fragment

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fitem.ktgames.R
import com.fitem.ktgames.common.base.BaseFragment
import com.fitem.ktgames.common.widget.DividerItemDecoration
import com.fitem.ktgames.games.api.HostType
import com.fitem.ktgames.games.contract.NewsContract
import com.fitem.ktgames.games.model.bean.GNews
import com.fitem.ktgames.games.presenter.NewsPresenter
import com.fitem.ktgames.games.ui.news.activity.NewsDetailActivity
import com.fitem.ktgames.games.ui.news.adapter.NewsListAdapter
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * Created by LeiGuangwu on 2019-07-04.
 */

class NewsFragment : BaseFragment(), NewsContract.View, SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.RequestLoadMoreListener {

    private val mPresnter by lazy { NewsPresenter() }

    private val mAdapter by lazy { NewsListAdapter(R.layout.item_news, arrayListOf()) }

    init {
        mPresnter.attachView(this)
    }

    companion object {

        fun getInstance(): NewsFragment {
            val fragment = NewsFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun initView() {
        mTitle.setText(R.string.news)
        mSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        mSwipeLayout.setOnRefreshListener(this)
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context!!,
                LinearLayoutManager.VERTICAL
            )
        )
        mRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mRecyclerView.adapter = mAdapter
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT)
    }

    override fun lazyFetchDataIfPrepared() {
        super.lazyFetchDataIfPrepared()
        activity?.let {
            BarUtils.setStatusBarColor(it, ContextCompat.getColor(it, R.color.white))
            BarUtils.setStatusBarLightMode(it, true)
            BarUtils.addMarginTopEqualStatusBarHeight(mCoordinatorLayout)
        }
    }

    override fun initListener() {
        mAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { _, _, position ->
                val dto = mAdapter.data[position]
                NewsDetailActivity.actionActivity(context!!, dto.docid, dto.title)
            }
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
    }

    override fun onLoadMoreRequested() {
        mPresnter.loadMoreDataPresenter()
    }

    override fun initData() {
    }

    override fun lazyFetchData() {
        onRefresh()
    }

    override fun getLayoutResource(): Int = R.layout.fragment_news

    override fun onRefresh() {
        mPresnter.requestNewsDataPresenter()
    }

    override fun setNewsData(newsList: List<GNews>) {
        mAdapter.setNewData(newsList)
        if (newsList.isNullOrEmpty() || newsList.size < HostType.PAGE_SIZE) {
            mAdapter.loadMoreEnd()
        } else {
            mAdapter.loadMoreComplete()
        }
    }

    override fun setMoreData(newsList: List<GNews>) {
        mAdapter.addData(newsList)
        if (newsList.isNullOrEmpty() || newsList.size < HostType.PAGE_SIZE) {
            mAdapter.loadMoreEnd()
        } else {
            mAdapter.loadMoreComplete()
        }
    }

    override fun showError(errorMsg: String) {
        ToastUtils.showShort(errorMsg)
        mSwipeLayout.isRefreshing = false
        mAdapter.loadMoreFail()
    }

    override fun showLoading() {
        ToastUtils.showShort("网络请求中。。。")
    }

    override fun dismissLoading() {
        ToastUtils.showShort("请求结束")
        mSwipeLayout.isRefreshing = false
        mAdapter.loadMoreComplete()
    }

}
