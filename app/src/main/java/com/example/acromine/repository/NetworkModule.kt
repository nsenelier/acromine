package com.example.acromine.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(
    ): Retrofit {
       return Retrofit.Builder()
            .baseUrl(Repository.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideNetwork(retrofit: Retrofit): NetworkApi{
     return retrofit.create(NetworkApi::class.java)
    }

}