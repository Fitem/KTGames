package com.fitem.ktgames.games.ui.main.fragment

import android.app.Activity
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
import com.fitem.ktgames.games.app.AppApplication
import com.fitem.ktgames.games.contract.HomeContract
import com.fitem.ktgames.games.model.bean.HomeBean
import com.fitem.ktgames.games.presenter.HomePresenter
import com.fitem.ktgames.games.ui.video.adapter.VideoAdapter
import com.fitem.ktgames.games.ui.video.adapter.VideoConstant
import kotlinx.android.synthetic.main.fragment_girls.mRecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_home_content.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * 视频
 * Created by LeiGuangwu on 2019-07-04.
 */

class HomeFragment : BaseFragment(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.RequestLoadMoreListener {

    private val mPresenter by lazy { HomePresenter() }

    private val layoutManager by lazy {
        LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    private val mAdapter by lazy { VideoAdapter(mItemList) }

    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    private var mItemList: List<HomeBean.Issue.Item> = ArrayList()

    private var num: Int = 1

    init {
        mPresenter.attachView(this)
    }

    companion object {

        fun getInstance(): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun initView() {
        mSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        mSwipeLayout.setOnRefreshListener(this)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
    }

    override fun lazyFetchDataIfPrepared() {
        super.lazyFetchDataIfPrepared()
        initStatusBar()
    }

    private fun initStatusBar() {
        activity?.let {
            BarUtils.setStatusBarColor(it, ContextCompat.getColor(it, R.color.color_translucent))
            BarUtils.setStatusBarLightMode(it, true)
            BarUtils.addMarginTopEqualStatusBarHeight(mToolbar)
        }
    }

    override fun initListener() {
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    mRlToolbar.setBackgroundColor(
                        ContextCompat.getColor(
                            AppApplication.context,
                            R.color.color_translucent
                        )
                    )
                    mIvSearch.setImageResource(R.drawable.ic_action_search_white)
                    mTvTitle.text = ""
                } else {
                    if (mAdapter.data.size > 1) {
                        mRlToolbar.setBackgroundColor(
                            ContextCompat.getColor(
                                AppApplication.context,
                                R.color.half_alpha_white
                            )
                        )
                        mIvSearch.setImageResource(R.drawable.ic_action_search_black)
                        val itemList = mAdapter.data
                        val item = itemList[currentVisibleItemPosition]
                        if (item.itemType == VideoConstant.VIDEO_TEXT_HEADER) {
                            mTvTitle.text = item.data?.text
                        } else {
                            mTvTitle.text = simpleDateFormat.format(item.data?.date)
                        }
                    }
                }
            }
        })

        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val bean = mAdapter.data[position]
            if (bean.itemType == VideoConstant.VIDEO_CONTENT) {
                mAdapter.goToVideoPlayer(context as Activity, view.iv_cover_feed, bean)
            }
        }
        mIvSearch.setOnClickListener {
            ToastUtils.showShort("即将上线！")
        }
    }

    override fun onLoadMoreRequested() {
        mPresenter.loadMoreData()
    }

    override fun initData() {
    }

    override fun lazyFetchData() {
        mPresenter.requestHomeData(num)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_home

    override fun onRefresh() {
        mPresenter.requestHomeData(num)
    }

    override fun setHomeData(homeBean: HomeBean) {
        val itemList = homeBean.issueList[0].itemList
        val bannerCount = homeBean.issueList[0].count
        if (itemList.size > 0 && itemList.size >= bannerCount) {
            var bannerList = itemList.subList(0, bannerCount).toCollection(ArrayList())
            var videoList = itemList.subList(bannerCount, itemList.size).toCollection(ArrayList())
            setItemType(videoList)
            val bannerBean = HomeBean.Issue.Item("", null, "", VideoConstant.VIDEO_BANNER, bannerList)
            videoList.add(0, bannerBean)
            mAdapter.setNewData(videoList)
        }
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        setItemType(itemList)
        mAdapter.addData(itemList)

        if (itemList.size <= 0) {
            mAdapter.loadMoreEnd()
        } else {
            mAdapter.loadMoreComplete()
        }
    }

    private fun setItemType(itemList: ArrayList<HomeBean.Issue.Item>) {
        itemList.map { item ->
            item.itemType =
                if (VideoConstant.TEXT_HEAD == item.type) VideoConstant.VIDEO_TEXT_HEADER else VideoConstant.VIDEO_CONTENT
        }
    }

    override fun showError(msg: String, errorCode: Int) {
        ToastUtils.showShort(msg)
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