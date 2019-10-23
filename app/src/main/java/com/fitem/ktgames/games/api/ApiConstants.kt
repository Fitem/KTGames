package com.fitem.ktgames.games.api

/**
 * Created by LeiGuangwu on 2019-06-28.
 */

object ApiConstants {
    /**
     * 新闻资讯
     * http://c.3g.163.com/nc/article/list/T1348654151579/0-20.html
     * http://c.3g.163.com/nc/article/DCUAJVQC00318QE9/full.html
     */

    const val NEWS_HOST = "http://c.3g.163.com/nc/article/"
    const val NEWS_GAMES_TYPE = "T1348654151579"
    const val NEWS_HOT_TYPE = "T1348647909107"

    /**
     * 美女图片
     * http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
     */

    const val GRILS_HOST = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/"

    /**
     * TYPE: lol、dnf、pubg
     * http://api.maxjia.com/api/live/list/?offset=0&limit=20&live_type=&game_type=lol
     * http://api.maxjia.com/api/live/detail/?live_type=quanmin&live_id=666&game_type=lol
     */

    const val LIVE_HOST = "http://api.maxjia.com/api/live/"

    /**
     * 视频
     * http://baobab.kaiyanapp.com/api/v2/feed?date=1503104400000&num=1
     */
    const val VIDEO_HOST = "http://baobab.kaiyanapp.com/api/"

    /**
     * 网易云音乐
     */
    const val BASE_NETEASE_URL = "http://musiclake.leanapp.cn"

    /**
     * 百度音乐
     */
    const val BASE_BAIDU_MUSIC_URL = "http://musicapi.qianqian.com/"

    const val MUSIC_V1_TING = "v1/restserver/ting"

    const val MUSIC_GET_BILLCATEGORY = "baidu.ting.billboard.billCategory" //榜单

    const val MUSIC_GET_CATEGORY_LIST = "baidu.ting.radio.getCategoryList"
    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    fun getHost(hostType: Int): String {
        var url = NEWS_HOST
        when (hostType) {
            HostType.NEWS_HOST -> url = NEWS_HOST
            HostType.GRILS_HOST -> url = GRILS_HOST
            HostType.LIVE_HOST -> url = LIVE_HOST
            HostType.VIDEO_HOST -> url = VIDEO_HOST
            HostType.NETEASE_MUSIC_HOST -> url = BASE_NETEASE_URL
            HostType.BASE_BAIDU_MUSIC_URL -> url = BASE_BAIDU_MUSIC_URL
        }
        return url
    }
}