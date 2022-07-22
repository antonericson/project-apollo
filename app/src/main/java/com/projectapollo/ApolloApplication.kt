package com.projectapollo

import android.app.Application
import android.content.Context
import com.projectapollo.model.Model

class ApolloApplication : Application() {
    lateinit var model: Model

    override fun onCreate() {
        super.onCreate()
        model = Model
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }
}