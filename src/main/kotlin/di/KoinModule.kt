package com.daccvo.di

import com.daccvo.repository.UserRepository
import com.daccvo.repository.UserRepositoryImpl
import org.koin.dsl.module

val KoinModule = module {
    single <UserRepository> {
        UserRepositoryImpl()
    }

}