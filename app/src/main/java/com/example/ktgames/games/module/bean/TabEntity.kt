package com.example.ktgames.games.module.bean

import com.flyco.tablayout.listener.CustomTabEntity
import java.io.Serializable

data class TabEntity(val title: String, val selectedIcon: Int, val unSelectedIcon: Int) : Serializable, CustomTabEntity {
    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabTitle(): String {
        return title
    }
}
