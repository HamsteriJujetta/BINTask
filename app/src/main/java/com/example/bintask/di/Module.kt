package com.example.bintask.di

import com.example.bintask.database.data.RequestInteractor
import com.example.bintask.database.data.RequestLocalSource
import com.example.bintask.database.data.RequestRepository
import com.example.bintask.database.data.RequestRepositoryImpl
import com.example.bintask.network.data.NetworkInteractor
import com.example.bintask.network.data.NetworkRemoteSource
import com.example.bintask.network.data.NetworkRepository
import com.example.bintask.network.data.NetworkRepositoryImpl
import com.example.bintask.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    // see databaseModule:
    // single {get<AppDatabase>().requestDao()}
    single {
        RequestLocalSource(requestDao = get())
    }

    single<RequestRepository> {
        RequestRepositoryImpl(requestLocalSource = get())
    }

    single {
        RequestInteractor(requestRepository = get())
    }


    // see networkModule:
    // single<BINApi> {get<Retrofit>().create(BINApi::class.java)}
    single<NetworkRemoteSource> {
        NetworkRemoteSource(api = get())
    }

    single<NetworkRepository> {
        NetworkRepositoryImpl(source = get())
    }

    single {
        NetworkInteractor(repository = get())
    }

    viewModel {
        MainViewModel(requestInteractor = get(), networkInteractor = get())
    }
}