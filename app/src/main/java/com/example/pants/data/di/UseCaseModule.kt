package com.example.pants.data.di

import com.example.pants.domain.usecases.CheckBoardOrderUseCase
import com.example.pants.domain.usecases.GetColorBoardUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetColorBoardUseCase)
    factoryOf(::CheckBoardOrderUseCase)
}
