package com.example.ktgames.games.UI.main.fragment

import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.ToastUtils
import com.example.ktgames.R
import com.example.ktgames.common.base.BaseFragment
import com.example.ktgames.games.GirlsContract
import com.example.ktgames.games.UI.grils.adapter.GirlsAdapter
import com.example.ktgames.games.module.bean.Girls
import com.example.ktgames.games.presenter.GirlsPresenter
import kotlinx.android.synthetic.main.fragment_girls.*

/**
 * Created by LeiGuangwu on 2019-07-04.
 */

class GirlsFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, GirlsContract.View {

    private val mPresenter by lazy { GirlsPresenter() }

    private val mAdapter by lazy { GirlsAdapter(R.layout.item_girls, mItemList) }

    private var mItemList: List<Girls.ResultsBean> = ArrayList()

    init {
        mPresenter.attachView(this)
    }

    override fun onRefresh() {
        mPresenter.requestGrilsListPresenter(0)
    }

    companion object {

        fun getInstance(): GirlsFragment {
            val fragment = GirlsFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun initView() {
        mToolbar.setTitle(R.string.girls)
        mSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        mSwipeLayout.setOnRefreshListener(this)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter

    }

    override fun initData() {

    }

    override fun initListener() {
    }

    override fun lazyFetchData() {
        onRefresh()
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_girls
    }

    override fun setGirlsList(list: List<Girls.ResultsBean>) {
        mAdapter.setNewData(list)
    }

    override fun showError(errorMsg: String) {
        ToastUtils.showShort(errorMsg)
        mSwipeLayout.isRefreshing = false
    }

    override fun showLoading() {
        ToastUtils.showShort("网络请求中。。。")
        mSwipeLayout.isRefreshing = false
    }

    override fun dismissLoading() {
        ToastUtils.showShort("请求结束")
        mSwipeLayout.isRefreshing = false
    }

}