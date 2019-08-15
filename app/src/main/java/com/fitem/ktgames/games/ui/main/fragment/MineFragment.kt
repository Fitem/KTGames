package com.fitem.ktgames.games.ui.main.fragment

import android.os.Bundle
import com.fitem.ktgames.R
import com.fitem.ktgames.common.base.BaseFragment

/**
 * Created by LeiGuangwu on 2019-07-04.
 */

class MineFragment : BaseFragment() {
    companion object {

        fun getInstance(): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun getLayoutResource(): Int = R.layout.fragment_mine

}