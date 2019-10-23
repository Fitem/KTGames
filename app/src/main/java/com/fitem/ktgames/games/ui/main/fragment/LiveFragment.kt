package com.fitem.ktgames.games.ui.main.fragment

import android.os.Bundle
import com.fitem.ktgames.R
import com.fitem.ktgames.common.base.BaseFragment

/**
 * 直播
 * Created by LeiGuangwu on 2019-07-04.
 */

class LiveFragment : BaseFragment() {

    companion object {

        fun getInstance(): LiveFragment {
            val fragment = LiveFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
    }

    override fun initListener() {
    }

    override fun initData() {
    }

    override fun getLayoutResource(): Int = R.layout.fragment_live

}