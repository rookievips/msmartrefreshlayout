package com.rookievips.android.app.msmartrefreshlayout

import android.app.Application
import com.rookievips.msmartrefreshlayout.smart.MSmartRefreshConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MSmartRefreshConfig.init(
            refreshStyle = MSmartRefreshConfig.STYLE_CLASSIC
        )
    }
}