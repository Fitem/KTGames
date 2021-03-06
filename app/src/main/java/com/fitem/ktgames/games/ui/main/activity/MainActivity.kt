package com.fitem.ktgames.games.ui.main.activity

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.fitem.ktgames.R
import com.fitem.ktgames.common.base.BaseActivity
import com.fitem.ktgames.games.model.bean.TabEntity
import com.fitem.ktgames.games.ui.main.Constants
import com.fitem.ktgames.games.ui.main.fragment.GirlsFragment
import com.fitem.ktgames.games.ui.main.fragment.HomeFragment
import com.fitem.ktgames.games.ui.main.fragment.MusicFragment
import com.fitem.ktgames.games.ui.main.fragment.NewsFragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.properties.Delegates

class MainActivity : BaseActivity() {

    private var mTitls: Array<String> by Delegates.notNull()
    private var mTabEntities = ArrayList<CustomTabEntity>()
    private val mIconUnselectIds = arrayOf(
        R.drawable.ic_home_normal,
        R.drawable.ic_hot_normal,
        R.drawable.ic_mine_normal,
        R.drawable.ic_discovery_normal
    )
    private val mIconSelectIds = arrayOf(
        R.drawable.ic_home_selected,
        R.drawable.ic_hot_selected,
        R.drawable.ic_mine_selected,
        R.drawable.ic_discovery_selected
    )

    private var mHomeFragment: HomeFragment? = null
    private var mGirlsFragment: GirlsFragment? = null
    private var mNewsFragment: NewsFragment? = null
    private var mMusicFragment: MusicFragment? = null

    //默认为0
    private var mIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            mIndex = it.getInt(Constants.CURRENT_TAB_INDEX, 0)
        }
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int = R.layout.activity_main

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
            0 //视频
            -> mHomeFragment?.let {
                transaction.show(it)
            } ?: HomeFragment.getInstance().let {
                mHomeFragment = it
                transaction.add(R.id.fl_container, it, "video")
            }
            1 //新闻
            -> mNewsFragment?.let {
                transaction.show(it)
            } ?: NewsFragment.getInstance().let {
                mNewsFragment = it
                transaction.add(R.id.fl_container, it, "news")
            }
            2 //美女
            -> mGirlsFragment?.let {
                transaction.show(it)
            } ?: GirlsFragment.getInstance().let {
                mGirlsFragment = it
                transaction.add(R.id.fl_container, it, "grils")
            }
            3 //音乐
            -> mMusicFragment?.let {
                transaction.show(it)
            } ?: MusicFragment.getInstance().let {
                mMusicFragment = it
                transaction.add(R.id.fl_container, it, "music")
            }
        }
        mIndex = position
        tab_layout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }

    private fun hideFrgamnts(transaction: FragmentTransaction) {
        mHomeFragment?.let {
            transaction.hide(it)
        }
        mNewsFragment?.let {
            transaction.hide(it)
        }
        mGirlsFragment?.let {
            transaction.hide(it)
        }
        mMusicFragment?.let {
            transaction.hide(it)
        }
    }

    private fun initTabData() {
        mTitls = arrayOf(
            getString(R.string.video),
            getString(R.string.news),
            getString(R.string.girls),
            getString(R.string.music)
        )
        mTitls.indices.mapTo(mTabEntities) {
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
