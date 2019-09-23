package com.fitem.ktgames.games.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by LeiGuangwu on 2019-09-02.
 */
object FormatUtils {

    fun durationFormat(duration: Long?): String {
        val minute = duration!! / 60
        val second = duration % 60
        return if (minute <= 9) {
            if (second <= 9) {
                "0$minute' 0$second''"
            } else {
                "0$minute' $second''"
            }
        } else {
            if (second <= 9) {
                "$minute' 0$second''"
            } else {
                "$minute' $second''"
            }
        }
    }

    /**
     * 数据流量格式化
     */
    fun dataFormat(total: Long): String {
        var result: String
        var speedReal: Int = (total / (1024)).toInt()
        result = if (speedReal < 512) {
            speedReal.toString() + " KB"
        } else {
            val mSpeed = speedReal / 1024.0
            (Math.round(mSpeed * 100) / 100.0).toString() + " MB"
        }
        return result
    }

    @SuppressLint("SimpleDateFormat")
    fun format(date: Date, s: String): String? {
        return SimpleDateFormat(s).format(date)
    }
}