package com.example.rickandmortycharacterviewer.di

import com.bumptech.glide.util.ViewPreloadSizeProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

class AdapterModule {
    @Module
    @InstallIn(FragmentComponent::class)
    object AppModule {

        @Provides
        fun provideSizeProvider(): ViewPreloadSizeProvider<Any> {
            return ViewPreloadSizeProvider()
        }
    }
}