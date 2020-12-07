package kg.kyrgyzcoder.uigo01.util

import android.app.Application
import kg.kyrgyzcoder.uigo01.data.local.UserPreferences
import kg.kyrgyzcoder.uigo01.data.local.UserPreferencesImpl
import kg.kyrgyzcoder.uigo01.data.network.ApiService
import kg.kyrgyzcoder.uigo01.ui.splash.viewmodel.UserPreferencesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class UigoApp : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@UigoApp))

        bind() from singleton { ApiService() }

        bind<UserPreferences>() with singleton { UserPreferencesImpl(instance()) }
        bind() from provider { UserPreferencesViewModelFactory(instance()) }

    }
}