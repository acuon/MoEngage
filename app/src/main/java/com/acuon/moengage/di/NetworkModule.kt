package com.acuon.moengage.di

import com.acuon.moengage.data.remote.RequestHandler
import com.acuon.moengage.data.repository.MainRepositoryImpl
import com.acuon.moengage.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRequestHandler(): RequestHandler {
        return RequestHandler
    }

    @Provides
    fun providesRepository(requestHandler: RequestHandler): MainRepository {
        return MainRepositoryImpl(requestHandler)
    }

}