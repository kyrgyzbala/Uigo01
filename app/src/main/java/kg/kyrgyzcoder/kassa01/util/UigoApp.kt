package kg.kyrgyzcoder.kassa01.util

import android.app.Application
import kg.kyrgyzcoder.kassa01.data.local.UserPreferences
import kg.kyrgyzcoder.kassa01.data.local.UserPreferencesImpl
import kg.kyrgyzcoder.kassa01.data.network.ApiService
import kg.kyrgyzcoder.kassa01.data.network.ApiService2
import kg.kyrgyzcoder.kassa01.data.network.item.repo.ItemRepository
import kg.kyrgyzcoder.kassa01.data.network.login.repo.LoginCashierRepository
import kg.kyrgyzcoder.kassa01.data.network.login.repo.LoginRepository
import kg.kyrgyzcoder.kassa01.data.network.order.repo.OrderRepository
import kg.kyrgyzcoder.kassa01.data.network.user.repo.UserDataRepository
import kg.kyrgyzcoder.kassa01.ui.items.viewmodel.ItemViewModelFactory
import kg.kyrgyzcoder.kassa01.ui.login.viewmodel.LoginCashierViewModelFactory
import kg.kyrgyzcoder.kassa01.ui.login.viewmodel.LoginViewModelFactory
import kg.kyrgyzcoder.kassa01.ui.more.viewmodel.MoreViewModelFactory
import kg.kyrgyzcoder.kassa01.ui.orders.viewmodel.OrderViewModelFactory
import kg.kyrgyzcoder.kassa01.ui.splash.viewmodel.UserPreferencesViewModelFactory
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
        bind() from singleton { ApiService2() }

        bind<UserPreferences>() with singleton { UserPreferencesImpl(instance()) }
        bind() from provider { UserPreferencesViewModelFactory(instance()) }

        bind<LoginRepository>() with singleton { LoginRepository(instance()) }
        bind() from provider { LoginViewModelFactory(instance(), instance()) }

        bind<ItemRepository>() with singleton { ItemRepository(instance()) }
        bind() from provider { ItemViewModelFactory(instance(), instance()) }

        bind<LoginCashierRepository>() with singleton { LoginCashierRepository(instance()) }
        bind() from provider { LoginCashierViewModelFactory(instance(), instance()) }

        bind<UserDataRepository>() with singleton { UserDataRepository(instance()) }
        bind() from provider { MoreViewModelFactory(instance(), instance()) }

        bind<OrderRepository>() with singleton { OrderRepository(instance()) }
        bind() from provider { OrderViewModelFactory(instance(), instance()) }
    }
}