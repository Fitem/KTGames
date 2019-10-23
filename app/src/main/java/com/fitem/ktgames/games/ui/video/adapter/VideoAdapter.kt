package com.fitem.ktgames.games.ui.video.adapter

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fitem.ktgames.R
import com.fitem.ktgames.games.model.bean.HomeBean
import com.fitem.ktgames.games.ui.video.activity.VideoDetailActivity
import com.fitem.ktgames.games.utils.FormatUtils
import com.hazz.kotlinmvp.glide.GlideApp
import io.reactivex.Observable

/**
 * Created by LeiGuangwu on 2019-08-01.
 */
class VideoAdapter(data: List<HomeBean.Issue.Item>) :
    BaseMultiItemQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(data) {

    init {
        addItemType(VideoConstant.VIDEO_BANNER, R.layout.item_home_banner)
        addItemType(VideoConstant.VIDEO_TEXT_HEADER, R.layout.item_home_header)
        addItemType(VideoConstant.VIDEO_CONTENT, R.layout.item_home_content)
    }

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.Item?) {
        when (helper?.itemViewType) {
            //Banner
            VideoConstant.VIDEO_BANNER -> {
                val bannerItemData = item?.itemList
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                //取出banner 显示的 img 和 Title
                Observable.fromIterable(bannerItemData)
                    .subscribe { list ->
                        bannerFeedList.add(list.data?.cover?.feed ?: "")
                        bannerTitleList.add(list.data?.title ?: "")
                    }
                //设置 banner
                helper.getView<BGABanner>(R.id.banner).run {
                    setAutoPlayAble(bannerFeedList.size > 1)
                    setData(bannerFeedList, bannerTitleList)
                    setAdapter { banner, _, feedImageUrl, position ->
                        GlideApp.with(mContext)
                            .load(feedImageUrl)
                            .transition(DrawableTransitionOptions().crossFade())
                            .placeholder(R.drawable.placeholder_banner)
                            .into(banner.getItemImageView(position))


                    }
                }
                //没有使用到的参数在 kotlin 中用"_"代替
                helper.getView<BGABanner>(R.id.banner).setDelegate { _, imageView, _, i ->
                    goToVideoPlayer(mContext as Activity, imageView, bannerItemData!![i])

                }
            }
            //TextHeader
            VideoConstant.VIDEO_TEXT_HEADER -> {
                helper?.setText(R.id.tvHeader, item?.data?.text ?: "")
            }

            //content
            VideoConstant.VIDEO_CONTENT -> {
                setVideoItem(helper, item)
            }


        }

    }

    /**
     * 加载 content item
     */
    private fun setVideoItem(helper: BaseViewHolder, item: HomeBean.Issue.Item?) {
        val itemData = item?.data

        val defAvatar = R.drawable.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = "#"

        // 作者出处为空，就显获取提供者的信息
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }
        // 加载封页图
        GlideApp.with(mContext)
            .load(cover)
            .placeholder(R.drawable.placeholder_banner)
            .transition(DrawableTransitionOptions().crossFade())
            .into(helper.getView<ImageView>(R.id.iv_cover_feed))

        // 如果提供者信息为空，就显示默认
        if (avatar.isNullOrEmpty()) {
            GlideApp.with(mContext)
                .load(defAvatar)
                .placeholder(R.drawable.default_avatar).circleCrop()
                .transition(DrawableTransitionOptions().crossFade())
                .into(helper.getView<ImageView>(R.id.iv_avatar))

        } else {
            GlideApp.with(mContext)
                .load(avatar)
                .placeholder(R.drawable.default_avatar).circleCrop()
                .transition(DrawableTransitionOptions().crossFade())
                .into(helper.getView<ImageView>(R.id.iv_avatar))
        }
        helper.setText(R.id.tv_title, itemData?.title ?: "")

        //遍历标签
        itemData?.tags?.take(4)?.forEach {
            tagText += (it.name + "/")
        }
        // 格式化时间
        val timeFormat = FormatUtils.durationFormat(itemData?.duration)

        tagText += timeFormat

        helper.setText(R.id.tv_tag, tagText!!)

        helper.setText(R.id.tv_category, "#" + itemData?.category)
    }

    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    open fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(VideoConstant.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoConstant.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair(view, VideoConstant.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, pair
            )
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
}

