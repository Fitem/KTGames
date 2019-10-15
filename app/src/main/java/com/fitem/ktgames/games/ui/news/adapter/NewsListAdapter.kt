package com.fitem.ktgames.games.ui.news.adapter

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fitem.ktgames.R
import com.fitem.ktgames.games.app.AppApplication
import com.fitem.ktgames.games.model.bean.GNews
import com.hazz.kotlinmvp.glide.GlideApp

/**
 * Created by LeiGuangwu on 2019-10-12.
 */
class NewsListAdapter(layoutResId: Int, data: List<GNews>) :
    BaseQuickAdapter<GNews, BaseViewHolder>(layoutResId, data) {

    private val titleColor = ContextCompat.getColor(AppApplication.context, R.color.sdk_color_104)
    private val contentColor = ContextCompat.getColor(AppApplication.context, R.color.sdk_color_008)
    private val accentColor = ContextCompat.getColor(AppApplication.context, R.color.colorAccent)

    override fun convert(helper: BaseViewHolder?, item: GNews?) {
        val url = item?.imgsrc
        val position = data.indexOf(item)
        val isFirst = position == 0
        val color1 = if (isFirst) accentColor else titleColor
        val color2 = if (isFirst) accentColor else contentColor

        helper?.let {
            it.setTextColor(R.id.tv_title, color1)
            it.setTextColor(R.id.tv_date, color2)
            it.setTextColor(R.id.tv_source, color2)
            it.setText(R.id.tv_title, item!!.title)
            it.setText(R.id.tv_date, item.ptime)
            it.setText(R.id.tv_source, item.source)
            val picView = it.getView<ImageView>(R.id.iv_pic)
            GlideApp.with(AppApplication.context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.ic_news_prepare)
                .error(R.drawable.ic_news_prepare)
                .centerCrop()
                .into(picView)
        }
    }

}