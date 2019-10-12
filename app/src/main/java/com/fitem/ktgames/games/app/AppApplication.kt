package com.fitem.ktgames.games.app

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.Utils
import kotlin.properties.Delegates


/**
 * Created by Fitem on 2018/3/16.
 */

class AppApplication : MultiDexApplication() {


    companion object {
        var context: Context by Delegates.notNull()
        fun getAppResources(): Resources {
            val myApplication = context.applicationContext as Application
            return myApplication.resources
        }
    }

    override fun onCreate() {
        super.onCreate()
        // Utils工具初始化
        Utils.init(this)
        context = applicationContext;
    }
}
