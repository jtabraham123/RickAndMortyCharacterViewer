package com.example.rickandmortycharacterviewer.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageInterceptor @Inject constructor() : Interceptor {
    private var ongoingRequests = AtomicInteger(0)

    override fun intercept(chain: Interceptor.Chain): Response {
        ongoingRequests.incrementAndGet()
        Log.d("Test", "Test")
        try {
            return chain.proceed(chain.request())
        } finally {
            ongoingRequests.decrementAndGet()
        }
    }

    fun getOnGoingRequests() : Int {
        return ongoingRequests.get()
    }
}