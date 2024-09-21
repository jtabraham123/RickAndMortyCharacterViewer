package com.example.rickandmortycharacterviewer.network

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.example.rickandmortycharacterviewer.RickAndMortyCharacterViewerApplication
import okhttp3.OkHttpClient
import java.io.InputStream
import javax.inject.Inject


@GlideModule
class GlideMod: AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val interceptor = (context as RickAndMortyCharacterViewerApplication).imageInterceptor
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        registry.replace(
            GlideUrl::class.java,               // URL handling with Glide
            InputStream::class.java,            // Stream for loading images
            OkHttpUrlLoader.Factory(client)
        )
    }
}

