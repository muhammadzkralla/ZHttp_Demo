package com.zkrallah.zhttpdemo.di

import com.zkrallah.zhttp.client.ZHttpClient
import com.zkrallah.zhttp.model.Bearer
import com.zkrallah.zhttpdemo.data.repo.MainRepoCoroutineImpl
import com.zkrallah.zhttpdemo.data.repo.MainRepoImpl
import com.zkrallah.zhttpdemo.data.repo.MainRepoSyncImpl
import com.zkrallah.zhttpdemo.domain.repo.MainRepo
import com.zkrallah.zhttpdemo.domain.repo.MainRepoCoroutine
import com.zkrallah.zhttpdemo.domain.repo.MainRepoSync
import com.zkrallah.zhttpdemo.util.Imgur
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImgurClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StoreClient


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    @StoreClient
    fun provideStoreClient(): ZHttpClient {
        return ZHttpClient.Builder()
            .baseUrl("https://fakestoreapi.com")
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
            .authenticated(Bearer(Imgur.TOKEN))
            .build()
    }

    @Provides
    @Singleton
    fun provideMainRepo(
        @StoreClient storeClient: ZHttpClient,
        @ImgurClient imgurClient: ZHttpClient,
    ): MainRepo {
        return MainRepoImpl(storeClient, imgurClient)
    }

    @Provides
    @Singleton
    fun provideMainRepoSync(
        @StoreClient storeClient: ZHttpClient,
        @ImgurClient imgurClient: ZHttpClient,
    ): MainRepoSync {
        return MainRepoSyncImpl(storeClient, imgurClient)
    }

    @Provides
    @Singleton
    fun provideMainRepoCoroutine(
        @StoreClient storeClient: ZHttpClient,
        @ImgurClient imgurClient: ZHttpClient,
    ): MainRepoCoroutine {
        return MainRepoCoroutineImpl(storeClient, imgurClient)
    }

}