package com.fitem.ktgames.games.ui.grils.activity

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.fitem.ktgames.R
import com.fitem.ktgames.common.base.BaseActivity
import com.fitem.ktgames.common.utils.SystemUiVisibilityUtil
import com.fitem.ktgames.common.widget.PullBackLayout
import com.fitem.ktgames.games.ui.main.Constants
import com.hazz.kotlinmvp.glide.GlideApp
import kotlinx.android.synthetic.main.activity_girls.*
import java.io.File
import java.io.FileNotFoundException


/**
 * Created by LeiGuangwu on 2019-08-14.
 */
class GirlsActivity : BaseActivity(), PullBackLayout.Callback {

    private var mIsToolBarHidden = false
    private var mIsStatusBarHidden = false
    private var mBackground: ColorDrawable? = null
    private var url: String? = null
    private var fileTypeStr: String? = null

    override fun onPullStart() {
        toolBarFadeOut()
        mIsStatusBarHidden = true
        hideOrShowStatusBar()
    }

    override fun onPull(progress: Float) {
        val newProgress = Math.min(1f, progress * 3f)
        mBackground?.alpha = (0xff/*255*/ * (1f - newProgress)).toInt()
    }

    override fun onPullCancel() {
        toolBarFadeIn()
    }

    override fun onPullComplete() {
        supportFinishAfterTransition()
    }

    override fun getLayoutId(): Int = R.layout.activity_girls

    override fun parseArgument() {
        url = intent.getStringExtra(Constants.GIRLS_URL)
        mBackground = ColorDrawable(getColorPrimary())
        fileTypeStr = url?.let {
            val list = it.split(".")
            "." + list[list.size - 1]
        }
    }

    override fun initView() {
//        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.black_gray))
        mToolbar.setTitle(R.string.girls)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_girls, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home
            -> onBackPressed()
            R.id.save
            -> url?.let { download(it) }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getColorPrimary(): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        return typedValue.data
    }

    // 保存图片到手机
    fun download(url: String) {
        val pathDir = Constants.DOWNLOAD_DIR
        val pathName = pathDir + File.separator +
                EncryptUtils.encryptMD5ToString(url) + fileTypeStr
        if(FileUtils.isFileExists(pathName)){
            return ToastUtils.showShort("该图片已存在！")
        }
        var mRequestManager = GlideApp.with(this)
        var mRequestBuilder = mRequestManager
            .downloadOnly()
            .load(url)
            .listener(object : RequestListener<File> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<File>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: File?,
                    model: Any?,
                    target: Target<File>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    try {
                        FileUtils.createOrExistsDir(pathDir).let {
                            FileUtils.createOrExistsFile(pathName).let {
                                var file = FileUtils.getFileByPath(pathName)
                                resource?.apply {
                                    val success = FileUtils.copyFile(resource, file)
                                    val resultStr = if (success) "下载成功，目录：$pathName" else "下载失败"
                                    ToastUtils.showLong(resultStr)
                                    if(success){
                                        updateMediaStore(file)
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    return false
                }

            })

        mRequestBuilder.preload()
    }

    private fun updateMediaStore(file: File) {
        if (file.exists()) {
            try {
                val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                val uri = Uri.fromFile(file)
                intent.data = uri
                sendBroadcast(intent)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}