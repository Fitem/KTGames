package com.example.ktgames.games.UI.main.fragment

import android.os.Bundle
import com.example.ktgames.R
import com.example.ktgames.common.base.BaseFragment

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

    override fun getLayoutResource(): Int {
     return R.layout.fragment_mine
    }

}