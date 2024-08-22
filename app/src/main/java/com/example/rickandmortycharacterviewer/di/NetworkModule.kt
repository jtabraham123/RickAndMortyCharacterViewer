package com.example.rickandmortycharacterviewer.di

import com.example.rickandmortycharacterviewer.network.CharacterService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("https://rickandmortyapi.com/api/character")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideCharacterService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)

}