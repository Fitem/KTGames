package com.example.ktgames.games.UI.grils.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.ktgames.R
import com.example.ktgames.games.app.AppApplication
import com.example.ktgames.games.module.bean.Girls
import com.hazz.kotlinmvp.glide.GlideApp

/**
 * Created by LeiGuangwu on 2019-08-01.
 */
class GirlsAdapter(layoutResId: Int, data: List<Girls.ResultsBean>) :
    BaseQuickAdapter<Girls.ResultsBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: Girls.ResultsBean?) {

        helper?.getView<ImageView>(R.id.iv_pic)?.let {
            GlideApp.with(AppApplication.context).load(item?.url ?: "")
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.ic_news_prepare)
                .error(R.drawable.ic_news_prepare)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (it.scaleType != ImageView.ScaleType.FIT_XY) {
                            it.scaleType = ImageView.ScaleType.FIT_XY
                        }
                        val params = it.layoutParams
                        val vw = it.width - it.paddingLeft - it.paddingRight
                        val scale = vw.toFloat() / (resource?.intrinsicWidth?.toFloat() ?: vw.toFloat())
                        val vh = Math.round((resource?.intrinsicHeight ?: 0) * scale)
                        params.height = vh + it.paddingTop + it.paddingBottom
                        it.layoutParams = params
                        return false
                    }

                })
                .fitCenter()
                .dontAnimate().into(it)

        }
    }

}

