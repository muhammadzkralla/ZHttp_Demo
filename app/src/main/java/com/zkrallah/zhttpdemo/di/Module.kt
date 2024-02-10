package com.zkrallah.zhttpdemo.di

import com.zkrallah.zhttp.ZHttpClient
import com.zkrallah.zhttpdemo.data.repo.MainRepoImpl
import com.zkrallah.zhttpdemo.domain.repo.MainRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StoreClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImgurClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthClient


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    @StoreClient
    fun provideStoreClient(): ZHttpClient {
        return ZHttpClient.Builder()
            .baseUrl("https://api.restful-api.dev")
            .connectionTimeout(20000)
            .readTimeout(20000)
            .build()
    }

    @Provides
    @Singleton
    @ImgurClient
    fun provideImgurClient(): ZHttpClient {
        return ZHttpClient.Builder()
            .baseUrl("https://api.imgur.com")
            .connectionTimeout(20000)
            .readTimeout(20000)
            .build()
    }

    @Provides
    @Singleton
    @AuthClient
    fun provideAuthClient(): ZHttpClient {
        return ZHttpClient.Builder()
            .baseUrl("https://fakestoreapi.com")
            .connectionTimeout(20000)
            .readTimeout(20000)
            .build()
    }

    @Provides
    @Singleton
    fun provideMainRepo(
        @StoreClient storeClient: ZHttpClient,
        @ImgurClient imgurClient: ZHttpClient,
        @AuthClient authClient: ZHttpClient
    ): MainRepo {
        return MainRepoImpl(storeClient, imgurClient, authClient)
    }

}