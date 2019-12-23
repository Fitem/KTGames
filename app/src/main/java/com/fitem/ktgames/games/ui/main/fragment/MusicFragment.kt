package com.fitem.ktgames.games.ui.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitem.ktgames.R
import com.fitem.ktgames.common.base.BaseFragment
import com.fitem.ktgames.games.contract.MusicContract
import com.fitem.ktgames.games.model.bean.ArtistsInfo
import com.fitem.ktgames.games.model.bean.BannerBean
import com.fitem.ktgames.games.model.bean.PersonalizedItem
import com.fitem.ktgames.games.model.bean.ResultItem
import com.fitem.ktgames.games.presenter.MusicPresenter
import com.hazz.kotlinmvp.glide.GlideApp
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_music.*
import kotlinx.android.synthetic.main.layout_music_home_router.*


/**
 * 音乐
 * Created by LeiGuangwu on 2019-07-04.
 */

class MusicFragment : BaseFragment(), MusicContract.View {

    private val mPresenter by lazy { MusicPresenter() }

    init {
        mPresenter.attachView(this)
    }

    companion object {

        fun getInstance(): MusicFragment {
            val fragment = MusicFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun lazyFetchData() {
        mPresenter.requestBannerViewPresenter()
        mPresenter.requestTopArtistsPresenter()
        mPresenter.requestPersonalizedPlaylistPresenter()
        mPresenter.requestRadioChannelsPresenter()
    }

    override fun initView() {
        mBGABanner.setAdapter { banner, _, feedImageUrl, position ->
            GlideApp.with(this)
                .load(feedImageUrl)
                .transition(DrawableTransitionOptions().crossFade())
                .placeholder(R.drawable.placeholder_banner)
                .into(banner.getItemImageView(position))
        }
    }

    override fun initData() {
    }

    override fun initListener() {
        mTvRecommendArtists.setOnClickListener {
            ToastUtils.showShort(mTvRecommendArtists.text.toString().trim())
        }
        mTvHotArtists.setOnClickListener{
            ToastUtils.showShort(mTvHotArtists.text.toString().trim())
        }
        mTvRecommendMusic.setOnClickListener{
            ToastUtils.showShort(mTvRecommendMusic.text.toString().trim())
        }
        mTvRadio.setOnClickListener{
            ToastUtils.showShort(mTvRadio.text.toString().trim())
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_music


    override fun setTopArtists(artists: List<ArtistsInfo.List.ArtistInfo>) {
    }

    override fun setRadioChannels(channels: List<ResultItem>) {
    }

    @SuppressLint("CheckResult")
    override fun setBannerView(banners: List<BannerBean>) {
        if (!banners.isNullOrEmpty()) {
            val bannerFeedList = ArrayList<String>()
            val bannerTitleList = ArrayList<String>()
            //取出banner 显示的 img 和 Title
            Observable.fromIterable(banners)
                .subscribe { list ->
                    bannerFeedList.add(list.picUrl)
                    bannerTitleList.add("")
                }

            mBGABanner.setAutoPlayAble(banners.size > 1)
            mBGABanner.setData(bannerFeedList, bannerTitleList)
        }
    }

    override fun setPersonalizedPlaylist(playList: List<PersonalizedItem>) {
    }

    override fun showError(errorMsg: String) {
        ToastUtils.showShort(errorMsg)
        mSwipeLayout.isRefreshing = false
    }

    override fun showLoading() {
        ToastUtils.showShort("网络请求中。。。")
    }

    override fun dismissLoading() {
        ToastUtils.showShort("请求结束")
        mSwipeLayout.isRefreshing = false
    }
}