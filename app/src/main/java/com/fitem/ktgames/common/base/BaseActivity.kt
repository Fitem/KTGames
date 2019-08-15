package com.fitem.ktgames.common.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by LeiGuangwu on 2019-06-28.
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doBeforeSetcontentView()
        parseArgument()
        if (getLayoutId() > 0) {
            setContentView(getLayoutId())
        }
        initView()
        initListener()
        initData()
    }

    /**
     * 预处理数据
     */
    open fun parseArgument() {}

    /**
     * 加载布局
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化View
     */
    abstract fun initView()

    /**
     * 初始化Listener
     */
    abstract fun initListener()

    /**
     * 初始化数据
     */
    abstract fun initData()

    fun doBeforeSetcontentView() {
        //设置昼夜主题
//        initTheme();
        // 无标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // 设置竖屏
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * 打开软键盘
     */
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }
}