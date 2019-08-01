package com.example.ktgames.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by LeiGuangwu on 2019-06-28.
 */

abstract class BaseFragment : Fragment() {

    private var isViewPrepared = false
    private var hasFetchData = false
    private var isHideShow = true
    private var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepared = true
        lazyFetchDataIfPrepared()
        initView()
        initListener()
        initData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isHideShow = !hidden
        lazyFetchDataIfPrepared()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // view被销毁后，将可以重新触发数据懒加载，因为在viewpager下，fragment不会再次新建并走onCreate的生命周期流程，将从onCreateView开始
        hasFetchData = false
        isViewPrepared = false
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared()
        }
    }

    private fun lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (isHideShow && userVisibleHint && !hasFetchData && isViewPrepared) {
            hasFetchData = true
            lazyFetchData()
        }
    }

    /**
     * 获取布局文件
     */
    abstract fun getLayoutResource(): Int

    /**
     * 初始化view
     */
    abstract fun initView()

    /**
     * 初始化listener
     */
    abstract fun initListener()

    abstract fun initData()

    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    open fun lazyFetchData() {

    }
}