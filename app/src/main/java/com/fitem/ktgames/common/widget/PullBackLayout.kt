/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.fitem.ktgames.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper

class PullBackLayout(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private val dragger: ViewDragHelper // http://blog.csdn.net/lmj623565791/article/details/46858663

    private val minimumFlingVelocity: Int

    private var callback: Callback? = null

    init {
        dragger = ViewDragHelper.create(this, 1f / 8f, ViewDragCallback()) // 1f / 8f是敏感度参数参数越大越敏感
        minimumFlingVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity
    }

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        try {
            return dragger.shouldInterceptTouchEvent(ev)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        try {
            dragger.processTouchEvent(event)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    override fun computeScroll() {
        if (dragger.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    interface Callback {

        fun onPullStart()

        fun onPull(progress: Float)

        fun onPullCancel()

        fun onPullComplete()

    }

    private inner class ViewDragCallback : ViewDragHelper.Callback() {

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return 0
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return Math.max(0, top)
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return 0
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return height
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            if (callback != null) {
                callback!!.onPullStart()
            }
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            if (callback != null) {
                callback!!.onPull(top.toFloat() / height.toFloat())
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            val slop = if (yvel > minimumFlingVelocity) height / 6 else height / 3
            if (releasedChild.top > slop) {
                if (callback != null) {
                    callback!!.onPullComplete()
                }
            } else {
                if (callback != null) {
                    callback!!.onPullCancel()
                }

                dragger.settleCapturedViewAt(0, 0)
                invalidate()
            }
        }

    }

}
