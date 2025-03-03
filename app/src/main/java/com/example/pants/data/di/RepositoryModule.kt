package com.example.pants.data.di

import com.example.pants.data.repositoryImpl.ColorRepositoryImpl
import com.example.pants.domain.repository.ColorRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val productRepositoryModule = module {
    singleOf(::ColorRepositoryImpl) { bind<ColorRepository>() }
}
