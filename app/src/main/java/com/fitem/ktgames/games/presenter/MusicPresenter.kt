package com.fitem.ktgames.games.presenter

import com.fitem.ktgames.games.contract.MusicContract
import com.fitem.ktgames.games.model.MusicModel
import com.fitem.ktgames.games.net.exception.ExceptionHandle
import com.hazz.kotlinmvp.base.BasePresenter

/**
 * Created by LeiGuangwu on 2019-08-01.
 */
class MusicPresenter : BasePresenter<MusicContract.View>(), MusicContract.Presenter {

    private val mModel by lazy { MusicModel() }

    override fun requestTopArtistsPresenter() {
        val disposable = mModel.requestTopArtists()
            .subscribe({ artists ->
                mRootView?.apply {
                    dismissLoading()
                    setTopArtists(artists.list.artists)
                }
            }, { t ->
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(t))
                }
            })

        addSubscription(disposable)
    }

    override fun requestRadioChannelsPresenter() {
        val disposable = mModel.requestRadioChannels().subscribe({ radioData ->
            mRootView?.apply {
                dismissLoading()
                setRadioChannels(radioData.result)
            }
        }, { t ->
            mRootView?.apply {
                dismissLoading()
                showError(ExceptionHandle.handleException(t))
            }
        })
        addSubscription(disposable)
    }

    override fun requestBannerViewPresenter() {
        val disposable = mModel.requestBannerView().subscribe({ result ->
            mRootView?.apply {
                dismissLoading()
                setBannerView(result.banners)
            }
        }, { t ->
            mRootView?.apply {
                dismissLoading()
                showError(ExceptionHandle.handleException(t))
            }
        })
        addSubscription(disposable)
    }

    override fun requestPersonalizedPlaylistPresenter() {
        val disposable = mModel.requestPersonalizedPlaylist().subscribe({ info ->
            mRootView?.apply {
                dismissLoading()
                setPersonalizedPlaylist(info.result)
            }
        }, { t ->
            mRootView?.apply {
                dismissLoading()
                showError(ExceptionHandle.handleException(t))
            }
        })
        addSubscription(disposable)
    }

}