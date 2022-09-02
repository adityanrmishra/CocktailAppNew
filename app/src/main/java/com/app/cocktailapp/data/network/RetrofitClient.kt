package com.app.cocktailapp.data.network

import android.content.Context
import com.app.cocktailapp.core.NetworkConfig
import com.app.cocktailapp.core.NetworkConfig.CACHE_DIRECTORY
import com.app.cocktailapp.core.NetworkConfig.CACHE_HEADER_CACHE_CONTROL
import com.app.cocktailapp.core.NetworkConfig.CACHE_HEADER_EXPIRES
import com.app.cocktailapp.core.NetworkConfig.CACHE_HEADER_PRAGMA
import com.app.cocktailapp.core.NetworkConfig.CACHE_SIZE_BYTES
import com.app.cocktailapp.core.CheckInternet.isOnline
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitClient @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun getRetrofitObj(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
    }

    private fun getOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(NetworkConfig.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkConfig.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(getLoginInterceptor())
            .addInterceptor(getHeaderInterceptor())
            .addNetworkInterceptor(getCacheInterceptor())
            .addInterceptor(getOfflineCacheInterceptor())
            .cache(getHttpCache())
            .build()
    }

    private fun getHeaderInterceptor(): Interceptor {
        return Interceptor {
            val requestBuilder = it.request().newBuilder()
            //hear you can add all headers you want by calling 'requestBuilder.addHeader(name ,  value)'
            requestBuilder.addHeader("Content-Type", "application/json")
            requestBuilder.addHeader("Accept", "application/json")
            //requestBuilder.addHeader("OS", "Android-${Build.VERSION.SDK_INT}")
            //requestBuilder.addHeader("Version", BuildConfig.VERSION_NAME)
            requestBuilder.build()
            it.proceed(requestBuilder.build())
        }
    }

    private fun getLoginInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        return interceptor
    }

    private fun getHttpCache(): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, CACHE_DIRECTORY)
        return Cache(httpCacheDirectory, CACHE_SIZE_BYTES)
    }

    private fun getCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()
            val originalResponse: Response = chain.proceed(request)
            if (isOnline(context)) {
                originalResponse
            } else {
                val cacheControl: String? = originalResponse.header(CACHE_HEADER_CACHE_CONTROL)
                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains(
                        "no-cache"
                    ) ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")
                ) {
                    val cc = CacheControl.Builder()
                        .maxStale(1, TimeUnit.DAYS)
                        .build()
                    request = request.newBuilder()
                        .removeHeader(CACHE_HEADER_PRAGMA)
                        .removeHeader(CACHE_HEADER_EXPIRES)
                        .removeHeader(CACHE_HEADER_CACHE_CONTROL)
                        .cacheControl(cc)
                        .build()
                    chain.proceed(request)
                } else {
                    originalResponse
                }
            }
        }
    }

    private fun getOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            try {
                return@Interceptor chain.proceed(chain.request())
            } catch (e: Exception) {
                val cacheControl = CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()

                val offlineRequest: Request = chain.request().newBuilder()
                    .removeHeader(CACHE_HEADER_PRAGMA)
                    .removeHeader(CACHE_HEADER_EXPIRES)
                    .removeHeader(CACHE_HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()

                return@Interceptor chain.proceed(offlineRequest)
            }
        }
    }
}