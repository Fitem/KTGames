package com.fitem.ktgames.games.ui.grils.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.MenuItem
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitem.ktgames.R
import com.fitem.ktgames.common.base.BaseActivity
import com.fitem.ktgames.common.utils.SystemUiVisibilityUtil
import com.fitem.ktgames.common.widget.PullBackLayout
import com.fitem.ktgames.games.ui.main.Constants
import com.hazz.kotlinmvp.glide.GlideApp
import kotlinx.android.synthetic.main.activity_girls.*

/**
 * Created by LeiGuangwu on 2019-08-14.
 */
class GirlsActivity : BaseActivity(), PullBackLayout.Callback {

    private var mIsToolBarHidden = false
    private var mIsStatusBarHidden = false
    private var mBackground: ColorDrawable = ColorDrawable(Color.BLACK)
    private var url: String? = null

    override fun onPullStart() {
        toolBarFadeOut()
        mIsStatusBarHidden = true
        hideOrShowStatusBar()
    }

    override fun onPull(progress: Float) {
        val newProgress = Math.min(1f, progress * 3f)
        mBackground.alpha = (0xff/*255*/ * (1f - newProgress)).toInt()
    }

    override fun onPullCancel() {
        toolBarFadeIn()
    }

    override fun onPullComplete() {
        supportFinishAfterTransition()
    }

    override fun getLayoutId(): Int = R.layout.activity_girls;

    override fun parseArgument() {
        url = intent.getStringExtra(Constants.GIRLS_URL)
    }

    override fun initView() {
//        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.black_gray))
        mToolbar.setTitle(R.string.girls)
        mToolbar.setBackgroundColor(Color.BLACK)
        findViewById<ViewGroup>(android.R.id.content).getChildAt(0).setBackgroundDrawable(mBackground)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    override fun initListener() {
        mPullBackLayout.setCallback(this)
    }

    override fun initData() {
        GlideApp.with(this).load(url)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .error(R.drawable.ic_news_prepare)
            .fitCenter()
            .transition(DrawableTransitionOptions().crossFade())
            .into(mIvPhoto)
    }

    protected fun hideOrShowToolbar() {
        mToolbar.animate()
            .alpha(if (mIsToolBarHidden) 1.0f else 0.0f)
            .setInterpolator(DecelerateInterpolator(2f))
            .start()
        mIsToolBarHidden = !mIsToolBarHidden
    }

    private fun hideOrShowStatusBar() {
        if (mIsStatusBarHidden) {
            SystemUiVisibilityUtil.enter(this)
        } else {
            SystemUiVisibilityUtil.exit(this)
        }
        mIsStatusBarHidden = !mIsStatusBarHidden
    }

    private fun toolBarFadeIn() {
        mIsToolBarHidden = true
        hideOrShowToolbar()
    }

    private fun toolBarFadeOut() {
        mIsToolBarHidden = false
        hideOrShowToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}