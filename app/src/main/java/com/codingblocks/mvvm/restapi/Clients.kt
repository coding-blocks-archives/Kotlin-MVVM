package com.codingblocks.mvvm.restapi

import com.codingblocks.mvvm.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton Class for the Networking Client
 **/

object Clients {

    private const val connectTimeout = 15 // 15s
    private const val readTimeout = 15 // 15s

    private val clientInterceptor = OkHttpClient.Builder()
        .connectTimeout(connectTimeout.toLong(), TimeUnit.SECONDS)
        .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
        /**
         * An OkHttp interceptor which logs request and response information.
         * Logs are generated into the studio logger
         **/
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        )
        /**
         * use this interceptor to authorization token to every request
         **/
//        .addInterceptor { chain ->
//            chain.proceed(
//                chain.request().newBuilder().addHeader(
//                    "Authorization",
//                    "Token $token"
//                ).build()
//            )
//        }
        .build()

    /**
     * Configures Gson to apply a specific naming policy to an object's field during serialization
     * and deserialization.
     * Reduces the work to use annotation like @SerializedName
     **/

    var gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    private val retrofit = Retrofit.Builder()
        .client(clientInterceptor)
        .baseUrl(BuildConfig.DEFAULT_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}