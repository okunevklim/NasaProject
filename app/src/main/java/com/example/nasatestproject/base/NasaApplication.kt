package com.example.nasatestproject.base

import android.app.Application
import android.content.Context
import com.example.nasatestproject.di.component.ApiComponent
import com.example.nasatestproject.di.component.DaggerApiComponent

class NasaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
        component = buildComponent()
    }

    private fun buildComponent(): ApiComponent {
        return DaggerApiComponent.builder().build()
    }

    companion object {
        lateinit var context: Context
        lateinit var component: ApiComponent
    }
}