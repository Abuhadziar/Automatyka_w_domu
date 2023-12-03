package com.example.automatyka_w_domu

import android.app.Application
import android.content.Context

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
        //TODO admin moze usuwac/dodawac userów, jesli admin nie nada uprawnien to nie widzis urzadzen w sali, admin moze usuwac dowolny pokoj a user tylko swój
    }

    companion object {
        lateinit var appContext: Context
    }
}