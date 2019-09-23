package com.fitem.ktgames.games.ui.video.activity

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.res.Configuration
import android.os.Build
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fitem.ktgames.R
import com.fitem.ktgames.common.base.BaseActivity
import com.fitem.ktgames.games.app.AppApplication
import com.fitem.ktgames.games.contract.VideoDetailContract
import com.fitem.ktgames.games.model.bean.HomeBean
import com.fitem.ktgames.games.presenter.VideoDetailPresenter
import com.fitem.ktgames.games.ui.video.VideoListener
import com.fitem.ktgames.games.ui.video.adapter.VideoConstant
import com.fitem.ktgames.games.ui.video.adapter.VideoDetailAdapter
import com.fitem.ktgames.games.utils.CleanLeakUtils
import com.fitem.ktgames.games.utils.FormatUtils
import com.fitem.ktgames.games.utils.WatchHistoryUtils
import com.hazz.kotlinmvp.glide.GlideApp
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.item_video_detail_info.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by LeiGuangwu on 2019-08-30.
 */
class VideoDetailActivity : BaseActivity(), VideoDetailContract.View, SwipeRefreshLayout.OnRefreshListener {
    private var orientationUtils: OrientationUtils? = null

    private lateinit var itemData: HomeBean.Issue.Item
    private var itemList = ArrayList<HomeBean.Issue.Item>()
    private var isPlay: Boolean = false
    private var isPause: Boolean = false
    private var isTransition: Boolean = false
    private var transition: Transition? = null
    private var mHeadView: View? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_video_detail
    }

    /**
     * 第一次调用的时候初始化
     */
    private val mPresenter by lazy { VideoDetailPresenter() }

    private val mAdapter by lazy { VideoDetailAdapter(itemList) }

    init {
        mPresenter.attachView(this)
    }

    override fun parseArgument() {
        itemData = intent.getSerializableExtra(VideoConstant.BUNDLE_VIDEO_DATA) as HomeBean.Issue.Item
        isTransition = intent.getBooleanExtra(VideoConstant.TRANSITION, false)
        saveWatchVideoHistoryInfo(itemData)
    }

    override fun initView() {
        //过渡动画
        initStatusBar()
        initTransition()
        initVideoViewConfig()
        initRecyclerView()

        mRefreshLayout.setOnRefreshListener(this)
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent)
    }

    private fun initStatusBar() {
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.color_translucent))
        BarUtils.setStatusBarLightMode(this, false)
        /* minSDK是19，因此不做低版本兼容处理 */
        mVideoView.setPadding(
            mVideoView.paddingLeft,
            BarUtils.getStatusBarHeight(),
            mVideoView.paddingRight,
            mVideoView.paddingBottom
        )
    }

    private fun initRecyclerView() {
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
        mHeadView = createHeadView()
        mAdapter.addHeaderView(mHeadView)
    }

    private fun createHeadView(): View {
        return layoutInflater.inflate(R.layout.item_video_detail_info, mRecyclerView, false)
    }

    override fun initListener() {
        mAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { _, _, position ->
                mPresenter.loadVideoInfo(mAdapter.data[position])
            }
    }

    override fun onRefresh() {
        loadVideoInfo()
    }

    override fun initData() {

    }

    private fun initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(mVideoView, VideoConstant.IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        } else {
            loadVideoInfo()
        }
    }

    /**
     * 初始化 VideoView 的配置
     */
    private fun initVideoViewConfig() {
        //设置旋转
        orientationUtils = OrientationUtils(this, mVideoView)
        //是否旋转
        mVideoView.isRotateViewAuto = false
        //是否可以滑动调整
        mVideoView.setIsTouchWiget(true)

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        GlideApp.with(this)
            .load(itemData.data?.cover?.feed)
            .centerCrop()
            .into(imageView)
        mVideoView.thumbImageView = imageView

        mVideoView.setStandardVideoAllCallBack(object : VideoListener {

            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils?.isEnable = true
                isPlay = true
            }

            override fun onAutoComplete(url: String, vararg objects: Any) {
                super.onAutoComplete(url, *objects)
                LogUtils.d("***** onAutoPlayComplete **** ")
            }

            override fun onPlayError(url: String, vararg objects: Any) {
                super.onPlayError(url, *objects)
                ToastUtils.showShort("播放失败")
            }

            override fun onEnterFullscreen(url: String, vararg objects: Any) {
                super.onEnterFullscreen(url, *objects)
                LogUtils.d("***** onEnterFullscreen **** ")
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                LogUtils.d("***** onQuitFullscreen **** ")
                //列表返回的样式判断
                orientationUtils?.backToProtVideo()
            }
        })
        //设置返回按键功能
        mVideoView.backButton.setOnClickListener({ onBackPressed() })
        //设置全屏按键功能
        mVideoView.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            mVideoView.startWindowFullscreen(this, true, true)
        }
        //锁屏事件
        mVideoView.setLockClickListener(object : LockClickListener {
            override fun onClick(view: View?, lock: Boolean) {
                //配合下方的onConfigurationChanged
                orientationUtils?.isEnable = !lock
            }

        })
    }

    /**
     * 设置视频详情数据
     */
    @SuppressLint("SetTextI18n")
    private fun setVideoDetailInfo(data: HomeBean.Issue.Item, view: View) {
        data.data?.title?.let { view.tv_title.text = it }
        //视频简介
        data.data?.description?.let { view.expandable_text.text = it }
        //标签
        view.tv_tag.text = "#${data.data?.category} / ${FormatUtils.durationFormat(data.data?.duration)}"
        //喜欢
        view.tv_action_favorites.text = data.data?.consumption?.collectionCount.toString()
        //分享
        view.tv_action_share.text = data.data?.consumption?.shareCount.toString()
        //评论
        view.tv_action_reply.text = data.data?.consumption?.replyCount.toString()

        if (data.data?.author != null) {
            view.tv_author_name.text = data.data.author.name
            view.tv_author_desc.text = data.data.author.description
            //加载头像
            GlideApp.with(this)
                .load(data.data.author.icon)
                .placeholder(R.drawable.default_avatar).circleCrop()
                .into(view.iv_avatar)
        } else {
            view.layout_author_view.visibility = View.GONE
        }

        with(view) {
            tv_action_favorites.setOnClickListener {
                ToastUtils.showShort("功能待开发，精")
            }
            tv_action_share.setOnClickListener {
                ToastUtils.showShort("分享")
            }
            tv_action_reply.setOnClickListener {
                ToastUtils.showShort("评论")
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener() {
        transition = window.sharedElementEnterTransition
        transition?.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(p0: Transition?) {
            }

            override fun onTransitionPause(p0: Transition?) {
            }

            override fun onTransitionCancel(p0: Transition?) {
            }

            override fun onTransitionStart(p0: Transition?) {
            }

            override fun onTransitionEnd(p0: Transition?) {
                LogUtils.d("onTransitionEnd()------")

                loadVideoInfo()
                transition?.removeListener(this)
            }

        })
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            mVideoView.onConfigurationChanged(this, newConfig, orientationUtils)
        }
    }

    /**
     * 保存观看记录
     */
    private fun saveWatchVideoHistoryInfo(watchItem: HomeBean.Issue.Item) {
        //保存之前要先查询sp中是否有该value的记录，有则删除.这样保证搜索历史记录不会有重复条目
        val historyMap =
            WatchHistoryUtils.getAll(VideoConstant.FILE_WATCH_HISTORY_NAME, AppApplication.context) as Map<*, *>
        for ((key, _) in historyMap) {
            if (watchItem == WatchHistoryUtils.getObject(
                    VideoConstant.FILE_WATCH_HISTORY_NAME,
                    AppApplication.context,
                    key as String
                )
            ) {
                WatchHistoryUtils.remove(VideoConstant.FILE_WATCH_HISTORY_NAME, AppApplication.context, key)
            }
        }
        WatchHistoryUtils.putObject(
            VideoConstant.FILE_WATCH_HISTORY_NAME, AppApplication.context, watchItem, "" + FormatUtils.format(
                Date(), "yyyyMMddHHmmss"
            )
        )
    }

    /**
     * 1.加载视频信息
     */
    fun loadVideoInfo() {
        mPresenter.loadVideoInfo(itemData)
    }

    override fun setVideo(url: String) {
        mVideoView.setUp(url, false, "")
        //开始自动播放
        mVideoView.startPlayLogic()
    }

    override fun setVideoInfo(itemInfo: HomeBean.Issue.Item) {
        itemData = itemInfo
        setVideoDetailInfo(itemInfo, mHeadView!!)
        // 请求相关的最新等视频
        mPresenter.requestRelatedVideo(itemInfo.data?.id ?: 0)
    }

    override fun setBackground(url: String) {
        GlideApp.with(this)
            .load(url)
            .centerCrop()
            .format(DecodeFormat.PREFER_ARGB_8888)
            .transition(DrawableTransitionOptions().crossFade())
            .into(mVideoBackground)
    }

    override fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>) {
        itemList.map {
            when (it.type) {
                VideoConstant.TEXE_CARD ->
                    it.itemType = VideoConstant.VIDEO_TEXT_CARD
                VideoConstant.SMALL_CARD ->
                    it.itemType = VideoConstant.VIDEO_SMALL_CARD
                else ->
                    it.itemType = VideoConstant.VIDEO_SMALL_CARD
            }
        }
        mAdapter.addData(itemList)
    }

    override fun setErrorMsg(errorMsg: String) {
        ToastUtils.showShort(errorMsg)
        mRefreshLayout.isRefreshing = false
    }


    override fun showLoading() {
        ToastUtils.showShort("网络请求中。。。")
    }

    override fun dismissLoading() {
        ToastUtils.showShort("请求结束")
        mRefreshLayout.isRefreshing = false
    }


    /**
     * 监听返回键
     */
    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (StandardGSYVideoPlayer.backFromWindowFull(this))
            return
        //释放所有
        mVideoView.setStandardVideoAllCallBack(null)
        GSYVideoPlayer.releaseAllVideos()
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) run {
            super.onBackPressed()
        } else {
            finish()
            overridePendingTransition(R.anim.anim_out, R.anim.anim_in)
        }
    }

    override fun onResume() {
        super.onResume()
        getCurPlay().onVideoResume()
        isPause = false
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    override fun onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this)
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.releaseListener()
        mPresenter.detachView()
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null) {
            mVideoView.fullWindowPlayer
        } else mVideoView
    }
}
