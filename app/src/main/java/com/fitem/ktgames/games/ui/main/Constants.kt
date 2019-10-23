package com.fitem.ktgames.games.ui.main

import android.os.Environment
import com.blankj.utilcode.util.AppUtils
import java.io.File

/**
 * Created by LeiGuangwu on 2019-07-12.
 */
object Constants {

    const val CURRENT_TAB_INDEX = "current_tab_index"
    const val GIRLS_URL = "girls_url"
    const val NEWS_ID = "newsId"
    const val TITLE = "title"
    const val PLAYLIST_LOCAL_ID = "local"
    const val NETEASE = "netease"

    @JvmField
    val DOWNLOAD_DIR = Environment.getExternalStorageDirectory().absolutePath +
            File.separator + AppUtils.getAppName() + File.separator + "Download"
}
