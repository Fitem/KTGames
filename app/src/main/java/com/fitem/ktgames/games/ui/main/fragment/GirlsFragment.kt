package com.fitem.ktgames.games.ui.main.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fitem.ktgames.R
import com.fitem.ktgames.common.base.BaseFragment
import com.fitem.ktgames.games.api.HostType
import com.fitem.ktgames.games.app.AppApplication
import com.fitem.ktgames.games.contract.GirlsContract
import com.fitem.ktgames.games.model.bean.Girls
import com.fitem.ktgames.games.presenter.GirlsPresenter
import com.fitem.ktgames.games.ui.grils.activity.GirlsActivity
import com.fitem.ktgames.games.ui.grils.adapter.GirlsAdapter
import com.fitem.ktgames.games.ui.main.Constants
import com.hazz.kotlinmvp.glide.GlideApp
import kotlinx.android.synthetic.main.fragment_girls.*


/**
 * Created by LeiGuangwu on 2019-07-04.
 */

class GirlsFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, GirlsContract.View,
    BaseQuickAdapter.RequestLoadMoreListener {

    private val mPresenter by lazy { GirlsPresenter() }

    private val mAdapter by lazy { GirlsAdapter(R.layout.item_girls, mItemList) }

    private var mItemList: List<Girls.ResultsBean> = ArrayList()

    init {
        mPresenter.attachView(this)
    }

    override fun onRefresh() {
        mPresenter.requestGirlsFirstPagePresenter()
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
        mTitle.setText(R.string.girls)
        mSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        mSwipeLayout.setOnRefreshListener(this)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
    }

    override fun lazyFetchDataIfPrepared() {
        super.lazyFetchDataIfPrepared()
        activity?.let {
            BarUtils.setStatusBarColor(it, ContextCompat.getColor(it, R.color.white))
            BarUtils.setStatusBarLightMode(it, true)
            BarUtils.addMarginTopEqualStatusBarHeight(mAppBarLayout)
        }
    }

    override fun initData() {

    }

    override fun initListener() {
        mAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                val picView = view.findViewById<View>(R.id.iv_pic)
                val bean = mAdapter.data[position]
                transition(bean, picView)
            }
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    GlideApp.with(AppApplication.context).pauseRequests()
                } else {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //-1代表顶部,返回true表示没到顶,还可以滑
                        //1代表底部,返回true表示没到底部,还可以滑
                        val isScrollTop = !recyclerView.canScrollVertically(-1)
                        if(!mSwipeLayout.isRefreshing && isScrollTop) {
                            mAdapter.notifyDataSetChanged()
                        }
                    }
                    GlideApp.with(AppApplication.context).resumeRequests()
                }
            }
        })
    }

    override fun onLoadMoreRequested() {
        mPresenter.requestGirlsNextPagePresenter()
    }

    private fun transition(bean: Girls.ResultsBean, view: View) {
        val options: ActivityOptionsCompat
        val intent = Intent(activity, GirlsActivity::class.java)
        intent.putExtra(Constants.GIRLS_URL, bean.url)
        activity?.let {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                options = ActivityOptionsCompat     //让新的Activity从一个小的范围扩大到全屏
                    .makeScaleUpAnimation(view, view.width / 2, view.height / 2, 0, 0)
            } else {
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    it, view, getString(R.string.transition_grils)
                )
            }
            ActivityCompat.startActivity(it, intent, options.toBundle())
        }
    }


    override fun lazyFetchData() {
        onRefresh()
    }

    override fun getLayoutResource(): Int = R.layout.fragment_girls


    override fun setGirlsList(list: List<Girls.ResultsBean>, isFirst: Boolean) {
        if (isFirst) {
            mAdapter.setNewData(list)
        } else {
            mAdapter.addData(list)
        }

        if (list.isNullOrEmpty() || list.size < HostType.PAGE_SIZE) {
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