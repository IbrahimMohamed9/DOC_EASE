package com.example.DocEase

import android.app.Application
import com.example.DocEase.model.AppContainer
import com.example.DocEase.model.AppDataContainer

class DocEaseApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
