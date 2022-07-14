package com.example.exam

import android.app.Application
import com.example.exam.data.MediaItemsProvider
import com.example.exam.ui.detail.DetailActivity
import com.example.exam.ui.detail.DetailViewModel
import com.example.exam.ui.main.MainActivity
import com.example.exam.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class MyExamApplication : Application() {

    companion object {
        const val IO_DISPATCHER = "ioDispatcher"
    }

    val appModule = module {
        single { MediaItemsProvider }
        single(named(IO_DISPATCHER)) { Dispatchers.IO }
    }

    val scopeModule = module {
        scope<MainActivity> {
            viewModel { MainViewModel(get(), get(named(IO_DISPATCHER))) }
        }

        scope<DetailActivity> {
            viewModel { DetailViewModel(get(), get(named(IO_DISPATCHER))) }
        }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyExamApplication)
            modules(appModule, scopeModule)
        }
    }
}