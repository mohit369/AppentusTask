package com.appentustask.utils


import android.app.Application
import androidx.room.Room
import com.appentustask.BuildConfig
import com.appentustask.interfaces.ApiInterface
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val BASE_URL = "https://picsum.photos/"
    var apiRestInterfaces: ApiInterface? = null

    /**
     * making retrofit API Client to call API
     */
    @JvmStatic
    val client: ApiInterface?
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(180, TimeUnit.SECONDS)
                .connectTimeout(180, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) okHttpClient.addInterceptor(interceptor)
            if (apiRestInterfaces == null) {
                val client = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .build()
                apiRestInterfaces =
                    client.create(ApiInterface::class.java)
            }
            return apiRestInterfaces
        }


    fun provideDatabase(app: Application) : OfflineDatabases =
        Room.databaseBuilder(app, OfflineDatabases::class.java, "offline_databases")
            .build()
}