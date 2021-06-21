package com.qingmei2.sample.base

import com.facebook.stetho.Stetho
import com.qingmei2.architecture.core.base.BaseApplication
import com.qingmei2.architecture.core.logger.initLogger
import com.qingmei2.sample.BuildConfig
import com.squareup.leakcanary.LeakCanary
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class GitHubApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initLogger(BuildConfig.DEBUG)
        initStetho()
        initLeakCanary()
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        if (!isTestEnvironment())
            LeakCanary.install(this)
    }

    override fun isTestEnvironment(): Boolean {
        return true
    }
}
