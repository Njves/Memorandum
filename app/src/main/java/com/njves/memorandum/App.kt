package com.njves.memorandum

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        MemoRepository.init(applicationContext)
    }
}