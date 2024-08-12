package com.example.applenia_carbon.core.retrofit

import com.example.applenia_carbon.dataEjemplo.ipserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ipserver)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideLeniaCarbonClient(retrofit: Retrofit): LeniaCarbonClient {
        return retrofit.create(LeniaCarbonClient::class.java)
    }

}