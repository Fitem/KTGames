package com.fitem.ktgames.games.ui.video.adapter

import android.graphics.Typeface
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fitem.ktgames.R
import com.fitem.ktgames.games.app.AppApplication
import com.fitem.ktgames.games.model.bean.HomeBean
import com.fitem.ktgames.games.utils.FormatUtils
import com.hazz.kotlinmvp.glide.GlideApp
import com.hazz.kotlinmvp.glide.GlideRoundTransform

/**
 * Created by LeiGuangwu on 2019-09-02.
 */
class VideoDetailAdapter(data: List<HomeBean.Issue.Item>) :
    BaseMultiItemQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(data) {

    private val textTypeface by lazy {
        Typeface.createFromAsset(AppApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }


    init {
        addItemType(VideoConstant.VIDEO_TEXT_CARD, R.layout.item_video_text_card)
        addItemType(VideoConstant.VIDEO_SMALL_CARD, R.layout.item_video_small_card)
    }

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.Item?) {
        helper?.let {
            when (it.itemViewType) {
                VideoConstant.VIDEO_TEXT_CARD -> {
                    helper.setText(R.id.tv_text_card, item?.data?.text!!)
                    //设置方正兰亭细黑简体
                    helper.getView<TextView>(R.id.tv_text_card).typeface = textTypeface
                }
                VideoConstant.VIDEO_SMALL_CARD -> {
                    helper.setText(R.id.tv_title, item?.data?.title)
                    helper.setText(
                        R.id.tv_tag,
                        "#${item?.data?.category} / ${FormatUtils.durationFormat(item?.data?.duration)}"
                    )
                    GlideApp.with(mContext)
                        .load(item?.data?.cover?.detail)
                        .optionalTransform(GlideRoundTransform())
                        .placeholder(R.drawable.placeholder_banner)
                        .into(helper.getView(R.id.iv_video_small_card))
                }
                else ->
                    throw IllegalAccessException("Api 解析出错了，出现其他类型")
            }
        }
    }

}