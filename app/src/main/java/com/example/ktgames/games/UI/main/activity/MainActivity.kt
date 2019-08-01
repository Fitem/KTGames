package com.example.ktgames.games.UI.main.activity

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.ktgames.R
import com.example.ktgames.common.base.BaseActivity
import com.example.ktgames.games.UI.main.Constants
import com.example.ktgames.games.UI.main.fragment.GirlsFragment
import com.example.ktgames.games.UI.main.fragment.LiveFragment
import com.example.ktgames.games.UI.main.fragment.MineFragment
import com.example.ktgames.games.UI.main.fragment.NewsFragment
import com.example.ktgames.games.module.bean.TabEntity
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.properties.Delegates

class MainActivity : BaseActivity() {

    private var mTitls: Array<String> by Delegates.notNull()
    private var mTabEntities = ArrayList<CustomTabEntity>()
    private val mIconUnselectIds =
        arrayOf(R.mipmap.ic_news_normal, R.mipmap.ic_score_normal, R.mipmap.ic_group_normal, R.mipmap.ic_mine_normal)
    private val mIconSelectIds = arrayOf(
        R.mipmap.ic_news_selected,
        R.mipmap.ic_score_selected,
        R.mipmap.ic_group_selected,
        R.mipmap.ic_mine_selected
    )

    private var mNewFragment: NewsFragment? = null
    private var mGirlsFragment: GirlsFragment? = null
    private var mLiveFragment: LiveFragment? = null
    private var mMineFragment: MineFragment? = null

    //默认为0
    private var mIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            mIndex = it.getInt(Constants.CURRENT_TAB_INDEX, 0)
        }
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        initTab()
    }

    override fun initListener() {
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabReselect(position: Int) {

            }

            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }
        })
    }

    override fun initData() {
        tab_layout.currentTab = mIndex
        switchFragment(mIndex)
    }

    private fun initTab() {
        initTabData()
        tab_layout.setTabData(mTabEntities)
    }

    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFrgamnts(transaction)
        when (position) {
            0 //新闻
            -> mNewFragment?.let {
                transaction.show(it)
            } ?: NewsFragment.getInstance().let {
                mNewFragment = it
                transaction.add(R.id.fl_container, it, "news")
            }
            1 //直播
            -> mLiveFragment?.let {
                transaction.show(it)
            } ?: LiveFragment.getInstance().let {
                mLiveFragment = it
                transaction.add(R.id.fl_container, it, "live")
            }
            2 //美女
            -> mGirlsFragment?.let {
                transaction.show(it)
            } ?: GirlsFragment.getInstance().let {
                mGirlsFragment = it
                transaction.add(R.id.fl_container, it, "grils")
            }
            3 //我的
            -> mMineFragment?.let {
                transaction.show(it)
            } ?: MineFragment.getInstance().let {
                transaction.add(R.id.fl_container, it, "mine")
            }
        }
        mIndex = position
        tab_layout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }

    private fun hideFrgamnts(transaction: FragmentTransaction) {
        mNewFragment?.let { transaction.hide(it) }
        mLiveFragment?.let { transaction.hide(it) }
        mGirlsFragment?.let { transaction.hide(it) }
        mMineFragment?.let { transaction.hide(it) }
    }

    private fun initTabData() {
        mTitls = arrayOf(
            getString(R.string.news),
            getString(R.string.live),
            getString(R.string.girls),
            getString(R.string.mine)
        )
        (0 until mTitls.size)
            .mapTo(mTabEntities) {
                TabEntity(mTitls[it], mIconSelectIds[it], mIconUnselectIds[it])
            }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        tab_layout?.let {
            outState?.let { it.putInt(Constants.CURRENT_TAB_INDEX, mIndex) }
        }
    }
}
