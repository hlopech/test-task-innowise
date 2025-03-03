package com.example.pants.data.di

import org.koin.dsl.module

val dataModule = module {
    includes(networkModule, productRepositoryModule, useCaseModule)
}
